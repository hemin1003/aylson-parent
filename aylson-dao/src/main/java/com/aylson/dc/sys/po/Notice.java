package com.aylson.dc.sys.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Notice implements Serializable{

	private static final long serialVersionUID = 75605020711551570L;
	
	private Integer id;             		//主键  
	private String title;             		//标题
	private String content;             	//内容  
	private Integer state;             		//状态：0草稿 1发布 2结束发布
	private Integer noticeObj;             	//通知对象：1：设计师 2：工长 3：开拓者 4：业主 5：艾臣合伙人  
	private Date createTime;             	//创建时间
	private Date publishTime;             	//更新时间
	
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
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	public Integer getNoticeObj() {
		return noticeObj;
	}
	public void setNoticeObj(Integer noticeObj) {
		this.noticeObj = noticeObj;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	

}
