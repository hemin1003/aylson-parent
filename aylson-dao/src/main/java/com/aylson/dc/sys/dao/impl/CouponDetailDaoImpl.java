package com.aylson.dc.sys.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.sys.dao.CouponDetailDao;
import com.aylson.dc.sys.po.CouponDetail;
import com.aylson.dc.sys.search.CouponDetailSearch;

@Repository
public class CouponDetailDaoImpl extends BaseDaoImpl< CouponDetail, CouponDetailSearch> implements CouponDetailDao {

	@Override
	public List selectCouponDetails(Map<String, Object> params) {
		return this.sqlSessionTemplate.selectList(this.getSqlName("selectCouponDetails"), params);
	}
}
