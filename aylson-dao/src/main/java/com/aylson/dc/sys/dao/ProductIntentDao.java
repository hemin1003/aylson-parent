package com.aylson.dc.sys.dao;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.dc.sys.po.ProductIntent;
import com.aylson.dc.sys.search.ProductIntentSearch;

public interface ProductIntentDao extends BaseDao<ProductIntent,ProductIntentSearch> {
	
	/**
	 * 安全门窗访问数+1
	 */
	public void accessCountDW();
	
	/**
	 * 健康厨房访问数+1
	 */
	public void accessCountHK();
	
}
