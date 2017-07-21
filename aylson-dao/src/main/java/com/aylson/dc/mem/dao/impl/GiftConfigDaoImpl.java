package com.aylson.dc.mem.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.mem.dao.GiftConfigDao;
import com.aylson.dc.mem.po.GiftConfig;
import com.aylson.dc.mem.search.GiftConfigSearch;

@Repository
public class GiftConfigDaoImpl extends BaseDaoImpl<GiftConfig,GiftConfigSearch> implements GiftConfigDao {


}
