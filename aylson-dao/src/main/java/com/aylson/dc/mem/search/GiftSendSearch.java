package com.aylson.dc.mem.search;

import com.aylson.core.frame.search.BaseSearch;

public class GiftSendSearch extends BaseSearch {

	private static final long serialVersionUID = 5929547573038567455L;
	
	//匹配查询
	private Integer id;           		       //主键
	//模糊查询
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	
}
