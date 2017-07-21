package com.aylson.dc.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.sys.dao.CouponActivityDao;
import com.aylson.dc.sys.po.CouponActivity;
import com.aylson.dc.sys.search.CouponActivitySearch;
import com.aylson.dc.sys.service.CouponActivityService;


@Service
public class CouponActivityServiceImpl extends BaseServiceImpl<CouponActivity, CouponActivitySearch> implements CouponActivityService {

	@Autowired
	private CouponActivityDao couponActivityDao;

	@Override
	protected BaseDao<CouponActivity, CouponActivitySearch> getBaseDao() {
		return couponActivityDao;
	}
}
