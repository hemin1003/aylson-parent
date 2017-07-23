package com.aylson.dc.cfdb.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.cfdb.dao.ImUsersDao;
import com.aylson.dc.cfdb.po.ImUsers;
import com.aylson.dc.cfdb.search.ImUsersSearch;

@Repository
public class ImUsersDaoImpl extends BaseDaoImpl<ImUsers, ImUsersSearch> implements ImUsersDao {

}
