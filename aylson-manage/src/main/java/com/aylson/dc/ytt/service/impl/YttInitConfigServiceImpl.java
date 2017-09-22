package com.aylson.dc.ytt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.ytt.dao.YttInitConfigDao;
import com.aylson.dc.ytt.po.YttInitConfig;
import com.aylson.dc.ytt.search.YttInitConfigSearch;
import com.aylson.dc.ytt.service.YttInitConfigService;

@Service
public class YttInitConfigServiceImpl  extends BaseServiceImpl<YttInitConfig, YttInitConfigSearch> implements YttInitConfigService {

	@Autowired
	private YttInitConfigDao initConfigDao;

	@Override
	protected BaseDao<YttInitConfig, YttInitConfigSearch> getBaseDao() {
		return initConfigDao;
	}
}
