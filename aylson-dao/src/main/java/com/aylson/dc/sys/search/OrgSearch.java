package com.aylson.dc.sys.search;

import com.aylson.core.frame.search.BaseSearch;

public class OrgSearch extends BaseSearch {

	private static final long serialVersionUID = 7055314584366242417L;

	private Integer id;             //主键
	private Integer parentId;       //父ID
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	
}
