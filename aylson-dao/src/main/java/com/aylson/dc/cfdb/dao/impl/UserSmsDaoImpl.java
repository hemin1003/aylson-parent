package com.aylson.dc.cfdb.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.cfdb.dao.UserSmsDao;
import com.aylson.dc.cfdb.po.UserSms;
import com.aylson.dc.cfdb.search.UserSmsSearch;

@Repository
public class UserSmsDaoImpl extends BaseDaoImpl<UserSms, UserSmsSearch> implements UserSmsDao {

}
