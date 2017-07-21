package com.aylson.dc.busi.search;

import com.aylson.core.frame.search.BaseSearch;

public class FlowNodeSearch extends BaseSearch{

	private static final long serialVersionUID = -2814615840472100666L;
	
	//匹配查询
	private Integer id;             		//主键  
	private Integer type;             		//流程类型：1 预约
	private Integer state;             		//状态
	private Integer sourceId;             	//来源id  
	private Integer sourceType;             //来源类型
	private Integer createId;             	//处理人用户id，对应sys_user的主键
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
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	
	public Integer getSourceType() {
		return sourceType;
	}
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}
	
	public Integer getCreateId() {
		return createId;
	}
	public void setCreateId(Integer createId) {
		this.createId = createId;
	}
	
	
}
