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
import com.aylson.dc.sys.dao.OrgUserDao;
import com.aylson.dc.sys.po.OrgUser;
import com.aylson.dc.sys.search.OrgUserSearch;
import com.aylson.dc.sys.service.OrgUserService;
import com.aylson.dc.sys.service.UserService;
import com.aylson.dc.sys.vo.OrgUserVo;
import com.aylson.dc.sys.vo.UserVo;
import com.aylson.utils.DateUtil;
import com.aylson.utils.MD5Encoder;
import com.aylson.utils.StringUtil;


@Service
public class OrgUserServiceImpl extends BaseServiceImpl<OrgUser,OrgUserSearch> implements OrgUserService {

	@Autowired
	private OrgUserDao orgUserDao;
	@Autowired
	private UserService userService;           //系统用户服务

	@Override
	protected BaseDao<OrgUser,OrgUserSearch> getBaseDao() {
		return orgUserDao;
	}

	@Override
	@Transactional
	public Result addOrgUser(OrgUserVo orgUserVo) {
		Result result = new Result();
		//1、信息有效性校验
		//添加前判断是否用户名存在
		UserVo userVo = new UserVo();
		userVo.setUserName(orgUserVo.getUserName());
		result = this.userService.validUserInfo(userVo);
		if(!result.isSuccess()){
			return result;
		}
		if(StringUtil.isEmpty(orgUserVo.getPwd())){
			result.setError(ResultCode.CODE_STATE_4006, "密码不能为空");
			return result;
		}
		if(StringUtil.isEmpty(orgUserVo.getConfirmPwd())){
			result.setError(ResultCode.CODE_STATE_4006, "确认密码不能为空");
			return result;
		}
		if(!orgUserVo.getPwd().trim().equals(orgUserVo.getConfirmPwd().trim())){
			result.setError(ResultCode.CODE_STATE_4006, "密码和确认密码不一致");
			return result;
		}
		//2、处理添加逻辑
		//添加系统用户
		if(!StringUtil.isEmpty(orgUserVo.getPwd())){
			userVo.setPwd(MD5Encoder.encodeByMD5(orgUserVo.getPwd()));
		}
		userVo.setType(UserType.ORG_USER);//组织机构
		userVo.setUserName(orgUserVo.getUserName().trim());
		userVo.setRoleId(orgUserVo.getRoleId());
		userVo.setRoleName(orgUserVo.getRoleName());
		Boolean flag = this.userService.add(userVo);
		if(flag){
			orgUserVo.setUserId(userVo.getId());//保存系统用户的用户id
			orgUserVo.setCreateTime(new Date());
			if(!StringUtil.isEmpty(orgUserVo.getBirthdayStr())){
				orgUserVo.setBirthday(DateUtil.format(orgUserVo.getBirthdayStr(), "yyyy-MM-dd"));
			}
			flag = this.add(orgUserVo);
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
	public OrgUserVo getByUserId(Integer userId) {
		return this.orgUserDao.selectByUserId(userId);
	}

	@Override
	@Transactional
	public Result editOrgUser(OrgUserVo orgUserVo) {
		Result result = new Result();
		Boolean flag = false;
		//1、信息有效性校验
		if(StringUtil.isNotEmpty(orgUserVo.getPwd())){
			if(!orgUserVo.getPwd().trim().equals(orgUserVo.getConfirmPwd().trim())){
				result.setError(ResultCode.CODE_STATE_4006, "密码和确认密码不一致");
				return result;
			}
		}
		UserVo userVo = new UserVo();
		
		//2、处理修改逻辑：修改用户资料以及角色对应关系
		//2.1、处理用户登录表
		userVo.setId(orgUserVo.getUserId());
		userVo.setRoleId(orgUserVo.getRoleId());
		userVo.setRoleName(orgUserVo.getRoleName());
		if(!StringUtil.isEmpty(orgUserVo.getPwd())){
			userVo.setPwd(MD5Encoder.encodeByMD5(orgUserVo.getPwd()));
		}
		flag = this.userService.edit(userVo);
		if(flag){
			if(!StringUtil.isEmpty(orgUserVo.getBirthdayStr())){
				orgUserVo.setBirthday(DateUtil.format(orgUserVo.getBirthdayStr(), "yyyy-MM-dd"));
			}
			flag = this.edit(orgUserVo);
			if(flag){
				this.userService.deleteRoleByUserId(userVo.getId());
				if(orgUserVo.getRoleId() != null){
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
