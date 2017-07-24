package com.aylson.dc.cfdb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.cfdb.dao.AppConfigDao;
import com.aylson.dc.cfdb.po.AppConfig;
import com.aylson.dc.cfdb.search.AppConfigSearch;
import com.aylson.dc.cfdb.service.AppConfigService;

@Service
public class AppConfigServiceImpl  extends BaseServiceImpl<AppConfig, AppConfigSearch> implements AppConfigService {

	@Autowired
	private AppConfigDao appConfigDao;

	@Override
	protected BaseDao<AppConfig, AppConfigSearch> getBaseDao() {
		return appConfigDao;
	}
}
