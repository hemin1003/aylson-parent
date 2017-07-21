package com.aylson.dc.sys.service;

import java.util.List;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.dc.sys.po.User;
import com.aylson.dc.sys.search.UserSearch;
import com.aylson.dc.sys.vo.UserVo;

public interface UserService extends BaseService<User,UserSearch> {
	
	/**
	 * 获取同步树
	 * @param list
	 * @param parentId
	 * @return
	 */
	public List<UserVo> getSyncGridTree(List<UserVo> list, Integer parentId);
	
	/**
	 * 添加注册用户
	 * @param userVo
	 * @return
	 */
	public Result addRegisterUser(UserVo userVo);
	
	/**
	 * 根据公司类型id 批量插入角色用户
	 * @param params
	 * @return
	 */
	public Boolean batchAddRoleUserByType(Integer userId, String companyTypeIds);
	
	/**
	 * 添加子账号
	 * @param userVo
	 * @return
	 */
	public Result addChildAccount(UserVo userVo, SessionInfo sessionInfo );
	
	/**
	 * 通用信息有效性校验
	 * @param userVo
	 * @return
	 */
	public Result commonValid(UserVo userVo);
	
	/**
	 * 校验用户信息
	 * @param userVo
	 * @return
	 */
	public Result validUserInfo(UserVo userVo);
	
	/**
	 * 添加用户角色
	 */
	public Boolean addUserRole(UserVo userVo);
	
	
	/**
	 * 根据用户id删除用户角色关系
	 * @param userId
	 * @return
	 */
	public Boolean deleteRoleByUserId(Integer userId);
	
	
	
}
