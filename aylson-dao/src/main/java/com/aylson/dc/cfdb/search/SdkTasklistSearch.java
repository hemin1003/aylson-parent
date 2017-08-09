package com.aylson.dc.cfdb.search;

import com.aylson.core.frame.search.BaseSearch;

public class SdkTasklistSearch extends BaseSearch {

	private static final long serialVersionUID = -7431414669683803445L;

	//匹配查询
	private String sdkType; // 广告来源类型
	// 模糊查询
	private String titleLike; // 广告名称

	public String getSdkType() {
		return sdkType;
	}

	public void setSdkType(String sdkType) {
		this.sdkType = sdkType;
	}

	public String getTitleLike() {
		return titleLike;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}
}
