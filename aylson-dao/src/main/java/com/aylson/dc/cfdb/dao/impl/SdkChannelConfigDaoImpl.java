package com.aylson.dc.cfdb.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.cfdb.dao.SdkChannelConfigDao;
import com.aylson.dc.cfdb.po.SdkChannelConfig;
import com.aylson.dc.cfdb.search.SdkChannelConfigSearch;

@Repository
public class SdkChannelConfigDaoImpl extends BaseDaoImpl<SdkChannelConfig, SdkChannelConfigSearch> implements SdkChannelConfigDao {

}
