package com.aylson.dc.mem.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.mem.dao.MemWalletDetailDao;
import com.aylson.dc.mem.po.MemWalletDetail;
import com.aylson.dc.mem.search.MemWalletDetailSearch;
import com.aylson.dc.mem.service.MemWalletDetailService;

@Service
public class MemWalletDetailServiceImpl extends BaseServiceImpl<MemWalletDetail,MemWalletDetailSearch> implements MemWalletDetailService {
	
	private static final Logger logger = LoggerFactory.getLogger(MemWalletDetailServiceImpl.class);
	
	@Autowired
	private MemWalletDetailDao memWalletDetailDao;
	
	@Override
	protected BaseDao<MemWalletDetail,MemWalletDetailSearch> getBaseDao() {
		return this.memWalletDetailDao;
	}

	
	
}
