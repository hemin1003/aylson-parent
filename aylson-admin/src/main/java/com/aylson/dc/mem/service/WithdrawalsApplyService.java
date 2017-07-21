package com.aylson.dc.mem.service;

import java.io.OutputStream;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.mem.po.WithdrawalsApply;
import com.aylson.dc.mem.search.WithdrawalsApplySearch;
import com.aylson.dc.mem.vo.WithdrawalsApplyVo;

public interface WithdrawalsApplyService extends BaseService<WithdrawalsApply,WithdrawalsApplySearch> {
	
	/**
	 * 提现申请
	 * @param withdrawalsApplyVo
	 * @return
	 */
	public Result addWithdrawalsApply(WithdrawalsApplyVo withdrawalsApplyVo);
	
	/**
	 * 转账处理
	 * @param withdrawalsApplyVo
	 * @return
	 */
	public Result transfer(WithdrawalsApplyVo withdrawalsApplyVo);
	
	/**
	 * 根据申请人id获取已提现金额
	 * @param applierId
	 * @return
	 */
	public Float sumByApplierId(Integer applierId); 
	
	
}
