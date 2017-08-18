package com.aylson.dc.qmtt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.qmtt.dao.AppUserDao;
import com.aylson.dc.qmtt.po.AppUser;
import com.aylson.dc.qmtt.search.AppUserSearch;
import com.aylson.dc.qmtt.service.AppUserService;

@Service
public class AppUserServiceImpl  extends BaseServiceImpl<AppUser, AppUserSearch> implements AppUserService {

	@Autowired
	private AppUserDao appUserDao;

	@Override
	protected BaseDao<AppUser, AppUserSearch> getBaseDao() {
		return appUserDao;
	}
}
