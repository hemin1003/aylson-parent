package com.aylson.dc.mem.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class FeedBack implements Serializable {
	
	private static final long serialVersionUID = -6668990762513025104L;
	
	private Integer id;           		          //主键
	private String title;           		      //反馈主题
	private String content;           		      //反馈内容
	private Integer feedbackerId;           	  //反馈人id
	private Date feedbackTime;           		  //反馈时间
	private Date replyTime;           		      //回复时间
	private Integer state;           		      //状态：1 待回复  2 已回答  3已查看 4反馈人已查看
	private Integer feedbackerType;               //反馈者类型,与会员类型一致 1设计师 2产业工人 3开拓者 4业主客户 5艾臣合伙人
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Integer getFeedbackerId() {
		return feedbackerId;
	}
	public void setFeedbackerId(Integer feedbackerId) {
		this.feedbackerId = feedbackerId;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getFeedbackTime() {
		return feedbackTime;
	}
	public void setFeedbackTime(Date feedbackTime) {
		this.feedbackTime = feedbackTime;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	public Integer getFeedbackerType() {
		return feedbackerType;
	}
	public void setFeedbackerType(Integer feedbackerType) {
		this.feedbackerType = feedbackerType;
	}
	
	
}
