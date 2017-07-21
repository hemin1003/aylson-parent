package com.aylson.dc.sys.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class CouponDetail implements Serializable{

	private static final long serialVersionUID = -3227529464561418L;
	
	private Integer id;             		//主键
	private String couponName;              //优惠券名称
	private Integer couponType;             //优惠券类型
	private Integer couponValue;            //券面值
	private String ruleDesc;                //兑换规则说明
	private Date startTime;                 //有效开始时间
	private Date endTime;                   //有效结束时间
	private String notice;                  //兑奖须知 
	private String serviceTel;              //客服电话
	private String comments;                //备注说明
	private Integer isEnabled;	            //是否启用：1=启用，0=禁用
	private Date createdTime;               //创建时间
	private String createdBy;               //创建人
	private Date updatedTime;               //更新时间
	private String updatedBy;               //更新人
	
	public Integer getCouponType() {
		return couponType;
	}
	public void setCouponType(Integer couponType) {
		this.couponType = couponType;
	}
	public Integer getCouponValue() {
		return couponValue;
	}
	public void setCouponValue(Integer couponValue) {
		this.couponValue = couponValue;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	public String getRuleDesc() {
		return ruleDesc;
	}
	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getServiceTel() {
		return serviceTel;
	}
	public void setServiceTel(String serviceTel) {
		this.serviceTel = serviceTel;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Integer getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(Integer isEnabled) {
		this.isEnabled = isEnabled;
	}
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
}
