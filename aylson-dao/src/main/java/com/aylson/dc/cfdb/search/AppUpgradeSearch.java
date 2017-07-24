package com.aylson.dc.cfdb.search;

import com.aylson.core.frame.search.BaseSearch;

public class AppUpgradeSearch  extends BaseSearch{

	private static final long serialVersionUID = 3910758552507182561L;

	//匹配查询
	private Integer status;		//版本状态
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
