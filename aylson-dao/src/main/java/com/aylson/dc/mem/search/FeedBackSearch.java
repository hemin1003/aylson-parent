package com.aylson.dc.mem.search;

import com.aylson.core.frame.search.BaseSearch;

public class FeedBackSearch extends BaseSearch {

	private static final long serialVersionUID = -8107076350238538836L;
	
	//匹配查询
	private Integer id;           		          //主键
	private Integer state;           		      //状态：1 待回复  2 已回答  3已查看
	private String feedbackTimeStr;           	  //反馈时间
	private String feedbackTimeBegin;             //反馈时间-开始
	private String feedbackTimeEnd;           	  //反馈时间-结束
	private Integer feedbackerId;           	  //反馈人id
	private Integer feedbackerType;               //反馈者类型,与会员类型一致 1设计师 2产业工人 3开拓者 4业主客户 5艾臣合伙人
	//模糊查询
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	public String getFeedbackTimeStr() {
		return feedbackTimeStr;
	}
	public void setFeedbackTimeStr(String feedbackTimeStr) {
		this.feedbackTimeStr = feedbackTimeStr;
	}
	
	public String getFeedbackTimeBegin() {
		return feedbackTimeBegin;
	}
	public void setFeedbackTimeBegin(String feedbackTimeBegin) {
		this.feedbackTimeBegin = feedbackTimeBegin;
	}
	
	public String getFeedbackTimeEnd() {
		return feedbackTimeEnd;
	}
	public void setFeedbackTimeEnd(String feedbackTimeEnd) {
		this.feedbackTimeEnd = feedbackTimeEnd;
	}
	
	public Integer getFeedbackerId() {
		return feedbackerId;
	}
	public void setFeedbackerId(Integer feedbackerId) {
		this.feedbackerId = feedbackerId;
	}
	
	public Integer getFeedbackerType() {
		return feedbackerType;
	}
	public void setFeedbackerType(Integer feedbackerType) {
		this.feedbackerType = feedbackerType;
	}
	
	
}
