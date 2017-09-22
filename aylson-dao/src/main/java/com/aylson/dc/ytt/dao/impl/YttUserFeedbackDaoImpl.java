package com.aylson.dc.ytt.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.ytt.dao.YttUserFeedbackDao;
import com.aylson.dc.ytt.po.YttUserFeedback;
import com.aylson.dc.ytt.search.YttUserFeedbackSearch;

@Repository
public class YttUserFeedbackDaoImpl extends BaseDaoImpl<YttUserFeedback, YttUserFeedbackSearch> implements YttUserFeedbackDao {

}
