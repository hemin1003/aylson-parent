package com.aylson.dc.cfdb.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.cfdb.dao.AppConfigDao;
import com.aylson.dc.cfdb.po.AppConfig;
import com.aylson.dc.cfdb.search.AppConfigSearch;

@Repository
public class AppConfigDaoImpl extends BaseDaoImpl<AppConfig, AppConfigSearch> implements AppConfigDao {

}
