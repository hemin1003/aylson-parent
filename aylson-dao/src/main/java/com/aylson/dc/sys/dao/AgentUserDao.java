package com.aylson.dc.sys.dao;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.dc.sys.po.AgentUser;
import com.aylson.dc.sys.search.AgentUserSearch;
import com.aylson.dc.sys.vo.AgentUserVo;

/**
 * 系统用户操作类接口
 */
public interface AgentUserDao extends BaseDao<AgentUser,AgentUserSearch> {
	
	/**
	 * 根据用户id查询
	 * @param userId
	 * @return
	 */
	public AgentUserVo selectByUserId(Integer userId);
	
	
}
