package com.aylson.dc.mem.search;

import com.aylson.core.frame.search.BaseSearch;

public class FeedBackReplySearch extends BaseSearch {

	
	private static final long serialVersionUID = 4667801863564028429L;
	
	
	//匹配查询
	private Integer id;           		          //主键
	private Integer feedBackId;           		  //反馈id
	//模糊查询
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getFeedBackId() {
		return feedBackId;
	}
	public void setFeedBackId(Integer feedBackId) {
		this.feedBackId = feedBackId;
	}
	
	
}
