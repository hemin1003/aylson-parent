package com.aylson.dc.sys.dao;

import java.util.List;
import java.util.Map;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.dc.sys.po.User;
import com.aylson.dc.sys.search.UserSearch;

/**
 * 系统用户操作类接口
 */
public interface UserDao extends BaseDao<User,UserSearch> {
	
	/**
	 * 批量插入角色用户
	 * @param roleUserList
	 * @return
	 */
	public Boolean batchInsertRoleUser(List<Map<String, Object>> roleUserList);
	
	/**
	 * 添加角色用户关系
	 * @param user
	 * @return
	 */
	public Boolean insertRoleUser(User user);
	
	/**
	 * 根据用户id删除用户角色关系
	 * @param userId
	 * @return
	 */
	public Boolean deleteRoleByUserId(Integer userId);
	
}
