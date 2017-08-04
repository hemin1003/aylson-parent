package com.aylson.dc.cfdb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.cfdb.dao.UserFeedbackDao;
import com.aylson.dc.cfdb.po.UserFeedback;
import com.aylson.dc.cfdb.search.UserFeedbackSearch;
import com.aylson.dc.cfdb.service.UserFeedbackService;

@Service
public class UserFeedbackServiceImpl  extends BaseServiceImpl<UserFeedback, UserFeedbackSearch> implements UserFeedbackService {

	@Autowired
	private UserFeedbackDao userFeedbackDao;

	@Override
	protected BaseDao<UserFeedback, UserFeedbackSearch> getBaseDao() {
		return userFeedbackDao;
	}
}
