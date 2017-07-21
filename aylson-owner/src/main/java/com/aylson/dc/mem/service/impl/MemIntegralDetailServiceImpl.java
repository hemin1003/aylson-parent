package com.aylson.dc.mem.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.mem.dao.MemIntegralDetailDao;
import com.aylson.dc.mem.po.MemIntegralDetail;
import com.aylson.dc.mem.search.MemIntegralDetailSearch;
import com.aylson.dc.mem.service.MemIntegralDetailService;

@Service
public class MemIntegralDetailServiceImpl extends BaseServiceImpl<MemIntegralDetail,MemIntegralDetailSearch> implements MemIntegralDetailService {
	
	private static final Logger logger = LoggerFactory.getLogger(MemIntegralDetailServiceImpl.class);
	
	@Autowired
	private MemIntegralDetailDao memIntegralDetailDao;
	
	@Override
	protected BaseDao<MemIntegralDetail,MemIntegralDetailSearch> getBaseDao() {
		return this.memIntegralDetailDao;
	}

	
	
}
