package com.aylson.dc.mem.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.mem.dao.FeedBackDao;
import com.aylson.dc.mem.po.FeedBack;
import com.aylson.dc.mem.search.FeedBackSearch;

@Repository
public class FeedBackDaoImpl extends BaseDaoImpl<FeedBack,FeedBackSearch> implements FeedBackDao {


}
