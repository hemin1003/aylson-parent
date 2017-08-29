package com.aylson.dc.qmtt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.qmtt.dao.QmttUserFeedbackDao;
import com.aylson.dc.qmtt.po.QmttUserFeedback;
import com.aylson.dc.qmtt.search.QmttUserFeedbackSearch;
import com.aylson.dc.qmtt.service.QmttUserFeedbackService;

@Service
public class QmttUserFeedbackServiceImpl  extends BaseServiceImpl<QmttUserFeedback, QmttUserFeedbackSearch> implements QmttUserFeedbackService {

	@Autowired
	private QmttUserFeedbackDao qmttUserFeedbackDao;

	@Override
	protected BaseDao<QmttUserFeedback, QmttUserFeedbackSearch> getBaseDao() {
		return qmttUserFeedbackDao;
	}
}
