package com.aylson.dc.mem.search;

import java.util.Date;

import com.aylson.core.frame.search.BaseSearch;

public class RechargeRecordsSearch extends BaseSearch {

	private static final long serialVersionUID = -3484473691806293941L;
	
	//匹配查询
	private Integer id;                     //主键
	private Integer memberId;               //会员id
	private Integer chargeId;               //充值id
	private Date createTime;                //创建时间
	private String orderNo;                 //订单号
	private Float amount;                   //订单金额
	private Integer busiType;               //业务类型
	private Integer status;                 //状态
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
	public Integer getChargeId() {
		return chargeId;
	}
	public void setChargeId(Integer chargeId) {
		this.chargeId = chargeId;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	
	public Integer getBusiType() {
		return busiType;
	}
	public void setBusiType(Integer busiType) {
		this.busiType = busiType;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
