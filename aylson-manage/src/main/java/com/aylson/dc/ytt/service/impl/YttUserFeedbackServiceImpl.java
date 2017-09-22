package com.aylson.dc.ytt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.ytt.dao.YttUserFeedbackDao;
import com.aylson.dc.ytt.po.YttUserFeedback;
import com.aylson.dc.ytt.search.YttUserFeedbackSearch;
import com.aylson.dc.ytt.service.YttUserFeedbackService;

@Service
public class YttUserFeedbackServiceImpl  extends BaseServiceImpl<YttUserFeedback, YttUserFeedbackSearch> implements YttUserFeedbackService {

	@Autowired
	private YttUserFeedbackDao yttUserFeedbackDao;

	@Override
	protected BaseDao<YttUserFeedback, YttUserFeedbackSearch> getBaseDao() {
		return yttUserFeedbackDao;
	}
}
