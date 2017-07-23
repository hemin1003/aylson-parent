package com.aylson.dc.cfdb.search;

import com.aylson.core.frame.search.BaseSearch;

public class ApkUrlSearch  extends BaseSearch{

	private static final long serialVersionUID = -4017139035488843947L;
	
	//模糊查询
	private String appNameLike;		//应用名称

	public String getAppNameLike() {
		return appNameLike;
	}

	public void setAppNameLike(String appNameLike) {
		this.appNameLike = appNameLike;
	}
}
