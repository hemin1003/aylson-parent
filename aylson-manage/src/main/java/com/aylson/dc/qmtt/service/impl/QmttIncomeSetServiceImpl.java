package com.aylson.dc.qmtt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.qmtt.dao.QmttIncomeSetDao;
import com.aylson.dc.qmtt.po.QmttIncomeSet;
import com.aylson.dc.qmtt.search.QmttIncomeSetSearch;
import com.aylson.dc.qmtt.service.QmttIncomeSetService;

@Service
public class QmttIncomeSetServiceImpl  extends BaseServiceImpl<QmttIncomeSet, QmttIncomeSetSearch> implements QmttIncomeSetService {

	@Autowired
	private QmttIncomeSetDao qmttIncomeSetDao;

	@Override
	protected BaseDao<QmttIncomeSet, QmttIncomeSetSearch> getBaseDao() {
		return qmttIncomeSetDao;
	}
}
