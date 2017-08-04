package com.aylson.dc.cfdb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.cfdb.dao.UserSmsDao;
import com.aylson.dc.cfdb.po.UserSms;
import com.aylson.dc.cfdb.search.UserSmsSearch;
import com.aylson.dc.cfdb.service.UserSmsService;

@Service
public class UserSmsServiceImpl  extends BaseServiceImpl<UserSms, UserSmsSearch> implements UserSmsService {

	@Autowired
	private UserSmsDao userSmsDao;

	@Override
	protected BaseDao<UserSms, UserSmsSearch> getBaseDao() {
		return userSmsDao;
	}
}
