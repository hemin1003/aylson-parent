package com.aylson.dc.sys.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.sys.dao.ActivityRegisterDao;
import com.aylson.dc.sys.po.ActivityRegister;
import com.aylson.dc.sys.search.ActivityRegisterSearch;
import com.aylson.dc.sys.service.ActivityRegisterService;


@Service
public class ActivityRegisterServiceImpl extends BaseServiceImpl<ActivityRegister,ActivityRegisterSearch> implements ActivityRegisterService {

	@Autowired
	private ActivityRegisterDao activityRegisterDao;

	@Override
	protected BaseDao<ActivityRegister,ActivityRegisterSearch> getBaseDao() {
		return activityRegisterDao;
	}


}
