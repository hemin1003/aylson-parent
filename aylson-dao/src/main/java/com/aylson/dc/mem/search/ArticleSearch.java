package com.aylson.dc.mem.search;

import com.aylson.core.frame.search.BaseSearch;

public class ArticleSearch extends BaseSearch {

	private static final long serialVersionUID = 892961190187902869L;

	//匹配查询
	private Integer id;           		          //主键
	private Integer publisherId;           		  //发布人id
	private Integer status;           		      //状态
	private Integer articleId;           		  //文章id
	private Integer readerId;           		  //读取人id
	private Integer replierId;           		  //回复人id
	//模糊查询
	private String titleLike;           		  //标题
	private String publisherLike;           	  //发布人
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTitleLike() {
		return titleLike;
	}
	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}
	
	public Integer getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	
	public String getPublisherLike() {
		return publisherLike;
	}
	public void setPublisherLike(String publisherLike) {
		this.publisherLike = publisherLike;
	}
	
	public Integer getReaderId() {
		return readerId;
	}
	public void setReaderId(Integer readerId) {
		this.readerId = readerId;
	}
	
	public Integer getReplierId() {
		return replierId;
	}
	public void setReplierId(Integer replierId) {
		this.replierId = replierId;
	}
	
	
}
