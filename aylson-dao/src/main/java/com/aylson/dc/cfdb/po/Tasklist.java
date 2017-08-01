package com.aylson.dc.cfdb.po;

import java.io.Serializable;

public class Tasklist implements Serializable{
	
	private static final long serialVersionUID = -7114269352941860240L;
	
	private String taskId;		//任务唯一ID
	private String logoUrl;		//任务logo url地址
	private String taskName;		//任务名称
	private String taskTag;		//任务标签
	private Integer amount;		//任务剩余数量
	private String income;		//用户收益金额
	private Integer orderNo;		//排序编号
	private String goUrl;		//跳转url地址
	private String createDate;	//创建时间
	private String updateDate;	//更新时间
	private Integer status;		//任务状态
	private String createdBy;	//创建人
	private String updatedBy;	//更新人
	private String taskValue;	//平台金额
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskTag() {
		return taskTag;
	}
	public void setTaskTag(String taskTag) {
		this.taskTag = taskTag;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getIncome() {
		return income;
	}
	public void setIncome(String income) {
		this.income = income;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public String getGoUrl() {
		return goUrl;
	}
	public void setGoUrl(String goUrl) {
		this.goUrl = goUrl;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getTaskValue() {
		return taskValue;
	}
	public void setTaskValue(String taskValue) {
		this.taskValue = taskValue;
	}
}