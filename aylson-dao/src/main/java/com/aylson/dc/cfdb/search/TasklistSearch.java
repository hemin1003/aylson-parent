package com.aylson.dc.cfdb.search;

import com.aylson.core.frame.search.BaseSearch;

public class TasklistSearch  extends BaseSearch{

	private static final long serialVersionUID = 7258302646362351840L;
	
	//模糊查询
	private String taskNameLike;		//任务名称

	public String getTaskNameLike() {
		return taskNameLike;
	}
	public void setTaskNameLike(String taskNameLike) {
		this.taskNameLike = taskNameLike;
	}
}
