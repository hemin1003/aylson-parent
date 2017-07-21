package com.aylson.dc.sys.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.sys.dao.ActivityDao;
import com.aylson.dc.sys.po.Activity;
import com.aylson.dc.sys.search.ActivitySearch;

@Repository
public class ActivityDaoImpl extends BaseDaoImpl<Activity,ActivitySearch> implements ActivityDao {

	
}
