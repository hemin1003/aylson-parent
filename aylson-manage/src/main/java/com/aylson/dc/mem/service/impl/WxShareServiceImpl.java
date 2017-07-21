package com.aylson.dc.mem.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.mem.dao.WxShareDao;
import com.aylson.dc.mem.po.WxShare;
import com.aylson.dc.mem.search.WxShareSearch;
import com.aylson.dc.mem.service.WxShareService;

@Service
public class WxShareServiceImpl extends BaseServiceImpl<WxShare,WxShareSearch> implements WxShareService {
	
	private static final Logger logger = LoggerFactory.getLogger(WxShareServiceImpl.class);
	
	@Autowired
	private WxShareDao wxShareDao;
	
	@Override
	protected BaseDao<WxShare,WxShareSearch> getBaseDao() {
		return this.wxShareDao;
	}

	
}
