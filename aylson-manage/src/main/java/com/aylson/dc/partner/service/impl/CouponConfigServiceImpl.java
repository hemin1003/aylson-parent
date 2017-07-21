package com.aylson.dc.partner.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.partner.dao.CouponConfigDao;
import com.aylson.dc.partner.po.CouponConfig;
import com.aylson.dc.partner.search.CouponConfigSearch;
import com.aylson.dc.partner.service.CouponConfigService;


@Service
public class CouponConfigServiceImpl extends BaseServiceImpl<CouponConfig,CouponConfigSearch> implements CouponConfigService {

	@Autowired
	private CouponConfigDao couponConfigDao;

	@Override
	protected BaseDao<CouponConfig,CouponConfigSearch> getBaseDao() {
		return couponConfigDao;
	}
	

}
