package com.aylson.dc.cfdb.service;

import java.util.Map;

import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.cfdb.po.SysReportInfo;
import com.aylson.dc.cfdb.search.SysReportInfoSearch;
import com.aylson.dc.cfdb.vo.SysReportInfoVo;

public interface SysReportInfoService  extends BaseService<SysReportInfo, SysReportInfoSearch> {

	/**
	 * 当日新增用户数
	 */
	public SysReportInfoVo selectNewUserOfDay(Map<String, Object> params);
	
	/**
	 * 当日做任务总人数
	 */
	public SysReportInfoVo selectUserCountOfTask(Map<String, Object> params);
	
	/**
	 * 当日完成任务总数
	 */
	public SysReportInfoVo selectTaskCountOfFinished(Map<String, Object> params);
	
	/**
	 * 当日用户总收入
	 */
	public SysReportInfoVo selectUserIncomeOfDay(Map<String, Object> params);
	
	/**
	 * 当日公司总收入
	 */
	public SysReportInfoVo selectSysIncomeOfDay(Map<String, Object> params);
}
