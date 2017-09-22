package com.aylson.dc.ytt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.ytt.dao.YttRateSetDao;
import com.aylson.dc.ytt.po.YttRateSet;
import com.aylson.dc.ytt.search.YttRateSetSearch;
import com.aylson.dc.ytt.service.YttRateSetService;

@Service
public class YttRateSetServiceImpl extends BaseServiceImpl<YttRateSet, YttRateSetSearch> implements YttRateSetService {

	@Autowired
	private YttRateSetDao rateSetDao;

	@Override
	protected BaseDao<YttRateSet, YttRateSetSearch> getBaseDao() {
		return rateSetDao;
	}
}
