package com.aylson.dc.qmtt.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.qmtt.dao.InitConfigDao;
import com.aylson.dc.qmtt.po.InitConfig;
import com.aylson.dc.qmtt.search.InitConfigSearch;

@Repository
public class InitConfigDaoImpl extends BaseDaoImpl<InitConfig, InitConfigSearch> implements InitConfigDao {

}
