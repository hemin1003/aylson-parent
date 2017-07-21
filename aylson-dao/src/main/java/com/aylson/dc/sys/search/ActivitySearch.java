package com.aylson.dc.sys.search;

import com.aylson.core.frame.search.BaseSearch;

public class ActivitySearch extends BaseSearch {

	private static final long serialVersionUID = -7526741828087808833L;

	//匹配查询
	private Integer id;                      //主键 
	private Integer state;                   //状态：1新建 2发布 3结束
	//模糊查询
	private String actThemeLike;             //活动主题
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	public String getActThemeLike() {
		return actThemeLike;
	}
	public void setActThemeLike(String actThemeLike) {
		this.actThemeLike = actThemeLike;
	}
	
	
}
