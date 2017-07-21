package com.aylson.dc.owner.search;

import com.aylson.core.frame.search.BaseSearch;

public class DesignDetailDWSearch extends BaseSearch {

	private static final long serialVersionUID = -4217613104304160721L;
	
	//匹配查询
	private Integer id;                      //主键  
	private Integer appointId;               //预约id
	private Integer designId;                //设计信息id
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
	
	public Integer getDesignId() {
		return designId;
	}
	public void setDesignId(Integer designId) {
		this.designId = designId;
	}
	
	
}
