package com.aylson.dc.mem.service;

import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.mem.po.RechargeRecords;
import com.aylson.dc.mem.search.RechargeRecordsSearch;
import com.aylson.dc.mem.vo.RechargeRecordsVo;

public interface RechargeRecordsService extends BaseService<RechargeRecords,RechargeRecordsSearch> {
	
	public RechargeRecordsVo getByOrderNo(String orderNo);
	
}
