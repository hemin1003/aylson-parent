package com.aylson.dc.qmtt.dao;

import java.util.Map;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.dc.qmtt.po.QmttSysReportInfo;
import com.aylson.dc.qmtt.search.QmttSysReportInfoSearch;
import com.aylson.dc.qmtt.vo.QmttSysReportInfoVo;

public interface QmttSysReportInfoDao extends BaseDao<QmttSysReportInfoVo, QmttSysReportInfoSearch> {

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
