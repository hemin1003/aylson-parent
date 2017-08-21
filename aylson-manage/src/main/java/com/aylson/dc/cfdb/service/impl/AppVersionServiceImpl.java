package com.aylson.dc.cfdb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.cfdb.dao.AppVersionDao;
import com.aylson.dc.cfdb.po.AppVersion;
import com.aylson.dc.cfdb.search.AppVersionSearch;
import com.aylson.dc.cfdb.service.AppVersionService;

@Service
public class AppVersionServiceImpl  extends BaseServiceImpl<AppVersion, AppVersionSearch> implements AppVersionService {

	@Autowired
	private AppVersionDao appVersionDao;

	@Override
	protected BaseDao<AppVersion, AppVersionSearch> getBaseDao() {
		return appVersionDao;
	}
}
