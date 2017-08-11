package com.aylson.dc.cfdb.po;

import java.io.Serializable;

/**
 * 收益记录
 * @author Minbo
 */
public class IncomeHis implements Serializable {

	private static final long serialVersionUID = 1945745595706808785L;
	
	private String id;
	private String phoneId;
	private String taskId;
	private String logoUrl;
	private String taskName;
	private String incomeTime;
	private String income;		//用户收益金额
	private String sysIncome;	//平台收益金额
	private String createDate;
	private String updateDate;
	private Integer flag;	//1=加钱；2=扣钱
	private Integer channel;	
	private String createdBy;	//创建人
	private String updatedBy;	//更新人
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPhoneId() {
		return phoneId;
	}
	public void setPhoneId(String phoneId) {
		this.phoneId = phoneId;
	}
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
	public String getIncomeTime() {
		return incomeTime;
	}
	public void setIncomeTime(String incomeTime) {
		this.incomeTime = incomeTime;
	}
	public String getIncome() {
		return income;
	}
	public void setIncome(String income) {
		this.income = income;
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
	public String getSysIncome() {
		return sysIncome;
	}
	public void setSysIncome(String sysIncome) {
		this.sysIncome = sysIncome;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Integer getChannel() {
		return channel;
	}
	public void setChannel(Integer channel) {
		this.channel = channel;
	}
}