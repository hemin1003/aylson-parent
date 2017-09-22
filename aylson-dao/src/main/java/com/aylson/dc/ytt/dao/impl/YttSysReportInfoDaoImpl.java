package com.aylson.dc.ytt.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.ytt.dao.YttSysReportInfoDao;
import com.aylson.dc.ytt.search.YttSysReportInfoSearch;
import com.aylson.dc.ytt.vo.YttSysReportInfoVo;

@Repository
public class YttSysReportInfoDaoImpl extends BaseDaoImpl<YttSysReportInfoVo, YttSysReportInfoSearch> implements YttSysReportInfoDao {

	@Override
	public YttSysReportInfoVo selectNewUserOfDay(Map<String, Object> params) {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectNewUserOfDay"), params);
	}

	@Override
	public YttSysReportInfoVo selectUserGoldOfDay(Map<String, Object> params) {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectUserGoldOfDay"), params);
	}

	@Override
	public YttSysReportInfoVo selectUserBalanceOfDay(Map<String, Object> params) {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectUserBalanceOfDay"), params);
	}

	@Override
	public YttSysReportInfoVo selectUserWithdrawOfDay(Map<String, Object> params) {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectUserWithdrawOfDay"), params);
	}

	@Override
	public YttSysReportInfoVo selectUserAllRead(Map<String, Object> params) {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectUserAllRead"), params);
	}
	
	@Override
	public YttSysReportInfoVo selectAllGold() {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectAllGold"));
	}
	
	@Override
	public YttSysReportInfoVo selectAllBalance() {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectAllBalance"));
	}

}
