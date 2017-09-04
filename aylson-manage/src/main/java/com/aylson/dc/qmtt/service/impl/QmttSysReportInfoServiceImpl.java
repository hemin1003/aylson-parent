package com.aylson.dc.qmtt.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.cfdb.dao.SysReportInfoDao;
import com.aylson.dc.cfdb.po.SysReportInfo;
import com.aylson.dc.cfdb.search.SysReportInfoSearch;
import com.aylson.dc.qmtt.dao.QmttSysReportInfoDao;
import com.aylson.dc.qmtt.po.QmttSysReportInfo;
import com.aylson.dc.qmtt.search.QmttSysReportInfoSearch;
import com.aylson.dc.qmtt.service.QmttSysReportInfoService;
import com.aylson.dc.qmtt.vo.QmttSysReportInfoVo;

@Service
public class QmttSysReportInfoServiceImpl  extends BaseServiceImpl<QmttSysReportInfoVo, QmttSysReportInfoSearch> implements QmttSysReportInfoService {
	
	@Autowired
	private QmttSysReportInfoDao qmttSysReportInfoDao;
	
	@Override
	protected BaseDao<QmttSysReportInfoVo, QmttSysReportInfoSearch> getBaseDao() {
		return qmttSysReportInfoDao;
	}

	/**
	 * 当日新增用户数
	 */
	public QmttSysReportInfoVo selectNewUserOfDay(Map<String, Object> params) {
		return this.qmttSysReportInfoDao.selectNewUserOfDay(params);
	}
	
	/**
	 * 用户金币余额
	 */
	public QmttSysReportInfoVo selectUserGoldOfDay(Map<String, Object> params) {
		return this.qmttSysReportInfoDao.selectUserGoldOfDay(params);
	}
	
	/**
	 * 用户零钱金额
	 */
	public QmttSysReportInfoVo selectUserBalanceOfDay(Map<String, Object> params) {
		return this.qmttSysReportInfoDao.selectUserBalanceOfDay(params);
	}
	
	/**
	 * 用户提现金额
	 */
	public QmttSysReportInfoVo selectUserWithdrawOfDay(Map<String, Object> params) {
		return this.qmttSysReportInfoDao.selectUserWithdrawOfDay(params);
	}
	
	/**
	 * 用户浏览新闻数
	 */
	public QmttSysReportInfoVo selectUserAllRead(Map<String, Object> params) {
		return this.qmttSysReportInfoDao.selectUserAllRead(params);
	}
	
	/**
	 * 用户总金币余额
	 */
	public QmttSysReportInfoVo selectAllGold() {
		return this.qmttSysReportInfoDao.selectAllGold();
	}
	
	/**
	 * 用户总零钱余额
	 */
	public QmttSysReportInfoVo selectAllBalance() {
		return this.qmttSysReportInfoDao.selectAllBalance();
	}
}
