package com.aylson.dc.mem.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.mem.FeedBackService;
import com.aylson.dc.mem.dao.FeedBackDao;
import com.aylson.dc.mem.po.FeedBack;
import com.aylson.dc.mem.search.FeedBackSearch;

@Service
public class FeedBackServiceImpl extends BaseServiceImpl<FeedBack,FeedBackSearch> implements FeedBackService {
	
	private static final Logger logger = LoggerFactory.getLogger(FeedBackServiceImpl.class);
	
	@Autowired
	private FeedBackDao feedBackDao;
	
	@Override
	protected BaseDao<FeedBack,FeedBackSearch> getBaseDao() {
		return this.feedBackDao;
	}

	
	
}
