package com.aylson.dc.cfdb.search;

import com.aylson.core.frame.search.BaseSearch;

public class ImUsersSearch  extends BaseSearch{

	private static final long serialVersionUID = -2995647462966977780L;
	
	//模糊查询
	private String phoneIdLike; //手机唯一标识码

	public String getPhoneIdLike() {
		return phoneIdLike;
	}

	public void setPhoneIdLike(String phoneIdLike) {
		this.phoneIdLike = phoneIdLike;
	}
	
}
