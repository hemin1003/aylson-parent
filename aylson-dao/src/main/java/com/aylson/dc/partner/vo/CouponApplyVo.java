package com.aylson.dc.partner.vo;

import com.aylson.dc.partner.po.CouponApply;

public class CouponApplyVo extends CouponApply {

	private static final long serialVersionUID = 2427102099747521406L;
	
	private String applier;                     //申请人名称
	private String applierPhone;                //申请人电话
	private Integer couponValue;                //申请优惠券券值
	private Integer orderNum;                   //成单数量
	
	public String getApplier() {
		return applier;
	}
	public void setApplier(String applier) {
		this.applier = applier;
	}
	
	public String getApplierPhone() {
		return applierPhone;
	}
	public void setApplierPhone(String applierPhone) {
		this.applierPhone = applierPhone;
	}
	
	public Integer getCouponValue() {
		return couponValue;
	}
	public void setCouponValue(Integer couponValue) {
		this.couponValue = couponValue;
	}
	
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	
	
}
