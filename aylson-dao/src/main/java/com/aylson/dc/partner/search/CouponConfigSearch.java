package com.aylson.dc.partner.search;

import com.aylson.core.frame.search.BaseSearch;

public class CouponConfigSearch extends BaseSearch {
	
	private static final long serialVersionUID = -798123605149835018L;
	
	//匹配查询
	private Integer id;                             //主键
	private Integer state;                          //状态
	//模糊查询
	private String couponNameLike;                  //券名
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	public String getCouponNameLike() {
		return couponNameLike;
	}
	public void setCouponNameLike(String couponNameLike) {
		this.couponNameLike = couponNameLike;
	}
	
	
}
