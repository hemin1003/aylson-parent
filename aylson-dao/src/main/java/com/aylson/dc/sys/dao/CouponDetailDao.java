package com.aylson.dc.sys.dao;

import java.util.List;
import java.util.Map;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.dc.sys.po.CouponDetail;
import com.aylson.dc.sys.search.CouponDetailSearch;

public interface CouponDetailDao extends BaseDao<CouponDetail, CouponDetailSearch> {
	/**
	 * 会员关系对应优惠券明细数据 
	 */
	public List selectCouponDetails(Map<String, Object> params);
}
