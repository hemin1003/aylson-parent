package com.aylson.dc.ytt.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.ytt.dao.YttAppVersionDao;
import com.aylson.dc.ytt.po.YttAppVersion;
import com.aylson.dc.ytt.search.YttAppVersionSearch;

@Repository
public class YttAppVersionDaoImpl extends BaseDaoImpl<YttAppVersion, YttAppVersionSearch> implements YttAppVersionDao {

}
