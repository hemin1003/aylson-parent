package com.aylson.dc.ytt.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.ytt.dao.YttWithdrawHisDao;
import com.aylson.dc.ytt.po.YttWithdrawHis;
import com.aylson.dc.ytt.search.YttWithdrawHisSearch;
import com.aylson.dc.ytt.service.YttWithdrawHisService;
import com.aylson.dc.ytt.vo.YttWithdrawHisVo;

@Service
public class YttWithdrawHisServiceImpl  extends BaseServiceImpl<YttWithdrawHis, YttWithdrawHisSearch> implements YttWithdrawHisService {

	@Autowired
	private YttWithdrawHisDao yttWithdrawHisDao;

	@Override
	protected BaseDao<YttWithdrawHis, YttWithdrawHisSearch> getBaseDao() {
		return yttWithdrawHisDao;
	}

	@Override
	public Result updateWithdrawHisInfo(YttWithdrawHisVo yttWithdrawHisVo, HttpServletRequest request) {
		return null;
	}
}
