package com.aylson.dc.cfdb.search;

import com.aylson.core.frame.search.BaseSearch;

public class TasklistSearch  extends BaseSearch{

	private static final long serialVersionUID = 7258302646362351840L;
	
	//匹配查询
	private Integer status;	//广告任务状态
	//模糊查询
	private String taskNameLike;		//广告任务名称

	public String getTaskNameLike() {
		return taskNameLike;
	}
	public void setTaskNameLike(String taskNameLike) {
		this.taskNameLike = taskNameLike;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
