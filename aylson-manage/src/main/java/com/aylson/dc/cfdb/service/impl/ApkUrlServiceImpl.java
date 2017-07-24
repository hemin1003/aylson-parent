package com.aylson.dc.cfdb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.cfdb.dao.ApkUrlDao;
import com.aylson.dc.cfdb.po.ApkUrl;
import com.aylson.dc.cfdb.search.ApkUrlSearch;
import com.aylson.dc.cfdb.service.ApkUrlService;

@Service
public class ApkUrlServiceImpl  extends BaseServiceImpl<ApkUrl, ApkUrlSearch> implements ApkUrlService {

	@Autowired
	private ApkUrlDao apkUrlDao;

	@Override
	protected BaseDao<ApkUrl, ApkUrlSearch> getBaseDao() {
		return apkUrlDao;
	}
}
