package com.aylson.dc.cfdb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.cfdb.dao.WithdrawHisDao;
import com.aylson.dc.cfdb.po.WithdrawHis;
import com.aylson.dc.cfdb.search.WithdrawHisSearch;
import com.aylson.dc.cfdb.service.WithdrawHisService;


@Service
public class WithdrawHisServiceImpl extends BaseServiceImpl<WithdrawHis, WithdrawHisSearch> implements WithdrawHisService {

	@Autowired
	private WithdrawHisDao withdrawHisDao;

	@Override
	protected BaseDao<WithdrawHis, WithdrawHisSearch> getBaseDao() {
		return withdrawHisDao;
	}
}
