package com.aylson.dc.partner.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.partner.dao.CouponDao;
import com.aylson.dc.partner.po.Coupon;
import com.aylson.dc.partner.search.CouponSearch;
import com.aylson.dc.partner.service.CouponService;


@Service
public class CouponServiceImpl extends BaseServiceImpl<Coupon,CouponSearch> implements CouponService {

	@Autowired
	private CouponDao couponDao;

	@Override
	protected BaseDao<Coupon,CouponSearch> getBaseDao() {
		return couponDao;
	}


}
