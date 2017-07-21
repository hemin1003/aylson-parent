package com.aylson.dc.sys.search;

import com.aylson.core.frame.search.BaseSearch;

public class NoticeReadSearch extends BaseSearch{

	private static final long serialVersionUID = -5492369098434657574L;
	
	//匹配查询
	private Integer id;             		//主键  
	private Integer noticeId;             	//公告id
	private Integer readerId;               //读取人id
	//模糊查询
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}
	
	public Integer getReaderId() {
		return readerId;
	}
	public void setReaderId(Integer readerId) {
		this.readerId = readerId;
	}
	
	
}
