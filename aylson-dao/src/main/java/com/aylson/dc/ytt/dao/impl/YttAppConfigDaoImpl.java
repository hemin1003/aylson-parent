package com.aylson.dc.ytt.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.ytt.dao.YttAppConfigDao;
import com.aylson.dc.ytt.po.YttAppConfig;
import com.aylson.dc.ytt.search.YttAppConfigSearch;

@Repository
public class YttAppConfigDaoImpl extends BaseDaoImpl<YttAppConfig, YttAppConfigSearch> implements YttAppConfigDao {

}
