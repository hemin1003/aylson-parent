package com.aylson.dc.qmtt.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.qmtt.dao.QmttAppConfigDao;
import com.aylson.dc.qmtt.po.QmttAppConfig;
import com.aylson.dc.qmtt.search.QmttAppConfigSearch;

@Repository
public class QmttAppConfigDaoImpl extends BaseDaoImpl<QmttAppConfig, QmttAppConfigSearch> implements QmttAppConfigDao {

}
