package com.aylson.dc.ytt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.ytt.dao.YttBalanceHisDao;
import com.aylson.dc.ytt.po.YttBalanceHis;
import com.aylson.dc.ytt.search.YttBalanceHisSearch;
import com.aylson.dc.ytt.service.YttBalanceHisService;

@Service
public class YttBalanceHisServiceImpl  extends BaseServiceImpl<YttBalanceHis, YttBalanceHisSearch> implements YttBalanceHisService {

	@Autowired
	private YttBalanceHisDao balanceHisDao;

	@Override
	protected BaseDao<YttBalanceHis, YttBalanceHisSearch> getBaseDao() {
		return balanceHisDao;
	}
}
