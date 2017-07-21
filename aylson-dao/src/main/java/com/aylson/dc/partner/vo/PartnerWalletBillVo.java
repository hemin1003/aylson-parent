package com.aylson.dc.partner.vo;

import java.util.Date;

import com.aylson.dc.partner.po.PartnerWalletBill;
import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class PartnerWalletBillVo extends PartnerWalletBill {

	private static final long serialVersionUID = 4797830317921973178L;
	
	private Integer orderState;             //订单状态
	private Date orderTime;           	    //订单更新时间
	private Float contractAmount;           //合同金额
	
	
	public Integer getOrderState() {
		return orderState;
	}
	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	
	public Float getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(Float contractAmount) {
		this.contractAmount = contractAmount;
	}
	
	
}
