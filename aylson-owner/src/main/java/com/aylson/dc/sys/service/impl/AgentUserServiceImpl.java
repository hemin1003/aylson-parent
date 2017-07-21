package com.aylson.dc.sys.service.impl;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.exception.ServiceException;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.base.GeneralConstant.UserType;
import com.aylson.dc.sys.dao.AgentUserDao;
import com.aylson.dc.sys.po.AgentUser;
import com.aylson.dc.sys.search.AgentUserSearch;
import com.aylson.dc.sys.service.AgentUserService;
import com.aylson.dc.sys.service.UserService;
import com.aylson.dc.sys.vo.AgentUserVo;
import com.aylson.dc.sys.vo.UserVo;
import com.aylson.utils.DateUtil;
import com.aylson.utils.MD5Encoder;
import com.aylson.utils.StringUtil;


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
				}
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "新增用户个人信息失败！");
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

	

}
