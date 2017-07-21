package com.aylson.dc.mem.search;

import com.aylson.core.frame.search.BaseSearch;

public class PublishSearch extends BaseSearch {

	private static final long serialVersionUID = -8107076350238538836L;
	
	//匹配查询
	private Integer id;           		          //主键
	private Integer status;           		      //状态
	private Integer type;           		      //类别:1通知公告 2：新闻动态
	private Integer readerId;                     //类别:读取人id
	//模糊查询
	private String titleLike;                     //标题
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public Integer getReaderId() {
		return readerId;
	}
	public void setReaderId(Integer readerId) {
		this.readerId = readerId;
	}
	
	public String getTitleLike() {
		return titleLike;
	}
	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}
	
	
}
