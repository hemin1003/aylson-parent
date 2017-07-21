package com.aylson.dc.mem.vo;

import com.aylson.dc.mem.po.ProjectInfo;

public class ClientInfoVo extends ProjectInfo {

	private static final long serialVersionUID = 2486679305360098987L;

	private Integer sourceType;    //来源类型：1代理商 2工长
	private Integer stillCount;    //还可以派送的数量
	
	public Integer getSourceType() {
		return sourceType;
	}
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}
	public Integer getStillCount() {
		return stillCount;
	}
	public void setStillCount(Integer stillCount) {
		this.stillCount = stillCount;
	}
	
	
}
