package com.aylson.dc.mem.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.mem.dao.GiftSendDetailDao;
import com.aylson.dc.mem.po.GiftSendDetail;
import com.aylson.dc.mem.search.GiftSendDetailSearch;
import com.aylson.dc.mem.service.GiftSendDetailService;

@Service
public class GiftSendDetailServiceImpl extends BaseServiceImpl<GiftSendDetail,GiftSendDetailSearch> implements GiftSendDetailService {
	
	private static final Logger logger = LoggerFactory.getLogger(GiftSendDetailServiceImpl.class);
	
	@Autowired
	private GiftSendDetailDao giftSendDetailDao;
	
	@Override
	protected BaseDao<GiftSendDetail,GiftSendDetailSearch> getBaseDao() {
		return this.giftSendDetailDao;
	}

	
}
