package com.aylson.dc.mem.po;

import java.io.Serializable;
import java.util.Date;

public class RechargeRecords implements Serializable {

	private static final long serialVersionUID = 5543519379549930816L;
	
	private Integer id;                     //主键
	private Integer memberId;               //会员id
	private String chargeId;                //充值id
	private Date createTime;                //创建时间
	private String orderNo;                 //订单号
	private Float amount;                   //订单金额
	private Integer busiType;               //业务类型
	private Integer status;                 //状态:0 已提交 1完成支付 2 支付失败 4 已退款
	private String description;             //描述
	private Integer gold;                   //价值金币
	private Integer busiId;                 //业务id
	
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
	
	public String getChargeId() {
		return chargeId;
	}
	public void setChargeId(String chargeId) {
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
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getGold() {
		return gold;
	}
	public void setGold(Integer gold) {
		this.gold = gold;
	}
	
	public Integer getBusiId() {
		return busiId;
	}
	public void setBusiId(Integer busiId) {
		this.busiId = busiId;
	}
	
	
}
