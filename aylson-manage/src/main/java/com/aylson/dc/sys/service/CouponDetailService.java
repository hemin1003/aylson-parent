package com.aylson.dc.sys.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.sys.po.CouponDetail;
import com.aylson.dc.sys.search.CouponDetailSearch;

public interface CouponDetailService extends BaseService<CouponDetail, CouponDetailSearch> {
	
	public Result addDetailAndCouponRule(CouponDetail couponDetail, String[] startPrice, String[] endPrice,
			String[] deratePrice);
	
	public Result updateDetailAndCouponRule(CouponDetail couponDetail, String[] startPrice, String[] endPrice,
			String[] deratePrice, String[] ruleId);
	
}
