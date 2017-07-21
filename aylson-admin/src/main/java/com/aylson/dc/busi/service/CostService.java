package com.aylson.dc.busi.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.busi.po.Cost;
import com.aylson.dc.busi.search.CostSearch;

public interface CostService extends BaseService<Cost,CostSearch> {
	
	/**
	 * 初始化预约费用信息
	 * @return
	 */
	public Result initAppointCost(Integer sourceType, Integer sourceId);
	
}
