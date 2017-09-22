package com.aylson.dc.ytt.service;

import javax.servlet.http.HttpServletRequest;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.ytt.po.YttWithdrawHis;
import com.aylson.dc.ytt.search.YttWithdrawHisSearch;
import com.aylson.dc.ytt.vo.YttWithdrawHisVo;

public interface YttWithdrawHisService  extends BaseService<YttWithdrawHis, YttWithdrawHisSearch> {

	/**
	 * 更新提现信息，如果成功或失败，则更新用户余额
	 * @param withdrawHisVo
	 * @return
	 */
	public Result updateWithdrawHisInfo(YttWithdrawHisVo yttWithdrawHisVo, HttpServletRequest request);
	
}
