package com.aylson.dc.sys.vo;

import com.aylson.dc.sys.po.Notice;

public class NoticeVo extends Notice {

	private static final long serialVersionUID = -1739780781354624466L;
	
	private Boolean isRead;             //是否已读

	public Boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
	

}
