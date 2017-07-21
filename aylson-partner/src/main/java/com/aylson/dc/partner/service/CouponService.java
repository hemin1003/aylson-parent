package com.aylson.dc.partner.service;

import java.util.Date;
import java.util.List;

import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.partner.po.Coupon;
import com.aylson.dc.partner.search.CouponSearch;
import com.aylson.dc.partner.vo.CouponVo;

public interface CouponService extends BaseService<Coupon,CouponSearch> {
	
	/**
	 * 获取优惠券号
	 * @param prefix
	 * @param date
	 * @param applyId
	 * @return
	 */
	public String getCouponCode(String prefix,Date date,Integer applyId);
	
	/**
	 * 获取发送优惠券列表
	 * @param applyId
	 * @param configId
	 * @param applierId
	 * @return
	 */
	public List<CouponVo> getSendList(Integer applyId, Integer configId, Integer applierId, Integer applyNum);
	
	
}
