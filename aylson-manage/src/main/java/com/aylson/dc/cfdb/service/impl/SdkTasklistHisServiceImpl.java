package com.aylson.dc.cfdb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.cfdb.dao.SdkTasklistHisDao;
import com.aylson.dc.cfdb.po.SdkTasklistHis;
import com.aylson.dc.cfdb.search.SdkTasklistHisSearch;
import com.aylson.dc.cfdb.service.SdkTasklistHisService;

@Service
public class SdkTasklistHisServiceImpl  extends BaseServiceImpl<SdkTasklistHis, SdkTasklistHisSearch> implements SdkTasklistHisService {

	@Autowired
	private SdkTasklistHisDao sdkTasklistHisDao;

	@Override
	protected BaseDao<SdkTasklistHis, SdkTasklistHisSearch> getBaseDao() {
		return sdkTasklistHisDao;
	}
}
