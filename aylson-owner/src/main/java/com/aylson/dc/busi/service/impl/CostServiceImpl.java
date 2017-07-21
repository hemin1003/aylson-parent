package com.aylson.dc.busi.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.busi.dao.CostDao;
import com.aylson.dc.busi.po.Cost;
import com.aylson.dc.busi.search.CostSearch;
import com.aylson.dc.busi.service.CostService;


@Service
public class CostServiceImpl extends BaseServiceImpl<Cost,CostSearch> implements CostService {

	@Autowired
	private CostDao costDao;

	@Override
	protected BaseDao<Cost,CostSearch> getBaseDao() {
		return costDao;
	}


}
