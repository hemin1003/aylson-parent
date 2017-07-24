package com.aylson.dc.cfdb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.cfdb.dao.UserTasklistDao;
import com.aylson.dc.cfdb.po.UserTasklist;
import com.aylson.dc.cfdb.search.UserTasklistSearch;
import com.aylson.dc.cfdb.service.UserTasklistService;

@Service
public class UserTasklistServiceImpl  extends BaseServiceImpl<UserTasklist, UserTasklistSearch> implements UserTasklistService {

	@Autowired
	private UserTasklistDao userTasklistDao;

	@Override
	protected BaseDao<UserTasklist, UserTasklistSearch> getBaseDao() {
		return userTasklistDao;
	}
}
