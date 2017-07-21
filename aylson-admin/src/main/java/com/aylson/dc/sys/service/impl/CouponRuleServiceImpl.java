package com.aylson.dc.sys.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.sys.dao.CouponRuleDao;
import com.aylson.dc.sys.po.CouponRule;
import com.aylson.dc.sys.search.CouponRuleSearch;
import com.aylson.dc.sys.service.CouponRuleService;


@Service
public class CouponRuleServiceImpl extends BaseServiceImpl<CouponRule, CouponRuleSearch> implements CouponRuleService {

	@Autowired
	private CouponRuleDao CouponRuleDao;

	@Override
	protected BaseDao<CouponRule, CouponRuleSearch> getBaseDao() {
		return CouponRuleDao;
	}

	@Override
	public <T extends CouponRule> List<T> getList(CouponRuleSearch search) {
		List<T> list = super.getList(search);
		for (CouponRule couponRule : list) {
			couponRule.setStartPrice(couponRule.getStartPrice()==0?null:couponRule.getStartPrice());
			couponRule.setEndPrice(couponRule.getEndPrice()==0?null:couponRule.getEndPrice());
			couponRule.setDeratePrice(couponRule.getDeratePrice()==0?null:couponRule.getDeratePrice());
		}
		return list;
	}
}
