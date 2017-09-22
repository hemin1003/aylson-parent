package com.aylson.dc.ytt.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.ytt.dao.YttAppUserDao;
import com.aylson.dc.ytt.po.YttAppUser;
import com.aylson.dc.ytt.search.YttAppUserSearch;

@Repository
public class YttAppUserDaoImpl extends BaseDaoImpl<YttAppUser, YttAppUserSearch> implements YttAppUserDao {

}
