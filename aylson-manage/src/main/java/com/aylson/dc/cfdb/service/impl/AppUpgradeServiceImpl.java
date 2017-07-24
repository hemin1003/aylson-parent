package com.aylson.dc.cfdb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.cfdb.dao.AppUpgradeDao;
import com.aylson.dc.cfdb.po.AppUpgrade;
import com.aylson.dc.cfdb.search.AppUpgradeSearch;
import com.aylson.dc.cfdb.service.AppUpgradeService;

@Service
public class AppUpgradeServiceImpl extends BaseServiceImpl<AppUpgrade, AppUpgradeSearch> implements AppUpgradeService {

	@Autowired
	private AppUpgradeDao appUpgrade;

	@Override
	protected BaseDao<AppUpgrade, AppUpgradeSearch> getBaseDao() {
		return appUpgrade;
	}
}
