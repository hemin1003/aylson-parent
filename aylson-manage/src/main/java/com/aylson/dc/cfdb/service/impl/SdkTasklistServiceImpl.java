package com.aylson.dc.cfdb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.cfdb.dao.SdkTasklistDao;
import com.aylson.dc.cfdb.po.SdkTasklist;
import com.aylson.dc.cfdb.search.SdkTasklistSearch;
import com.aylson.dc.cfdb.service.SdkTasklistService;

@Service
public class SdkTasklistServiceImpl  extends BaseServiceImpl<SdkTasklist, SdkTasklistSearch> implements SdkTasklistService {

	@Autowired
	private SdkTasklistDao sdkTasklistDao;

	@Override
	protected BaseDao<SdkTasklist, SdkTasklistSearch> getBaseDao() {
		return sdkTasklistDao;
	}
}
