package com.aylson.dc.mem.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.mem.dao.WithdrawalsApplyDao;
import com.aylson.dc.mem.po.WithdrawalsApply;
import com.aylson.dc.mem.search.WithdrawalsApplySearch;

@Repository
public class WithdrawalsApplyDaoImpl extends BaseDaoImpl<WithdrawalsApply,WithdrawalsApplySearch> implements WithdrawalsApplyDao {

	@Override
	public Float sumByApplierId(Integer applierId) {
		return this.sqlSessionTemplate.selectOne(getSqlName("sumByApplierId"), applierId);
	}
}
