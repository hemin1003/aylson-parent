package com.aylson.dc.qmtt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.qmtt.dao.RateSetDao;
import com.aylson.dc.qmtt.po.RateSet;
import com.aylson.dc.qmtt.search.RateSetSearch;
import com.aylson.dc.qmtt.service.RateSetService;

@Service
public class RateSetServiceImpl  extends BaseServiceImpl<RateSet, RateSetSearch> implements RateSetService {

	@Autowired
	private RateSetDao rateSetDao;

	@Override
	protected BaseDao<RateSet, RateSetSearch> getBaseDao() {
		return rateSetDao;
	}
}
