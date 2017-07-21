package com.aylson.dc.partner.dao;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.dc.partner.po.Coupon;
import com.aylson.dc.partner.search.CouponSearch;

public interface CouponDao extends BaseDao<Coupon,CouponSearch> {
	
	/**
	 * 更新过期的优惠券为失效状态
	 */
	public void updateCouponInvalid();
	
	/**
	 * 将已过期的申请解绑
	 */
	public void unbundCouponApply();
	
	/**
	 * 查询最后优惠券号
	 * @return
	 */
	public String selectLastCouponCode();
	
	
}
