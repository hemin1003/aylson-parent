package com.aylson.dc.mem.vo;

import java.util.List;

import com.aylson.dc.mem.po.Article;

public class ArticleVo extends Article {

	private static final long serialVersionUID = -7843319147617430903L;
	
	private Boolean isRead;                         //是否已读
	private List<ArticleReadVo> articleReadList;    //已读列表
	private String  photo;                          //会员头像
	private Integer replyCount;           		    //回复数量
	private Integer readCount;           		    //阅读数量

	public Boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
	
	public List<ArticleReadVo> getArticleReadList() {
		return articleReadList;
	}
	public void setArticleReadList(List<ArticleReadVo> articleReadList) {
		this.articleReadList = articleReadList;
	}
	
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public Integer getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}
	
	public Integer getReadCount() {
		return readCount;
	}
	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}
	
	
}
