package com.aylson.dc.sys.dao;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.dc.sys.po.OrgUser;
import com.aylson.dc.sys.search.OrgUserSearch;
import com.aylson.dc.sys.vo.OrgUserVo;

/**
 * 系统用户操作类接口
 */
public interface OrgUserDao extends BaseDao<OrgUser,OrgUserSearch> {
	
	/**
	 * 根据用户id查询
	 * @param userId
	 * @return
	 */
	public OrgUserVo selectByUserId(Integer userId);
	
	
}
