package com.aylson.dc.qmtt.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.qmtt.dao.QmttAppVersionDao;
import com.aylson.dc.qmtt.po.QmttAppVersion;
import com.aylson.dc.qmtt.search.QmttAppVersionSearch;

@Repository
public class QmttAppVersionDaoImpl extends BaseDaoImpl<QmttAppVersion, QmttAppVersionSearch> implements QmttAppVersionDao {

}
