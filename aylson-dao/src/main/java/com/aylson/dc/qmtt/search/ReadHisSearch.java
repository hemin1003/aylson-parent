package com.aylson.dc.qmtt.search;

import com.aylson.core.frame.search.BaseSearch;

public class ReadHisSearch  extends BaseSearch{

	private static final long serialVersionUID = -1756376702398205035L;
	
	//模糊查询
	private String phoneNumLike;		//手机号码

	public String getPhoneNumLike() {
		return phoneNumLike;
	}

	public void setPhoneNumLike(String phoneNumLike) {
		this.phoneNumLike = phoneNumLike;
	}
}
