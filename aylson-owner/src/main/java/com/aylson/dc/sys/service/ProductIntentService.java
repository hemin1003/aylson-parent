package com.aylson.dc.sys.service;

import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.sys.po.ProductIntent;
import com.aylson.dc.sys.search.ProductIntentSearch;

public interface ProductIntentService extends BaseService<ProductIntent,ProductIntentSearch> {
	
	/**
	 * 安全门窗访问数+1
	 */
	public void accessCountDW();
	
	/**
	 * 健康厨房访问数+1
	 */
	public void accessCountHK();
}
