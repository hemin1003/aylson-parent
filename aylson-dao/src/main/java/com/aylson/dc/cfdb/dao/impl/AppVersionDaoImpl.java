package com.aylson.dc.cfdb.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.cfdb.dao.AppVersionDao;
import com.aylson.dc.cfdb.po.AppVersion;
import com.aylson.dc.cfdb.search.AppVersionSearch;

@Repository
public class AppVersionDaoImpl extends BaseDaoImpl<AppVersion, AppVersionSearch> implements AppVersionDao {

}
