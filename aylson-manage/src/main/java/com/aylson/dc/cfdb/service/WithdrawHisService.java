package com.aylson.dc.cfdb.service;

import javax.servlet.http.HttpServletRequest;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.cfdb.po.WithdrawHis;
import com.aylson.dc.cfdb.search.WithdrawHisSearch;
import com.aylson.dc.cfdb.vo.WithdrawHisVo;

public interface WithdrawHisService extends BaseService<WithdrawHis, WithdrawHisSearch> {
	
	/**
	 * 更新提现信息，如果成功或失败，则更新用户余额
	 * @param withdrawHisVo
	 * @return
	 */
	public Result updateWithdrawHisInfo(WithdrawHisVo withdrawHisVo, HttpServletRequest request);
	
}
