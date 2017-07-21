package com.aylson.dc.mem.dao;

import java.util.List;
import java.util.Map;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.dc.mem.po.MemAccount;
import com.aylson.dc.mem.search.MemAccountSearch;
import com.aylson.dc.mem.vo.MemAccountVo;

public interface MemAccountDao extends BaseDao<MemAccount,MemAccountSearch> {
	
	/**
	 * 根据wxOpenId查询微信用户-推荐人绑定关系
	 * @param wxOpenId
	 * @return
	 */
	public List<MemAccountVo> selectRelationByWxOpenId(String wxOpenId);
	
	/**
	 * 根据组合条件查询微信用户-推荐人绑定关系
	 * @param wxOpenId
	 * @return
	 */
	public List<MemAccountVo> selectRelation(Map<String, Object> params);
	
	/**
	 * 添加微信用户-推荐人绑定关系
	 * @param memAccount
	 * @return
	 */
	public Boolean insertReferralWxUser(MemAccount memAccount);
	
	/**
	 * 根据删除微信推荐关系
	 * @param id
	 * @return
	 */
	public Boolean deleteRelationById(Integer id);
	
	public List<String> selectWxOpenId();
	
	
}
