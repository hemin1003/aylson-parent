package com.aylson.dc.sys.search;

import com.aylson.core.frame.search.BaseSearch;

public class ActivityRegisterSearch extends BaseSearch {

	private static final long serialVersionUID = 2332970990127040051L;
	
	//匹配查询
	private Integer id;             		//主键  
	private Integer actId;             		//活动id  
	private String registerPhone;           //报名人电话  

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getActId() {
		return actId;
	}
	public void setActId(Integer actId) {
		this.actId = actId;
	}
	
	public String getRegisterPhone() {
		return registerPhone;
	}
	public void setRegisterPhone(String registerPhone) {
		this.registerPhone = registerPhone;
	}
	
	
}
