package com.aylson.dc.cfdb.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.cfdb.dao.SysReportInfoDao;
import com.aylson.dc.cfdb.po.SysReportInfo;
import com.aylson.dc.cfdb.search.SysReportInfoSearch;
import com.aylson.dc.cfdb.service.SysReportInfoService;
import com.aylson.dc.cfdb.vo.SysReportInfoVo;

@Service
public class SysReportInfoServiceImpl  extends BaseServiceImpl<SysReportInfo, SysReportInfoSearch> implements SysReportInfoService {

	@Autowired
	private SysReportInfoDao sysReportInfoDao;

	@Override
	protected BaseDao<SysReportInfo, SysReportInfoSearch> getBaseDao() {
		return sysReportInfoDao;
	}
	
	/**
	 * 今日新增用户数
	 */
	public SysReportInfoVo selectNewUserOfDay(Map<String, Object> params) {
		return this.sysReportInfoDao.selectNewUserOfDay(params);
	}
	
	/**
	 * 今日做任务总人数
	 */
	public SysReportInfoVo selectUserCountOfTask(Map<String, Object> params) {
		return this.sysReportInfoDao.selectUserCountOfTask(params);
	}
	
	/**
	 * 今日完成任务总数
	 */
	public SysReportInfoVo selectTaskCountOfFinished(Map<String, Object> params) {
		return this.sysReportInfoDao.selectTaskCountOfFinished(params);
	}
	
	/**
	 * 今日用户总收入
	 */
	public SysReportInfoVo selectUserIncomeOfDay(Map<String, Object> params) {
		return this.sysReportInfoDao.selectUserIncomeOfDay(params);
	}
	
	/**
	 * 今日公司总收入
	 */
	public SysReportInfoVo selectSysIncomeOfDay(Map<String, Object> params) {
		return this.sysReportInfoDao.selectSysIncomeOfDay(params);
	}
}
