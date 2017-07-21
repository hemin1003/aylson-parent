package com.aylson.dc.mem.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class PublishRead implements Serializable {
	
	private static final long serialVersionUID = 3907816987913541730L;
	
	private Integer id;           		          //主键
	private Integer publishId;           		  //发布主题id
	private Integer readerId;           		  //阅读人id
	private Date readTime;           		      //读取时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getPublishId() {
		return publishId;
	}
	public void setPublishId(Integer publishId) {
		this.publishId = publishId;
	}
	
	public Integer getReaderId() {
		return readerId;
	}
	public void setReaderId(Integer readerId) {
		this.readerId = readerId;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getReadTime() {
		return readTime;
	}
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
	
	
}
