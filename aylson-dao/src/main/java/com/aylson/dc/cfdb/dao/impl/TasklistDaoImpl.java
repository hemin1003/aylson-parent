package com.aylson.dc.cfdb.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.cfdb.dao.TasklistDao;
import com.aylson.dc.cfdb.po.Tasklist;
import com.aylson.dc.cfdb.search.TasklistSearch;

@Repository
public class TasklistDaoImpl extends BaseDaoImpl<Tasklist, TasklistSearch> implements TasklistDao {

}
