package com.aylson.dc.cfdb.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.cfdb.dao.IncomeSetDao;
import com.aylson.dc.cfdb.po.IncomeSet;
import com.aylson.dc.cfdb.search.IncomeSetSearch;

@Repository
public class IncomeSetDaoImpl extends BaseDaoImpl<IncomeSet, IncomeSetSearch> implements IncomeSetDao {

}
