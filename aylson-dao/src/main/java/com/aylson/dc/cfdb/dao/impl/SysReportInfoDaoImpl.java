package com.aylson.dc.cfdb.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.cfdb.dao.SysReportInfoDao;
import com.aylson.dc.cfdb.po.SysReportInfo;
import com.aylson.dc.cfdb.search.SysReportInfoSearch;
import com.aylson.dc.cfdb.vo.SysReportInfoVo;

@Repository
public class SysReportInfoDaoImpl extends BaseDaoImpl<SysReportInfo, SysReportInfoSearch> implements SysReportInfoDao {

	@Override
	public SysReportInfoVo selectNewUserOfDay(Map<String, Object> params) {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectNewUserOfDay"), params);
	}

	@Override
	public SysReportInfoVo selectUserCountOfTask(Map<String, Object> params) {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectUserCountOfTask"), params);
	}

	@Override
	public SysReportInfoVo selectTaskCountOfFinished(Map<String, Object> params) {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectTaskCountOfFinished"), params);
	}

	@Override
	public SysReportInfoVo selectUserIncomeOfDay(Map<String, Object> params) {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectUserIncomeOfDay"), params);
	}

	@Override
	public SysReportInfoVo selectSysIncomeOfDay(Map<String, Object> params) {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectSysIncomeOfDay"), params);
	}

}
