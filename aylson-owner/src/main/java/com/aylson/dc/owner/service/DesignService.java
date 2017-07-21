package com.aylson.dc.owner.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.owner.po.Design;
import com.aylson.dc.owner.search.DesignSearch;

public interface DesignService extends BaseService<Design,DesignSearch> {
	
	
	/**
	 * 客户确认报价
	 * @param designId
	 * @param isSatisfy
	 * @return
	 */
	public Result confirmQuotation(Integer designId, Boolean isSatisfy);
	
	/**
	 * 客户确认所有报价
	 * @param designId
	 * @param isSatisfy
	 * @return
	 */
	public Result confirmAllQuotation(Integer appointId, Boolean isSatisfy);
	
	/**
	 * 客户确认所有报价
	 * @param designId
	 * @param isSatisfy
	 * @return
	 */
	public Result confirmAllQuotation(Integer appointId, Integer couponId, Integer couponUserId, Boolean isSatisfy);
	
}
