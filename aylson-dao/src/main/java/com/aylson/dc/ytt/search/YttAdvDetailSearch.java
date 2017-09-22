package com.aylson.dc.ytt.search;

import com.aylson.core.frame.search.BaseSearch;

public class YttAdvDetailSearch  extends BaseSearch{

	private static final long serialVersionUID = -6848948717192026817L;

	//模糊查询
	private String tagNameLike;		//新闻Tab标识

	public String getTagNameLike() {
		return tagNameLike;
	}

	public void setTagNameLike(String tagNameLike) {
		this.tagNameLike = tagNameLike;
	}
	
}
