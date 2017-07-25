package com.aylson.dc.cfdb.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.cfdb.dao.TaskDetailDao;
import com.aylson.dc.cfdb.po.TaskDetail;
import com.aylson.dc.cfdb.search.TaskDetailSearch;

@Repository
public class TaskDetailDaoImpl extends BaseDaoImpl<TaskDetail, TaskDetailSearch> implements TaskDetailDao {

}
