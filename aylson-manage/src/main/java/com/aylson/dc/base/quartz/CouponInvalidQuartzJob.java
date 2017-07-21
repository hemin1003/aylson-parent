package com.aylson.dc.base.quartz;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import com.aylson.core.utils.SpringBeanManager;
import com.aylson.dc.partner.service.CouponService;
import com.aylson.utils.DateUtil;

/**
 * 优惠券定时器
 * @author wwx
 *
 */
@Service
public class CouponInvalidQuartzJob extends QuartzJobBean{

	@Autowired
	private CouponService couponService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		if (couponService == null) {
			couponService = SpringBeanManager.getBean(CouponService.class);
		}
		System.out.println("时间"+DateUtil.format(new Date())+":定时器【优惠券定时器】开始................");
		this.couponService.updateCouponInvalid();    //优惠券过期失效
		this.couponService.unbundCouponApply();      //将已过期的申请解绑
		System.out.println("时间"+DateUtil.format(new Date())+":定时器【优惠券定时器】结束................");
	}

}
