package com.aylson.dc.mem.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.mem.dao.FeedBackReplyDao;
import com.aylson.dc.mem.po.FeedBackReply;
import com.aylson.dc.mem.search.FeedBackReplySearch;
import com.aylson.dc.mem.service.FeedBackReplyService;

@Service
public class FeedBackReplyServiceImpl extends BaseServiceImpl<FeedBackReply,FeedBackReplySearch> implements FeedBackReplyService {
	
	private static final Logger logger = LoggerFactory.getLogger(FeedBackReplyServiceImpl.class);
	
	@Autowired
	private FeedBackReplyDao feedBackReplyDao;
	
	@Override
	protected BaseDao<FeedBackReply,FeedBackReplySearch> getBaseDao() {
		return this.feedBackReplyDao;
	}

	
}
