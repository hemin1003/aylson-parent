package com.aylson.dc.qmtt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.qmtt.dao.InitConfigDao;
import com.aylson.dc.qmtt.po.InitConfig;
import com.aylson.dc.qmtt.search.InitConfigSearch;
import com.aylson.dc.qmtt.service.InitConfigService;

@Service
public class InitConfigServiceImpl  extends BaseServiceImpl<InitConfig, InitConfigSearch> implements InitConfigService {

	@Autowired
	private InitConfigDao initConfigDao;

	@Override
	protected BaseDao<InitConfig, InitConfigSearch> getBaseDao() {
		return initConfigDao;
	}
}
