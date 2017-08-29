package com.aylson.dc.qmtt.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.qmtt.dao.QmttWithdrawHisDao;
import com.aylson.dc.qmtt.po.QmttWithdrawHis;
import com.aylson.dc.qmtt.search.QmttWithdrawHisSearch;
import com.aylson.dc.qmtt.service.QmttWithdrawHisService;
import com.aylson.dc.qmtt.vo.QmttWithdrawHisVo;

@Service
public class QmttWithdrawHisServiceImpl  extends BaseServiceImpl<QmttWithdrawHis, QmttWithdrawHisSearch> implements QmttWithdrawHisService {

	@Autowired
	private QmttWithdrawHisDao qmttWithdrawHisDao;

	@Override
	protected BaseDao<QmttWithdrawHis, QmttWithdrawHisSearch> getBaseDao() {
		return qmttWithdrawHisDao;
	}

	@Override
	public Result updateWithdrawHisInfo(QmttWithdrawHisVo qmttWithdrawHisVo, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
}
