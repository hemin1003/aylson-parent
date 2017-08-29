package com.aylson.dc.qmtt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.qmtt.dao.BalanceHisDao;
import com.aylson.dc.qmtt.po.BalanceHis;
import com.aylson.dc.qmtt.search.BalanceHisSearch;
import com.aylson.dc.qmtt.service.BalanceHisService;

@Service
public class BalanceHisServiceImpl  extends BaseServiceImpl<BalanceHis, BalanceHisSearch> implements BalanceHisService {

	@Autowired
	private BalanceHisDao balanceHisDao;

	@Override
	protected BaseDao<BalanceHis, BalanceHisSearch> getBaseDao() {
		return balanceHisDao;
	}
}
