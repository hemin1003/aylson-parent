package com.aylson.dc.partner.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.partner.dao.CouponDao;
import com.aylson.dc.partner.po.Coupon;
import com.aylson.dc.partner.search.CouponSearch;

@Repository
public class CouponDaoImpl extends BaseDaoImpl<Coupon,CouponSearch> implements CouponDao {

	@Override
	public void updateCouponInvalid() {
		this.sqlSessionTemplate.update(getSqlName("updateCouponInvalid"));
	}

	@Override
	public void unbundCouponApply() {
		this.sqlSessionTemplate.update(getSqlName("unbundCouponApply"));
	}

	@Override
	public String selectLastCouponCode() {
		return this.sqlSessionTemplate.selectOne(getSqlName("selectLastCouponCode"));
	}
	
	
}
