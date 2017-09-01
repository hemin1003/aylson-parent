package com.aylson.dc.qmtt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.qmtt.dao.QmttAppVersionDao;
import com.aylson.dc.qmtt.po.QmttAppVersion;
import com.aylson.dc.qmtt.search.QmttAppVersionSearch;
import com.aylson.dc.qmtt.service.QmttAppVersionService;

@Service
public class QmttAppVersionServiceImpl  extends BaseServiceImpl<QmttAppVersion, QmttAppVersionSearch> implements QmttAppVersionService {

	@Autowired
	private QmttAppVersionDao qmttAppVersionDao;

	@Override
	protected BaseDao<QmttAppVersion, QmttAppVersionSearch> getBaseDao() {
		return qmttAppVersionDao;
	}
}
