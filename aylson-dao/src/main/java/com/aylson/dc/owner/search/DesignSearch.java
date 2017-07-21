package com.aylson.dc.owner.search;

import com.aylson.core.frame.search.BaseSearch;

public class DesignSearch extends BaseSearch {

	private static final long serialVersionUID = 117463883066983230L;
	
	
	//匹配查询
	private Integer id;                      //主键
	private Integer appointId;               //预约id
	//模糊查询


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getAppointId() {
		return appointId;
	}
	public void setAppointId(Integer appointId) {
		this.appointId = appointId;
	}
	
	
}
