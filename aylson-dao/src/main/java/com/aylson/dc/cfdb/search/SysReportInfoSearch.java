package com.aylson.dc.cfdb.search;

import com.aylson.core.frame.search.BaseSearch;

public class SysReportInfoSearch  extends BaseSearch{

	private static final long serialVersionUID = -8715608164019757829L;

	private Integer type;
	private String currentTime;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
}
