package com.aylson.dc.cfdb.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.cfdb.dao.SdkTasklistDao;
import com.aylson.dc.cfdb.po.SdkTasklist;
import com.aylson.dc.cfdb.search.SdkTasklistSearch;

@Repository
public class SdkTasklistDaoImpl extends BaseDaoImpl<SdkTasklist, SdkTasklistSearch> implements SdkTasklistDao {

}
