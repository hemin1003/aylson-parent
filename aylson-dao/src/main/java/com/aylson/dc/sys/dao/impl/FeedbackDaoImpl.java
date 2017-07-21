package com.aylson.dc.sys.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.sys.dao.FeedbackDao;
import com.aylson.dc.sys.po.Feedback;
import com.aylson.dc.sys.search.FeedbackSearch;

@Repository
public class FeedbackDaoImpl extends BaseDaoImpl<Feedback,FeedbackSearch> implements FeedbackDao {

	
}
