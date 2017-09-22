package com.aylson.dc.ytt.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.ytt.dao.YttSysReportInfoDao;
import com.aylson.dc.ytt.search.YttSysReportInfoSearch;
import com.aylson.dc.ytt.service.YttSysReportInfoService;
import com.aylson.dc.ytt.vo.YttSysReportInfoVo;

@Service
public class YttSysReportInfoServiceImpl  extends BaseServiceImpl<YttSysReportInfoVo, YttSysReportInfoSearch> implements YttSysReportInfoService {
	
	@Autowired
	private YttSysReportInfoDao yttSysReportInfoDao;
	
	@Override
	protected BaseDao<YttSysReportInfoVo, YttSysReportInfoSearch> getBaseDao() {
		return yttSysReportInfoDao;
	}

	/**
	 * 当日新增用户数
	 */
	public YttSysReportInfoVo selectNewUserOfDay(Map<String, Object> params) {
		return this.yttSysReportInfoDao.selectNewUserOfDay(params);
	}
	
	/**
	 * 用户金币余额
	 */
	public YttSysReportInfoVo selectUserGoldOfDay(Map<String, Object> params) {
		return this.yttSysReportInfoDao.selectUserGoldOfDay(params);
	}
	
	/**
	 * 用户零钱金额
	 */
	public YttSysReportInfoVo selectUserBalanceOfDay(Map<String, Object> params) {
		return this.yttSysReportInfoDao.selectUserBalanceOfDay(params);
	}
	
	/**
	 * 用户提现金额
	 */
	public YttSysReportInfoVo selectUserWithdrawOfDay(Map<String, Object> params) {
		return this.yttSysReportInfoDao.selectUserWithdrawOfDay(params);
	}
	
	/**
	 * 用户浏览新闻数
	 */
	public YttSysReportInfoVo selectUserAllRead(Map<String, Object> params) {
		return this.yttSysReportInfoDao.selectUserAllRead(params);
	}
	
	/**
	 * 用户总金币余额
	 */
	public YttSysReportInfoVo selectAllGold() {
		return this.yttSysReportInfoDao.selectAllGold();
	}
	
	/**
	 * 用户总零钱余额
	 */
	public YttSysReportInfoVo selectAllBalance() {
		return this.yttSysReportInfoDao.selectAllBalance();
	}
}
