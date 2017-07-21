package com.aylson.dc.partner.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.partner.po.CouponApply;
import com.aylson.dc.partner.search.CouponApplySearch;

public interface CouponApplyService extends BaseService<CouponApply,CouponApplySearch> {
	
	/**
	 * 审核优惠券申请
	 * @param id
	 * @param isPass
	 * @return
	 */
	public Result verify(Integer id, Boolean isPass);
	
	/**
	 * 根据客户电话返回绑定信息
	 * @param ownerPhone
	 * @return
	 */
	public Result isBond(String ownerPhone);
	

}
