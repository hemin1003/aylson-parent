package com.aylson.dc.cfdb.search;

import com.aylson.core.frame.search.BaseSearch;

public class SdkTasklistHisSearch extends BaseSearch {
	
	private static final long serialVersionUID = 149927255798956627L;
	
	// 模糊查询
	private String deviceidLike; // 手机唯一标识码

	public String getDeviceidLike() {
		return deviceidLike;
	}

	public void setDeviceidLike(String deviceidLike) {
		this.deviceidLike = deviceidLike;
	}
}
