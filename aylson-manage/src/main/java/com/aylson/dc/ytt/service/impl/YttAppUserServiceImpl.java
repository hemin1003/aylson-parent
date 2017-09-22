package com.aylson.dc.ytt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.ytt.dao.YttAppUserDao;
import com.aylson.dc.ytt.po.YttAppUser;
import com.aylson.dc.ytt.search.YttAppUserSearch;
import com.aylson.dc.ytt.service.YttAppUserService;

@Service
public class YttAppUserServiceImpl  extends BaseServiceImpl<YttAppUser, YttAppUserSearch> implements YttAppUserService {

	@Autowired
	private YttAppUserDao appUserDao;

	@Override
	protected BaseDao<YttAppUser, YttAppUserSearch> getBaseDao() {
		return appUserDao;
	}
}
