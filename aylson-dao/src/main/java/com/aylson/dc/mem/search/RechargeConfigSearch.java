package com.aylson.dc.mem.search;

import com.aylson.core.frame.search.BaseSearch;

public class RechargeConfigSearch extends BaseSearch {

	private static final long serialVersionUID = -3802963539545861478L;
	
	//匹配查询
	private Integer id;           		          //主键
	private Integer status;           		      //状态:1：新建；2：上架 3：下架	
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
	
	public String getTitleLike() {
		return titleLike;
	}
	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}
	
	
}
