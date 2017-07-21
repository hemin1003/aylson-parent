package com.aylson.dc.base.common;

import com.aylson.core.utils.SpringBeanManager;
import com.aylson.dc.partner.service.CouponService;
import com.aylson.utils.StringUtil;

/**
 * 系统初始化
 * 
 */
public class SystemInitializeJob {
	
	private static boolean hadInit = true;//是否需要初始化

	public static void start() {
		if (hadInit) {
			hadInit = false;
			initCurrentMaxBillSN();
		}
	}
	
	private static void initCurrentMaxBillSN() {
		CouponService couponService = SpringBeanManager.getBean(CouponService.class);
		String lastCouponCode = couponService.getLastCouponCode();
		long sn = 0;
		if(StringUtil.isNotEmpty(lastCouponCode) && lastCouponCode.indexOf("CC") != -1){
			sn = Long.valueOf(lastCouponCode.substring(2));
		}
		BillSNCreateFactory.setCurrentMaxBillSN(sn);
	}
	
	
}
