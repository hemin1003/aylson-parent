package com.aylson.dc.cfdb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.cfdb.dao.TasklistDao;
import com.aylson.dc.cfdb.po.Tasklist;
import com.aylson.dc.cfdb.search.TasklistSearch;
import com.aylson.dc.cfdb.service.TasklistService;

@Service
public class TasklistServiceImpl  extends BaseServiceImpl<Tasklist, TasklistSearch> implements TasklistService {

	@Autowired
	private TasklistDao tasklistDao;

	@Override
	protected BaseDao<Tasklist, TasklistSearch> getBaseDao() {
		return tasklistDao;
	}
}
