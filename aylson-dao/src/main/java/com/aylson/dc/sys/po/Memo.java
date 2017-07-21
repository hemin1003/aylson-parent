package com.aylson.dc.sys.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Memo implements Serializable{

	
	private static final long serialVersionUID = -7159368017855533199L;
	
	private Integer id;             		//主键  
	private Integer userId;                 //备忘人id  
	private String content;             	//备忘内容  
	private Date memoTime;             		//备忘时间
	private Integer memoLevel;             	//备忘级别 :1一般 2中级 3高级
	private Date createTime;             	//创建时间  
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getMemoTime() {
		return memoTime;
	}
	public void setMemoTime(Date memoTime) {
		this.memoTime = memoTime;
	}
	
	public Integer getMemoLevel() {
		return memoLevel;
	}
	public void setMemoLevel(Integer memoLevel) {
		this.memoLevel = memoLevel;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
