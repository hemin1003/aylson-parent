package com.aylson.dc.sys.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class CouponUserRelations implements Serializable{

	private static final long serialVersionUID = -2546546546565806L;
	
	private Integer id;             		 
	private String userId;             	
	private String userName;             		 
	private String phoneNum;
	private Integer activityFkid;
	private Integer couponFkid;
	private Date createdTime;
	private Integer isUsed;	//是否已使用：0=未使用，默认值；1=已使用
	private Date usedTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public Integer getActivityFkid() {
		return activityFkid;
	}
	public void setActivityFkid(Integer activityFkid) {
		this.activityFkid = activityFkid;
	}
	public Integer getCouponFkid() {
		return couponFkid;
	}
	public void setCouponFkid(Integer couponFkid) {
		this.couponFkid = couponFkid;
	}
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Integer getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getUsedTime() {
		return usedTime;
	}
	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}
	
}