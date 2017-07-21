package com.aylson.dc.partner.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.partner.po.CouponApply;
import com.aylson.dc.partner.search.CouponApplySearch;
import com.aylson.dc.partner.vo.CouponApplyVo;

public interface CouponApplyService extends BaseService<CouponApply,CouponApplySearch> {
	
	/**
	 * 申请优惠券
	 * @param couponApplyVo
	 * @return
	 */
	public Result applyCoupon(CouponApplyVo couponApplyVo);
	
	
}
