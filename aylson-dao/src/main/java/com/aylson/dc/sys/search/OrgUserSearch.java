package com.aylson.dc.sys.search;

import com.aylson.core.frame.search.BaseSearch;

public class OrgUserSearch extends BaseSearch {

	private static final long serialVersionUID = -9221401703929609469L;
	
	private Integer id;                //主键
	private Integer orgId;             //所属部门id
	private Integer status;            //用户状态
	private String userNameLike;       //用户名称-模糊查询
	private String realNameLike;       //姓名-模糊查询
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getUserNameLike() {
		return userNameLike;
	}
	public void setUserNameLike(String userNameLike) {
		this.userNameLike = userNameLike;
	}
	
	public String getRealNameLike() {
		return realNameLike;
	}
	public void setRealNameLike(String realNameLike) {
		this.realNameLike = realNameLike;
	}
	

}
