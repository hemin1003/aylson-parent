package com.aylson.dc.cfdb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.cfdb.dao.TaskDetailDao;
import com.aylson.dc.cfdb.po.TaskDetail;
import com.aylson.dc.cfdb.search.TaskDetailSearch;
import com.aylson.dc.cfdb.service.TaskDetailService;

@Service
public class TaskDetailServiceImpl  extends BaseServiceImpl<TaskDetail, TaskDetailSearch> implements TaskDetailService {

	@Autowired
	private TaskDetailDao taskDetailDao;

	@Override
	protected BaseDao<TaskDetail, TaskDetailSearch> getBaseDao() {
		return taskDetailDao;
	}
}
