package com.aylson.dc.qmtt.service;

import java.util.Map;

import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.qmtt.search.QmttSysReportInfoSearch;
import com.aylson.dc.qmtt.vo.QmttSysReportInfoVo;

public interface QmttSysReportInfoService  extends BaseService<QmttSysReportInfoVo, QmttSysReportInfoSearch> {

	/**
	 * 当日新增用户数
	 */
	public QmttSysReportInfoVo selectNewUserOfDay(Map<String, Object> params);
	
	/**
	 * 用户金币余额
	 */
	public QmttSysReportInfoVo selectUserGoldOfDay(Map<String, Object> params);
	
	/**
	 * 用户零钱金额
	 */
	public QmttSysReportInfoVo selectUserBalanceOfDay(Map<String, Object> params);
	
	/**
	 * 用户提现金额
	 */
	public QmttSysReportInfoVo selectUserWithdrawOfDay(Map<String, Object> params);
	
	/**
	 * 用户浏览新闻数
	 */
	public QmttSysReportInfoVo selectUserAllRead(Map<String, Object> params);
	
	/**
	 * 用户总金币余额
	 */
	public QmttSysReportInfoVo selectAllGold();
	
	/**
	 * 用户总零钱余额
	 */
	public QmttSysReportInfoVo selectAllBalance();
}
