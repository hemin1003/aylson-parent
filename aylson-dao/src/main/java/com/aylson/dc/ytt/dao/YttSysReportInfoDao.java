package com.aylson.dc.ytt.dao;

import java.util.Map;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.dc.ytt.search.YttSysReportInfoSearch;
import com.aylson.dc.ytt.vo.YttSysReportInfoVo;

public interface YttSysReportInfoDao extends BaseDao<YttSysReportInfoVo, YttSysReportInfoSearch> {

	/**
	 * 当日新增用户数
	 */
	public YttSysReportInfoVo selectNewUserOfDay(Map<String, Object> params);
	
	/**
	 * 用户金币余额
	 */
	public YttSysReportInfoVo selectUserGoldOfDay(Map<String, Object> params);
	
	/**
	 * 用户零钱金额
	 */
	public YttSysReportInfoVo selectUserBalanceOfDay(Map<String, Object> params);
	
	/**
	 * 用户提现金额
	 */
	public YttSysReportInfoVo selectUserWithdrawOfDay(Map<String, Object> params);
	
	/**
	 * 用户浏览新闻数
	 */
	public YttSysReportInfoVo selectUserAllRead(Map<String, Object> params);
	
	/**
	 * 用户总金币余额
	 */
	public YttSysReportInfoVo selectAllGold();
	
	/**
	 * 用户总零钱余额
	 */
	public YttSysReportInfoVo selectAllBalance();
}
