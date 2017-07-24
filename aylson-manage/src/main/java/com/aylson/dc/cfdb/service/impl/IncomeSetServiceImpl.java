package com.aylson.dc.cfdb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.cfdb.dao.IncomeSetDao;
import com.aylson.dc.cfdb.po.IncomeSet;
import com.aylson.dc.cfdb.search.IncomeSetSearch;
import com.aylson.dc.cfdb.service.IncomeSetService;

@Service
public class IncomeSetServiceImpl  extends BaseServiceImpl<IncomeSet, IncomeSetSearch> implements IncomeSetService {

	@Autowired
	private IncomeSetDao incomeSetDao;

	@Override
	protected BaseDao<IncomeSet, IncomeSetSearch> getBaseDao() {
		return incomeSetDao;
	}
}
