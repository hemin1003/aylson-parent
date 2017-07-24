package com.aylson.dc.cfdb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.cfdb.dao.ImUsersDao;
import com.aylson.dc.cfdb.po.ImUsers;
import com.aylson.dc.cfdb.search.ImUsersSearch;
import com.aylson.dc.cfdb.service.ImUsersService;

@Service
public class ImUsersServiceImpl  extends BaseServiceImpl<ImUsers, ImUsersSearch> implements ImUsersService {

	@Autowired
	private ImUsersDao imUsersDao;

	@Override
	protected BaseDao<ImUsers, ImUsersSearch> getBaseDao() {
		return imUsersDao;
	}
}
