package com.aylson.dc.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aylson.core.exception.ServiceException;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.sys.dao.CouponDetailDao;
import com.aylson.dc.sys.po.CouponDetail;
import com.aylson.dc.sys.po.CouponRule;
import com.aylson.dc.sys.search.CouponDetailSearch;
import com.aylson.dc.sys.service.CouponDetailService;
import com.aylson.dc.sys.service.CouponRuleService;

@Service
public class CouponDetailServiceImpl extends BaseServiceImpl<CouponDetail, CouponDetailSearch>
		implements CouponDetailService {

	protected static final Logger logger = Logger.getLogger(CouponDetailServiceImpl.class);

	@Autowired
	private CouponDetailDao CouponDetailDao;

	@Autowired
	private CouponRuleService couponRuleService;

	@Override
	protected BaseDao<CouponDetail, CouponDetailSearch> getBaseDao() {
		return CouponDetailDao;
	}

	@Override
	@Transactional
	public Result addDetailAndCouponRule(CouponDetail couponDetail, String[] startPrice, String[] endPrice,
			String[] deratePrice) {
		Result result = new Result();
		boolean flag = this.CouponDetailDao.insert(couponDetail);
		if (flag) {
			List<CouponRule> list = new ArrayList<CouponRule>();
			for (int i = 0; i < startPrice.length; i++) {
				if (null != startPrice[i] || !startPrice[i].equals("")) {
					CouponRule rule = new CouponRule();
					rule.setCouponFkid(couponDetail.getId());
					rule.setStartPrice(Integer.valueOf(startPrice[i] == "" ? "0" : startPrice[i]));
					rule.setEndPrice(Integer.valueOf(endPrice[i] == "" ? "0" : endPrice[i]));
					rule.setDeratePrice(Integer.valueOf(deratePrice[i] == "" ? "0" : deratePrice[i]));
					list.add(rule);
				}
			}
			flag = this.couponRuleService.batchAdd(list);
			if (flag) {
				result.setOK(ResultCode.CODE_STATE_200, "保存成功");
			} else {
				result.setError(ResultCode.CODE_STATE_4006, "保存优惠券配置信息失败！");
				logger.error("保存优惠券配置信息失败", new ServiceException("保存优惠券配置信息失败"));
			}
		} else {
			result.setError(ResultCode.CODE_STATE_4006, "新增优惠券配置信息失败！");
		}
		return result;
	}

	@Override
	@Transactional
	public Result updateDetailAndCouponRule(CouponDetail couponDetail, String[] startPrice, String[] endPrice,
			String[] deratePrice, String[] ruleId) {
		Result result = new Result();
		boolean flag = this.CouponDetailDao.updateById(couponDetail);
		if (flag) {
			List<CouponRule> list = new ArrayList<CouponRule>();
			for (int i = 0; i < startPrice.length; i++) {
				if (null != startPrice[i] || !startPrice[i].equals("")) {
					CouponRule rule = new CouponRule();
					rule.setId(Integer.valueOf(ruleId[i]));
					rule.setStartPrice(Integer.valueOf(startPrice[i] == "" ? "0" : startPrice[i]));
					rule.setEndPrice(Integer.valueOf(endPrice[i] == "" ? "0" : endPrice[i]));
					rule.setDeratePrice(Integer.valueOf(deratePrice[i] == "" ? "0" : deratePrice[i]));
					list.add(rule);
				}
			}
			flag = this.couponRuleService.batchUpdate(list);
			if (flag) {
				result.setOK(ResultCode.CODE_STATE_200, "更新成功");
			} else {
				result.setError(ResultCode.CODE_STATE_4006, "更新优惠券配置信息失败！");
				logger.error("更新优惠券配置信息失败", new ServiceException("更新优惠券配置信息失败"));
			}
		} else {
			result.setError(ResultCode.CODE_STATE_4006, "更新优惠券配置信息失败！");
		}
		return result;
	}

	@Override
	public List selectCouponDetails(Map<String, Object> params) {
		return this.CouponDetailDao.selectCouponDetails(params);
	}
}
