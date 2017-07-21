package com.aylson.dc.mem.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.mem.dao.MemGoldDetailDao;
import com.aylson.dc.mem.po.MemGoldDetail;
import com.aylson.dc.mem.search.MemGoldDetailSearch;
import com.aylson.dc.mem.service.MemGoldDetailService;

@Service
public class MemGoldDetailServiceImpl extends BaseServiceImpl<MemGoldDetail,MemGoldDetailSearch> implements MemGoldDetailService {
	
	private static final Logger logger = LoggerFactory.getLogger(MemGoldDetailServiceImpl.class);
	
	@Autowired
	private MemGoldDetailDao memGoldDetailDao;
	
	@Override
	protected BaseDao<MemGoldDetail,MemGoldDetailSearch> getBaseDao() {
		return this.memGoldDetailDao;
	}

	
	
}
