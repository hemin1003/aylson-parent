package com.aylson.dc.mem.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ArticleReply implements Serializable {
	
	
	private static final long serialVersionUID = -6680446570507064968L;
	
	private Integer id;           		          //主键
	private Integer articleId;           		  //标题id
	private String content;           		      //回复内容
	private Date replyTime;           		      //回复时间
	private String replier;           		      //回复人
	private Integer replierId;           		  //回复人id
	
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
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}
	
	public String getReplier() {
		return replier;
	}
	public void setReplier(String replier) {
		this.replier = replier;
	}
	
	public Integer getReplierId() {
		return replierId;
	}
	public void setReplierId(Integer replierId) {
		this.replierId = replierId;
	}
	
	
}
