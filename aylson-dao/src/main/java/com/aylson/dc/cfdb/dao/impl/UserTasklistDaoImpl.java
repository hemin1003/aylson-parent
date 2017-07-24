package com.aylson.dc.cfdb.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.cfdb.dao.UserTasklistDao;
import com.aylson.dc.cfdb.po.UserTasklist;
import com.aylson.dc.cfdb.search.UserTasklistSearch;

@Repository
public class UserTasklistDaoImpl extends BaseDaoImpl<UserTasklist, UserTasklistSearch> implements UserTasklistDao {

}
