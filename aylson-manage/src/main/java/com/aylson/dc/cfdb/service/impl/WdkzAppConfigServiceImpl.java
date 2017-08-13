package com.aylson.dc.cfdb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.cfdb.dao.WdkzAppConfigDao;
import com.aylson.dc.cfdb.po.WdkzAppConfig;
import com.aylson.dc.cfdb.search.WdkzAppConfigSearch;
import com.aylson.dc.cfdb.service.WdkzAppConfigService;

@Service
public class WdkzAppConfigServiceImpl  extends BaseServiceImpl<WdkzAppConfig, WdkzAppConfigSearch> implements WdkzAppConfigService {

	@Autowired
	private WdkzAppConfigDao wdkzAppConfigDao;

	@Override
	protected BaseDao<WdkzAppConfig, WdkzAppConfigSearch> getBaseDao() {
		return wdkzAppConfigDao;
	}
}
