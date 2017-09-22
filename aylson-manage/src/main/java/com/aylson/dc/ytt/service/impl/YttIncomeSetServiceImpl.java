package com.aylson.dc.ytt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.ytt.dao.YttIncomeSetDao;
import com.aylson.dc.ytt.po.YttIncomeSet;
import com.aylson.dc.ytt.search.YttIncomeSetSearch;
import com.aylson.dc.ytt.service.YttIncomeSetService;

@Service
public class YttIncomeSetServiceImpl  extends BaseServiceImpl<YttIncomeSet, YttIncomeSetSearch> implements YttIncomeSetService {

	@Autowired
	private YttIncomeSetDao yttIncomeSetDao;

	@Override
	protected BaseDao<YttIncomeSet, YttIncomeSetSearch> getBaseDao() {
		return yttIncomeSetDao;
	}
}
