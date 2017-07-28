package com.aylson.dc.cfdb.po;

import java.io.Serializable;

public class ImUsers implements Serializable{

	private static final long serialVersionUID = 6724807026829015944L;

	private String phoneId;		//手机唯一标识码
	private String balance;		//个人余额
	private String totalIncome;	//累积收入
	private String createDate;	//创建时间-注册
	private String updateDate;	//更新时间
	
	private String todayIncome;;	//今日收入
	private String currentTime;	//当前日期
	
	public String getPhoneId() {
		return phoneId;
	}
	public void setPhoneId(String phoneId) {
		this.phoneId = phoneId;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
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
	public String getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
	public String getTotalIncome() {
		return totalIncome;
	}
	public void setTotalIncome(String totalIncome) {
		this.totalIncome = totalIncome;
	}
	public String getTodayIncome() {
		return todayIncome;
	}
	public void setTodayIncome(String todayIncome) {
		this.todayIncome = todayIncome;
	}
}
