package com.aylson.dc.mem.vo;

import java.util.List;

import com.aylson.core.frame.base.Page;
import com.aylson.dc.mem.po.FeedBack;

public class FeedBackVo extends FeedBack {

	private static final long serialVersionUID = -3638968030464277172L;

	private String feedbackTimeStr;           		  //反馈时间
	private List<FeedBackReplyVo> feedBackReplyList;  //回复列表
	private Page<FeedBackReplyVo> feedBackReplyPage;  //回复列表

	public String getFeedbackTimeStr() {
		return feedbackTimeStr;
	}
	public void setFeedbackTimeStr(String feedbackTimeStr) {
		this.feedbackTimeStr = feedbackTimeStr;
	}
	
	public List<FeedBackReplyVo> getFeedBackReplyList() {
		return feedBackReplyList;
	}
	public void setFeedBackReplyList(List<FeedBackReplyVo> feedBackReplyList) {
		this.feedBackReplyList = feedBackReplyList;
	}
	
	public Page<FeedBackReplyVo> getFeedBackReplyPage() {
		return feedBackReplyPage;
	}
	public void setFeedBackReplyPage(Page<FeedBackReplyVo> feedBackReplyPage) {
		this.feedBackReplyPage = feedBackReplyPage;
	}
	
	
}
