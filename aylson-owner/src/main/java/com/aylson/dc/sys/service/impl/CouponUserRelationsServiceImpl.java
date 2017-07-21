package com.aylson.dc.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.sys.dao.CouponUserRelationsDao;
import com.aylson.dc.sys.po.CouponUserRelations;
import com.aylson.dc.sys.search.CouponUserRelationsSearch;
import com.aylson.dc.sys.service.CouponUserRelationsService;

@Service
public class CouponUserRelationsServiceImpl extends BaseServiceImpl<CouponUserRelations, CouponUserRelationsSearch>
		implements CouponUserRelationsService {

	@Autowired
	private CouponUserRelationsDao CouponUserRelationsDao;

	@Override
	protected BaseDao<CouponUserRelations, CouponUserRelationsSearch> getBaseDao() {
		return CouponUserRelationsDao;
	}
}
