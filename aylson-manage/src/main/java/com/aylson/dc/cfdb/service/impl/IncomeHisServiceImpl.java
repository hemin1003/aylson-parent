package com.aylson.dc.cfdb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.cfdb.dao.IncomeHisDao;
import com.aylson.dc.cfdb.po.IncomeHis;
import com.aylson.dc.cfdb.search.IncomeHisSearch;
import com.aylson.dc.cfdb.service.IncomeHisService;

@Service
public class IncomeHisServiceImpl  extends BaseServiceImpl<IncomeHis, IncomeHisSearch> implements IncomeHisService {

	@Autowired
	private IncomeHisDao incomeHisDao;

	@Override
	protected BaseDao<IncomeHis, IncomeHisSearch> getBaseDao() {
		return incomeHisDao;
	}
}
