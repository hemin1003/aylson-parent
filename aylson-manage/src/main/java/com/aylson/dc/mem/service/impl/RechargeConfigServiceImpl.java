package com.aylson.dc.mem.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.mem.dao.RechargeConfigDao;
import com.aylson.dc.mem.po.RechargeConfig;
import com.aylson.dc.mem.search.RechargeConfigSearch;
import com.aylson.dc.mem.service.RechargeConfigService;

@Service
public class RechargeConfigServiceImpl extends BaseServiceImpl<RechargeConfig,RechargeConfigSearch> implements RechargeConfigService {
	
	private static final Logger logger = LoggerFactory.getLogger(RechargeConfigServiceImpl.class);
	
	@Autowired
	private RechargeConfigDao rechargeConfigDao;
	
	@Override
	protected BaseDao<RechargeConfig,RechargeConfigSearch> getBaseDao() {
		return this.rechargeConfigDao;
	}


	
	
}
