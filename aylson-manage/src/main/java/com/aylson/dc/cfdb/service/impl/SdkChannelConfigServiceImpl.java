package com.aylson.dc.cfdb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.cfdb.dao.SdkChannelConfigDao;
import com.aylson.dc.cfdb.po.SdkChannelConfig;
import com.aylson.dc.cfdb.search.SdkChannelConfigSearch;
import com.aylson.dc.cfdb.service.SdkChannelConfigService;

@Service
public class SdkChannelConfigServiceImpl  extends BaseServiceImpl<SdkChannelConfig, SdkChannelConfigSearch> implements SdkChannelConfigService {

	@Autowired
	private SdkChannelConfigDao sdkChannelConfigDao;

	@Override
	protected BaseDao<SdkChannelConfig, SdkChannelConfigSearch> getBaseDao() {
		return sdkChannelConfigDao;
	}
}
