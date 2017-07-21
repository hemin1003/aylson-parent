package com.aylson.dc.sys.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.sys.dao.ActivityDao;
import com.aylson.dc.sys.po.Activity;
import com.aylson.dc.sys.search.ActivitySearch;
import com.aylson.dc.sys.service.ActivityService;


@Service
public class ActivityServiceImpl extends BaseServiceImpl<Activity,ActivitySearch> implements ActivityService {

	@Autowired
	private ActivityDao activityDao;

	@Override
	protected BaseDao<Activity,ActivitySearch> getBaseDao() {
		return activityDao;
	}


}
