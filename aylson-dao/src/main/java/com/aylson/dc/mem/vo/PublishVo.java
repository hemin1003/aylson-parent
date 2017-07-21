package com.aylson.dc.mem.vo;

import com.aylson.dc.mem.po.Publish;

public class PublishVo extends Publish {

	private static final long serialVersionUID = -7843319147617430903L;
	
	private Boolean isRead;              //是否已读

	public Boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	
}
