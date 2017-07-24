package com.aylson.dc.cfdb.search;

import com.aylson.core.frame.search.BaseSearch;

public class UserTasklistSearch  extends BaseSearch{

	private static final long serialVersionUID = 7258302646362351840L;
	
	//匹配查询
	private Integer statusFlag;	//任务状态标识

	public Integer getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(Integer statusFlag) {
		this.statusFlag = statusFlag;
	}
}
