package com.aylson.dc.sys.service.impl;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aylson.core.exception.ServiceException;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.base.GeneralConstant.UserType;
import com.aylson.dc.sys.dao.AgentUserDao;
import com.aylson.dc.sys.po.AgentUser;
import com.aylson.dc.sys.search.AgentUserSearch;
import com.aylson.dc.sys.search.UserSearch;
import com.aylson.dc.sys.service.AgentUserService;
import com.aylson.dc.sys.service.UserService;
import com.aylson.dc.sys.vo.AgentUserVo;
import com.aylson.dc.sys.vo.UserVo;
import com.aylson.utils.DateUtil;
import com.aylson.utils.MD5Encoder;
import com.aylson.utils.StringUtil;
import com.aylson.utils.VerificationUtils;


@Service
public class AgentUserServiceImpl extends BaseServiceImpl<AgentUser,AgentUserSearch> implements AgentUserService {

	@Autowired
	private AgentUserDao agentUserDao;
	@Autowired
	private UserService userService;           //系统用户服务

	@Override
	protected BaseDao<AgentUser,AgentUserSearch> getBaseDao() {
		return agentUserDao;
	}

	@Override
	@Transactional
	public Result addAgentUser(AgentUserVo agentUserVo) {
		Result result = new Result();
		//1、信息有效性校验
		//添加前判断是否用户名存在
		UserVo userVo = new UserVo();
		userVo.setUserName(agentUserVo.getUserName());
		result = this.userService.validUserInfo(userVo);
		if(!result.isSuccess()){
			return result;
		}
		if(StringUtil.isEmpty(agentUserVo.getPwd())){
			result.setError(ResultCode.CODE_STATE_4006, "密码不能为空");
			return result;
		}
		if(StringUtil.isEmpty(agentUserVo.getConfirmPwd())){
			result.setError(ResultCode.CODE_STATE_4006, "确认密码不能为空");
			return result;
		}
		if(!agentUserVo.getPwd().trim().equals(agentUserVo.getConfirmPwd().trim())){
			result.setError(ResultCode.CODE_STATE_4006, "密码和确认密码不一致");
			return result;
		}
		
		//2、处理添加逻辑
		//添加系统用户
		if(!StringUtil.isEmpty(agentUserVo.getPwd())){
			userVo.setPwd(MD5Encoder.encodeByMD5(agentUserVo.getPwd()));
		}
		userVo.setType(UserType.AGENT_USER);//代理商用户
		userVo.setUserName(agentUserVo.getUserName().trim());
		userVo.setRoleId(agentUserVo.getRoleId());
		userVo.setRoleName(agentUserVo.getRoleName());
		Boolean flag = this.userService.add(userVo);
		if(flag){
			agentUserVo.setUserId(userVo.getId());//保存系统用户的用户id
			agentUserVo.setCreateTime(new Date());
			if(!StringUtil.isEmpty(agentUserVo.getBirthdayStr())){
				agentUserVo.setBirthday(DateUtil.format(agentUserVo.getBirthdayStr(), "yyyy-MM-dd"));
			}
			flag = this.add(agentUserVo);
			if(flag){
				if(userVo.getRoleId() != null){
					flag = this.userService.addUserRole(userVo);
				}
				if(flag){
					result.setOK(ResultCode.CODE_STATE_200, "新增成功");
				}else{
					result.setError(ResultCode.CODE_STATE_4006, "保存用户角色失败！");
					throw new ServiceException("保存用户角色失败！");
				}
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "新增用户个人信息失败！");
				throw new ServiceException("新增用户个人信息失败！");
			}
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "新增用户登录信息失败！");
		}
		return result;
	}

	@Override
	public AgentUserVo getByUserId(Integer userId) {
		return this.agentUserDao.selectByUserId(userId);
	}

	@Override
	public Result editAgentUser(AgentUserVo agentUserVo) {
		Result result = new Result();
		Boolean flag = false;
		//1、信息有效性校验
		if(StringUtil.isNotEmpty(agentUserVo.getPwd())){
			if(!agentUserVo.getPwd().trim().equals(agentUserVo.getConfirmPwd().trim())){
				result.setError(ResultCode.CODE_STATE_4006, "密码和确认密码不一致");
				return result;
			}
		}
		UserVo userVo = new UserVo();
		//2、处理修改逻辑：修改用户资料以及角色对应关系
		//2.1、处理用户登录表
		userVo.setId(agentUserVo.getUserId());
		userVo.setRoleId(agentUserVo.getRoleId());
		userVo.setRoleName(agentUserVo.getRoleName());
		if(!StringUtil.isEmpty(agentUserVo.getPwd())){
			userVo.setPwd(MD5Encoder.encodeByMD5(agentUserVo.getPwd()));
		}
		flag = this.userService.edit(userVo);
		if(flag){
			if(!StringUtil.isEmpty(agentUserVo.getBirthdayStr())){
				agentUserVo.setBirthday(DateUtil.format(agentUserVo.getBirthdayStr(), "yyyy-MM-dd"));
			}
			flag = this.edit(agentUserVo);
			if(flag){
				this.userService.deleteRoleByUserId(userVo.getId());
				if(agentUserVo.getRoleId() != null){
					flag = this.userService.addUserRole(userVo);
					if(flag){
						result.setOK(ResultCode.CODE_STATE_200, "保存成功");
					}else{
						result.setError(ResultCode.CODE_STATE_4006, "保存用户角色信息失败！");
						throw new ServiceException("保存用户角色信息失败");
					}
				}
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "保存用户信息失败！");
				throw new ServiceException("保存用户信息失败");
			}
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "保存用户登录信息失败！");
		}
		
		return result;
	}

	@Override
	@Transactional
	public synchronized Result addAgent(AgentUserVo agentUserVo) {
		Result result = new Result();
		//信息校验
		if(StringUtil.isEmpty(agentUserVo.getUserName())){
			result.setError(ResultCode.CODE_STATE_4006, "用户名不能为空！");
			return result;
		}
		if(StringUtil.isEmpty(agentUserVo.getAgentName())){
			result.setError(ResultCode.CODE_STATE_4006, "门店名称不能为空！");
			return result;
		}
		if(StringUtil.isEmpty(agentUserVo.getAgentCode())){
			result.setError(ResultCode.CODE_STATE_4006, "门店编号不能为空！");
			return result;
		}
		if(agentUserVo.getIsAgent() == null){
			result.setError(ResultCode.CODE_STATE_4006, "门店类型不能为空！");
			return result;
		}
		if(StringUtil.isEmpty(agentUserVo.getContacter())){
			result.setError(ResultCode.CODE_STATE_4006, "联系人不能为空！");
			return result;
		}
		if(StringUtil.isEmpty(agentUserVo.getContactPhone())){
			result.setError(ResultCode.CODE_STATE_4006, "联系电话不能为空！");
			return result;
		}
		if(!VerificationUtils.isValid(agentUserVo.getContactPhone(), VerificationUtils.MOBILE)){
			result.setError(ResultCode.CODE_STATE_4006, "请输入有效的手机号码");
			return result;
		}
		if(StringUtil.isEmpty(agentUserVo.getProvince()) || agentUserVo.getProvinceId() == null
				|| StringUtil.isEmpty(agentUserVo.getCity()) || agentUserVo.getCityId() == null
				|| StringUtil.isEmpty(agentUserVo.getArea()) || agentUserVo.getAreaId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "所属地区不能为空！");
			return result;
		}
		if(StringUtil.isEmpty(agentUserVo.getPwd())){
			result.setError(ResultCode.CODE_STATE_4006, "密码不能为空");
			return result;
		}
		if(StringUtil.isEmpty(agentUserVo.getConfirmPwd())){
			result.setError(ResultCode.CODE_STATE_4006, "确认密码不能为空");
			return result;
		}
		if(!agentUserVo.getPwd().trim().equals(agentUserVo.getConfirmPwd().trim())){
			result.setError(ResultCode.CODE_STATE_4006, "密码和确认密码不一致");
			return result;
		}
		//添加前判断是否用户名存在
		UserVo userVo = new UserVo();
		userVo.setUserName(agentUserVo.getUserName());
		result = this.userService.validUserInfo(userVo);
		if(!result.isSuccess()){
			return result;
		}
		//信息保存
		agentUserVo.setRoleId(4);
		agentUserVo.setRoleName("代理商");
		if(StringUtil.isNotEmpty(agentUserVo.getBirthdayStr())){
			agentUserVo.setBirthday(DateUtil.format(agentUserVo.getBirthdayStr(), "yyyy-MM-dd"));
		}
		//添加系统用户
		userVo.setPwd(MD5Encoder.encodeByMD5(agentUserVo.getPwd()));	
		userVo.setType(UserType.AGENT_USER);//代理商用户
		userVo.setUserName(agentUserVo.getUserName().trim());
		userVo.setRoleId(agentUserVo.getRoleId());
		userVo.setRoleName(agentUserVo.getRoleName());
		Boolean flag = this.userService.add(userVo);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "新增用户登录信息失败！");
			return result;
		}
		//添加经销商用户信息
		agentUserVo.setUserId(userVo.getId());//保存系统用户的用户id
		agentUserVo.setCreateTime(new Date());
		flag = this.add(agentUserVo);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "新增用户个人信息失败！");
			throw new ServiceException("新增用户个人信息失败！");
		}
		//绑定用户角色关系
		flag = this.userService.addUserRole(userVo);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "保存用户角色失败！");
			throw new ServiceException("保存用户角色失败！");
		}
		result.setOK(ResultCode.CODE_STATE_200, "新增成功");
		return result;
	}

	@Override
	public Result updateAgent(AgentUserVo agentUserVo) {
		Result result = new Result();
		Boolean flag = false;
		//信息校验
		if(agentUserVo.getId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "经销商信息id不能为空！");
			return result;
		}
		if(StringUtil.isEmpty(agentUserVo.getAgentName())){
			result.setError(ResultCode.CODE_STATE_4006, "门店名称不能为空！");
			return result;
		}
		if(StringUtil.isEmpty(agentUserVo.getAgentCode())){
			result.setError(ResultCode.CODE_STATE_4006, "门店编号不能为空！");
			return result;
		}
		if(agentUserVo.getIsAgent() == null){
			result.setError(ResultCode.CODE_STATE_4006, "门店类型不能为空！");
			return result;
		}
		if(StringUtil.isEmpty(agentUserVo.getContacter())){
			result.setError(ResultCode.CODE_STATE_4006, "联系人不能为空！");
			return result;
		}
		if(StringUtil.isEmpty(agentUserVo.getContactPhone())){
			result.setError(ResultCode.CODE_STATE_4006, "联系电话不能为空！");
			return result;
		}
		if(!VerificationUtils.isValid(agentUserVo.getContactPhone(), VerificationUtils.MOBILE)){
			result.setError(ResultCode.CODE_STATE_4006, "请输入有效的手机号码");
			return result;
		}
		if(StringUtil.isEmpty(agentUserVo.getProvince()) || agentUserVo.getProvinceId() == null
				|| StringUtil.isEmpty(agentUserVo.getCity()) || agentUserVo.getCityId() == null
				|| StringUtil.isEmpty(agentUserVo.getArea()) || agentUserVo.getAreaId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "所属地区不能为空！");
			return result;
		}
		//如果修改密码
		if(StringUtil.isNotEmpty(agentUserVo.getPwd())){
			if(StringUtil.isEmpty(agentUserVo.getConfirmPwd())){
				result.setError(ResultCode.CODE_STATE_4006, "确认密码不能为空");
				return result;
			}
			if(!agentUserVo.getPwd().trim().equals(agentUserVo.getConfirmPwd().trim())){
				result.setError(ResultCode.CODE_STATE_4006, "密码和确认密码不一致");
				return result;
			}	
			if(agentUserVo.getUserId() != null){
				result.setError(ResultCode.CODE_STATE_4006, "经销商信息用户id不能为空！");
				return result;
			}
			UserVo userVo = new UserVo();
			userVo.setId(agentUserVo.getUserId());
			userVo.setPwd(MD5Encoder.encodeByMD5(agentUserVo.getPwd()));
			flag = this.userService.edit(userVo);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "修改用户密码失败！");
				return result;
			}
		}
		
		//信息保存
		if(StringUtil.isNotEmpty(agentUserVo.getBirthdayStr())){
			agentUserVo.setBirthday(DateUtil.format(agentUserVo.getBirthdayStr(), "yyyy-MM-dd"));
		}	
		//添加经销商用户信息
		flag = this.edit(agentUserVo);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "修改用户个人信息失败！");
			throw new ServiceException("修改用户个人信息失败！");
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		return result;
	}

}
