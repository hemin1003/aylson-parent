package com.aylson.dc.ytt.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.ytt.dao.YttInitConfigDao;
import com.aylson.dc.ytt.po.YttInitConfig;
import com.aylson.dc.ytt.search.YttInitConfigSearch;

@Repository
public class YttInitConfigDaoImpl extends BaseDaoImpl<YttInitConfig, YttInitConfigSearch> implements YttInitConfigDao {

}
