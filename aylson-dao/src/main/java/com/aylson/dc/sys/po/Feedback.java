package com.aylson.dc.sys.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Feedback implements Serializable{

	private static final long serialVersionUID = 7314894121923384198L;
	
	private Integer id;             		//主键  
	private String title;            		//标题
	private String describe;                //详细描述  
	private String feedbacker;              //反馈人
	private Integer feedbackerId;           //反馈人id  
	private String feedbackPhone;           //反馈人电话 
	private Date  feedbackTime;             //反馈时间  
	private Integer status;                 //状态：0 未解决 1 已解决 
	
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
	
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
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
	
	public String getFeedbackPhone() {
		return feedbackPhone;
	}
	public void setFeedbackPhone(String feedbackPhone) {
		this.feedbackPhone = feedbackPhone;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getFeedbackTime() {
		return feedbackTime;
	}
	public void setFeedbackTime(Date feedbackTime) {
		this.feedbackTime = feedbackTime;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	

}
