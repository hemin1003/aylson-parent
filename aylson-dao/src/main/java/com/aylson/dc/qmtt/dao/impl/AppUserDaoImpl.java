package com.aylson.dc.qmtt.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.qmtt.dao.AppUserDao;
import com.aylson.dc.qmtt.po.AppUser;
import com.aylson.dc.qmtt.search.AppUserSearch;

@Repository
public class AppUserDaoImpl extends BaseDaoImpl<AppUser, AppUserSearch> implements AppUserDao {

}
