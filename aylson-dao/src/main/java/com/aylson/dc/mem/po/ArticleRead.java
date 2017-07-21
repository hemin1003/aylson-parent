package com.aylson.dc.mem.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ArticleRead implements Serializable {
	
	
	private static final long serialVersionUID = 2100475585178993815L;
	
	
	private Integer id;           		          //主键
	private Integer articleId;           		  //标题id
	private Date readTime;           		      //阅读时间
	private String reader;           		      //阅读人
	private Integer readerId;           		  //阅读人id
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getReadTime() {
		return readTime;
	}
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
	
	public String getReader() {
		return reader;
	}
	public void setReader(String reader) {
		this.reader = reader;
	}
	
	public Integer getReaderId() {
		return readerId;
	}
	public void setReaderId(Integer readerId) {
		this.readerId = readerId;
	}
	
	
	
	
	
}
