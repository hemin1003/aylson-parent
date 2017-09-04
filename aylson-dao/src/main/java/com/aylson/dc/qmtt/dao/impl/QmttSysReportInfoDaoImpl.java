package com.aylson.dc.qmtt.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.qmtt.dao.QmttSysReportInfoDao;
import com.aylson.dc.qmtt.search.QmttSysReportInfoSearch;
import com.aylson.dc.qmtt.vo.QmttSysReportInfoVo;

@Repository
public class QmttSysReportInfoDaoImpl extends BaseDaoImpl<QmttSysReportInfoVo, QmttSysReportInfoSearch> implements QmttSysReportInfoDao {

	@Override
	public QmttSysReportInfoVo selectNewUserOfDay(Map<String, Object> params) {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectNewUserOfDay"), params);
	}

	@Override
	public QmttSysReportInfoVo selectUserGoldOfDay(Map<String, Object> params) {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectUserGoldOfDay"), params);
	}

	@Override
	public QmttSysReportInfoVo selectUserBalanceOfDay(Map<String, Object> params) {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectUserBalanceOfDay"), params);
	}

	@Override
	public QmttSysReportInfoVo selectUserWithdrawOfDay(Map<String, Object> params) {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectUserWithdrawOfDay"), params);
	}

	@Override
	public QmttSysReportInfoVo selectUserAllRead(Map<String, Object> params) {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectUserAllRead"), params);
	}
	
	@Override
	public QmttSysReportInfoVo selectAllGold() {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectAllGold"));
	}
	
	@Override
	public QmttSysReportInfoVo selectAllBalance() {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectAllBalance"));
	}

}
