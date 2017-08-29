package com.aylson.dc.qmtt.service;

import javax.servlet.http.HttpServletRequest;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.qmtt.po.QmttWithdrawHis;
import com.aylson.dc.qmtt.search.QmttWithdrawHisSearch;
import com.aylson.dc.qmtt.vo.QmttWithdrawHisVo;

public interface QmttWithdrawHisService  extends BaseService<QmttWithdrawHis, QmttWithdrawHisSearch> {

	/**
	 * 更新提现信息，如果成功或失败，则更新用户余额
	 * @param withdrawHisVo
	 * @return
	 */
	public Result updateWithdrawHisInfo(QmttWithdrawHisVo qmttWithdrawHisVo, HttpServletRequest request);
	
}
