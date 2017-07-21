package com.aylson.dc.partner.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.base.common.BillSNCreateFactory;
import com.aylson.dc.partner.dao.CouponDao;
import com.aylson.dc.partner.po.Coupon;
import com.aylson.dc.partner.search.CouponSearch;
import com.aylson.dc.partner.service.CouponConfigService;
import com.aylson.dc.partner.service.CouponService;
import com.aylson.dc.partner.vo.CouponConfigVo;
import com.aylson.dc.partner.vo.CouponVo;
import com.aylson.utils.DateUtil;


@Service
public class CouponServiceImpl extends BaseServiceImpl<Coupon,CouponSearch> implements CouponService {

	@Autowired
	private CouponDao couponDao;
	@Autowired
	private CouponConfigService couponConfigService;

	@Override
	protected BaseDao<Coupon,CouponSearch> getBaseDao() {
		return couponDao;
	}

	@Override
	public String getCouponCode(String prefix,Date date,Integer applyId) {
		//日期+申请id+序号：XJQ 20170117 1 1
		if(date == null){
			date = new Date();
		}
		String couponCode = prefix + DateUtil.format(date, "yyyyMMdd") + applyId;
		return couponCode;
	}

	@Override
	public List<CouponVo> getSendList(Integer applyId, Integer configId, Integer applierId, Integer applyNum) {
		List<CouponVo> list = new ArrayList<CouponVo>();
		Date curDate = new Date();
		Date effectTime = null;
		CouponConfigVo couponConfigVo = this.couponConfigService.getById(configId);//计算有效时间
		if(couponConfigVo != null && couponConfigVo.getEffectDays() != null){
			effectTime = DateUtil.format(DateUtil.format(DateUtil.operDay(curDate, couponConfigVo.getEffectDays()),true)+" 23:59:59");
		}
//		String couponCode = this.getCouponCode("XJQ", curDate, applyId);
		for(int i=0;i<applyNum;i++){
			CouponVo couponVo = new CouponVo();
			couponVo.setApplyId(applyId);
			couponVo.setConfigId(configId);
			couponVo.setApplierId(applierId);
//			couponVo.setCouponCode(couponCode+(i+1));
			couponVo.setCouponCode("CC"+BillSNCreateFactory.createBillSN());
			couponVo.setCreateTime(curDate);
			couponVo.setEffectTime(effectTime);
			list.add(couponVo);
		}
		return list;
	}
	
	@Override
	public void updateCouponInvalid() {
		this.couponDao.updateCouponInvalid();
	}
	
	public static void main(String[] args) {
		Date date = new Date();
		System.out.println(DateUtil.format(DateUtil.format(DateUtil.operDay(date, 7),true)+" 23:59:59"));
	}

	@Override
	public void unbundCouponApply() {
		this.couponDao.unbundCouponApply();
	}

	@Override
	public String getLastCouponCode() {
		return this.couponDao.selectLastCouponCode();
	}
	

}
