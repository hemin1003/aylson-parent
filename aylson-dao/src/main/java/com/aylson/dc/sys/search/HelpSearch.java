package com.aylson.dc.sys.search;

import com.aylson.core.frame.search.BaseSearch;

public class HelpSearch extends BaseSearch{

	private static final long serialVersionUID = -5492369098434657574L;
	
	//匹配查询
	private Integer id;             		//主键
	private Integer type;             		//类型：1 设计师 2开拓者 3业主
	//模糊查询
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

	
}
