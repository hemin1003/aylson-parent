package com.aylson.dc.mem.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class FeedBackReply implements Serializable {
	
	
	private static final long serialVersionUID = 8296440817260840078L;
	
	
	private Integer id;           		          //主键
	private Integer feedBackId;           		  //反馈id
	private String replyCont;           		  //回复内容
	private Date replyTime;           		      //回复时间
	private Integer replyType;           		  //回复对象类型：1提问人 2 客服人员
	
	
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
	
	public String getReplyCont() {
		return replyCont;
	}
	public void setReplyCont(String replyCont) {
		this.replyCont = replyCont;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}
	
	public Integer getReplyType() {
		return replyType;
	}
	public void setReplyType(Integer replyType) {
		this.replyType = replyType;
	}
	
	
}
