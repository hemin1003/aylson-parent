package com.aylson.dc.qmtt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.qmtt.dao.QmttAppConfigDao;
import com.aylson.dc.qmtt.po.QmttAppConfig;
import com.aylson.dc.qmtt.search.QmttAppConfigSearch;
import com.aylson.dc.qmtt.service.QmttAppConfigService;

@Service
public class QmttAppConfigServiceImpl  extends BaseServiceImpl<QmttAppConfig, QmttAppConfigSearch> implements QmttAppConfigService {

	@Autowired
	private QmttAppConfigDao appConfigDao;

	@Override
	protected BaseDao<QmttAppConfig, QmttAppConfigSearch> getBaseDao() {
		return appConfigDao;
	}
}
