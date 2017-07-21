package com.aylson.dc.busi.search;

import com.aylson.core.frame.search.BaseSearch;

public class CostSearch extends BaseSearch{

	
	private static final long serialVersionUID = 191492215795214237L;
	
	//匹配查询
	private Integer id;             		//主键  
	private Integer sourceType;             //项目来源类型 
	private Integer sourceId;             	//项目来源id 
	private Integer costType;             	//费用类型 
	//模糊查询
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getSourceType() {
		return sourceType;
	}
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}
	
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	
	public Integer getCostType() {
		return costType;
	}
	public void setCostType(Integer costType) {
		this.costType = costType;
	}
	
	
}
