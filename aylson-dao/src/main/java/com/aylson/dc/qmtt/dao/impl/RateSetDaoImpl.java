package com.aylson.dc.qmtt.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.qmtt.dao.RateSetDao;
import com.aylson.dc.qmtt.po.RateSet;
import com.aylson.dc.qmtt.search.RateSetSearch;

@Repository
public class RateSetDaoImpl extends BaseDaoImpl<RateSet, RateSetSearch> implements RateSetDao {

}
