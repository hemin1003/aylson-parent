package com.aylson.dc.sys.search;

import com.aylson.core.frame.search.BaseSearch;

public class CouponUserRelationsSearch extends BaseSearch{

	private static final long serialVersionUID = -9899568787874L;
	
	//匹配查询
	private Integer id;             		 
	private String userId;             	
	private String phoneNum;
	private Integer isUsed;	//是否已使用：0=未使用，默认值；1=已使用；-1=已过期
	private Integer activityFkid;
	private Integer couponFkid;
	
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
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public Integer getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}
	public Integer getCouponFkid() {
		return couponFkid;
	}
	public void setCouponFkid(Integer couponFkid) {
		this.couponFkid = couponFkid;
	}
	public Integer getActivityFkid() {
		return activityFkid;
	}
	public void setActivityFkid(Integer activityFkid) {
		this.activityFkid = activityFkid;
	}
}
