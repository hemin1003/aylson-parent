package com.aylson.dc.cfdb.search;

import com.aylson.core.frame.search.BaseSearch;

public class UserTasklistHisSearch  extends BaseSearch{

	private static final long serialVersionUID = 2015547934553985736L;
	
	//模糊查询
	private String phoneIdLike;		//手机唯一标识码
	private String taskNameLike;		//任务名称
	
	public String getPhoneIdLike() {
		return phoneIdLike;
	}
	public void setPhoneIdLike(String phoneIdLike) {
		this.phoneIdLike = phoneIdLike;
	}
	public String getTaskNameLike() {
		return taskNameLike;
	}
	public void setTaskNameLike(String taskNameLike) {
		this.taskNameLike = taskNameLike;
	}
}
