package com.aylson.dc.mem.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.mem.dao.GiftSendDao;
import com.aylson.dc.mem.po.GiftSend;
import com.aylson.dc.mem.search.GiftSendSearch;
import com.aylson.dc.mem.service.GiftSendService;

@Service
public class GiftSendServiceImpl extends BaseServiceImpl<GiftSend,GiftSendSearch> implements GiftSendService {
	
	private static final Logger logger = LoggerFactory.getLogger(GiftSendServiceImpl.class);
	
	@Autowired
	private GiftSendDao giftSendDao;
	
	@Override
	protected BaseDao<GiftSend,GiftSendSearch> getBaseDao() {
		return this.giftSendDao;
	}

	
}
