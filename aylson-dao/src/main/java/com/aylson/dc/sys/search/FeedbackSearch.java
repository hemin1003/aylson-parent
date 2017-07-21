package com.aylson.dc.sys.search;

import com.aylson.core.frame.search.BaseSearch;

public class FeedbackSearch extends BaseSearch {

	private static final long serialVersionUID = 6977894863276654916L;
	
	//匹配查询
	private Integer id;             		//主键  
	private String feedbacker;              //反馈人
	private Integer feedbackerId;           //反馈人id  
	private Integer status;                 //状态：0 未解决 1 已解决 
	private String feedbackTime;            //反馈时间
	//模糊查询
	private String titleLike;               //标题
	private String feedbackerLike;          //反馈人
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getFeedbacker() {
		return feedbacker;
	}
	public void setFeedbacker(String feedbacker) {
		this.feedbacker = feedbacker;
	}
	
	public Integer getFeedbackerId() {
		return feedbackerId;
	}
	public void setFeedbackerId(Integer feedbackerId) {
		this.feedbackerId = feedbackerId;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getFeedbackTime() {
		return feedbackTime;
	}
	public void setFeedbackTime(String feedbackTime) {
		this.feedbackTime = feedbackTime;
	}
	
	public String getTitleLike() {
		return titleLike;
	}
	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}
	
	public String getFeedbackerLike() {
		return feedbackerLike;
	}
	public void setFeedbackerLike(String feedbackerLike) {
		this.feedbackerLike = feedbackerLike;
	}
	
	
}
