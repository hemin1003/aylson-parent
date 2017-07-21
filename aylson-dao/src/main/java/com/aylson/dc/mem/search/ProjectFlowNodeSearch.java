package com.aylson.dc.mem.search;

import com.aylson.core.frame.search.BaseSearch;

public class ProjectFlowNodeSearch extends BaseSearch {

	private static final long serialVersionUID = 4344236277017429712L;

	//匹配查询
	private Integer id;           		          //主键
	private Integer projectId;           		  //工程id
	private Integer status;           		      //状态
	private Integer operId;           		      //操作人id
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getOperId() {
		return operId;
	}
	public void setOperId(Integer operId) {
		this.operId = operId;
	}
	
}
