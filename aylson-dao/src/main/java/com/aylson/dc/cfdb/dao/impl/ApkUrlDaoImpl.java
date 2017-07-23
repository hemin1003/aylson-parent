package com.aylson.dc.cfdb.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.cfdb.dao.ApkUrlDao;
import com.aylson.dc.cfdb.po.ApkUrl;
import com.aylson.dc.cfdb.search.ApkUrlSearch;

@Repository
public class ApkUrlDaoImpl extends BaseDaoImpl<ApkUrl, ApkUrlSearch> implements ApkUrlDao {

}
