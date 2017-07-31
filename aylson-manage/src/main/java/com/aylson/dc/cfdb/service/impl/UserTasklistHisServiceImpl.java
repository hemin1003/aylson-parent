package com.aylson.dc.cfdb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.cfdb.dao.UserTasklistHisDao;
import com.aylson.dc.cfdb.po.UserTasklistHis;
import com.aylson.dc.cfdb.search.UserTasklistHisSearch;
import com.aylson.dc.cfdb.service.UserTasklistHisService;

@Service
public class UserTasklistHisServiceImpl  extends BaseServiceImpl<UserTasklistHis, UserTasklistHisSearch> implements UserTasklistHisService {

	@Autowired
	private UserTasklistHisDao userTasklistHis;

	@Override
	protected BaseDao<UserTasklistHis, UserTasklistHisSearch> getBaseDao() {
		return userTasklistHis;
	}
}
