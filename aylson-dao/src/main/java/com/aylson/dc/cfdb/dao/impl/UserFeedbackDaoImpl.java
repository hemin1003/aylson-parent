package com.aylson.dc.cfdb.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.cfdb.dao.UserFeedbackDao;
import com.aylson.dc.cfdb.po.UserFeedback;
import com.aylson.dc.cfdb.search.UserFeedbackSearch;

@Repository
public class UserFeedbackDaoImpl extends BaseDaoImpl<UserFeedback, UserFeedbackSearch> implements UserFeedbackDao {

}
