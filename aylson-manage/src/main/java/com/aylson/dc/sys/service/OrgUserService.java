package com.aylson.dc.sys.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.sys.po.OrgUser;
import com.aylson.dc.sys.search.OrgUserSearch;
import com.aylson.dc.sys.vo.OrgUserVo;

public interface OrgUserService extends BaseService<OrgUser,OrgUserSearch> {
	
	/**
	 * 添加机构用户信息
	 * @param orgUserVo
	 * @return
	 */
	public Result addOrgUser(OrgUserVo orgUserVo);
	
	/**
	 * 修改机构用户信息
	 * @param orgUserVo
	 * @return
	 */
	public Result editOrgUser(OrgUserVo orgUserVo);
	
	/**
	 * 根据用户id查询
	 * @param userId
	 * @return
	 */
	public OrgUserVo getByUserId(Integer userId);
			
}
