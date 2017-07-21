package com.aylson.dc.sys.search;

import com.aylson.core.frame.search.BaseSearch;

public class CouponRuleSearch extends BaseSearch{

	private static final long serialVersionUID = -549564898985574L;
	
	//匹配查询
	private Integer id;             		 
	private Integer couponType;   //类型：1 现金券
	private Integer isEnabled;	//是否启用：1=启用，0=禁用
	private Integer couponFkid;
	
	public Integer getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(Integer isEnabled) {
		this.isEnabled = isEnabled;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCouponType() {
		return couponType;
	}
	public void setCouponType(Integer couponType) {
		this.couponType = couponType;
	}
	public Integer getCouponFkid() {
		return couponFkid;
	}
	public void setCouponFkid(Integer couponFkid) {
		this.couponFkid = couponFkid;
	}
}
