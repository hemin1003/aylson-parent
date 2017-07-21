package com.aylson.dc.sys.search;

import com.aylson.core.frame.search.BaseSearch;

public class UserSearch extends BaseSearch {

	private static final long serialVersionUID = 7055314584366242417L;

	private Integer id;             //主键
	private Integer parentId;       //父ID
	private String userName;        //用户名
	private Integer status;         //用户状态 :0禁用 1 前台登录 2 后台登录
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
