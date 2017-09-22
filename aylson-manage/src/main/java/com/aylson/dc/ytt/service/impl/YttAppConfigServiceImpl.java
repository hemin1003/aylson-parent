package com.aylson.dc.ytt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.ytt.dao.YttAppConfigDao;
import com.aylson.dc.ytt.po.YttAppConfig;
import com.aylson.dc.ytt.search.YttAppConfigSearch;
import com.aylson.dc.ytt.service.YttAppConfigService;

@Service
public class YttAppConfigServiceImpl  extends BaseServiceImpl<YttAppConfig, YttAppConfigSearch> implements YttAppConfigService {

	@Autowired
	private YttAppConfigDao appConfigDao;

	@Override
	protected BaseDao<YttAppConfig, YttAppConfigSearch> getBaseDao() {
		return appConfigDao;
	}
}
