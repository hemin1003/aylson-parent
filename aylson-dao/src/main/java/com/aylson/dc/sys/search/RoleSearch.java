package com.aylson.dc.sys.search;

import com.aylson.core.frame.search.BaseSearch;

public class RoleSearch extends BaseSearch {

	private static final long serialVersionUID = 6394102566863204762L;
	
	private Integer id;           //主键
	private String roleName;      //角色名称
	private String roleCode;      //角色编码
	private String roleNameLike;  //角色名称-模糊查询
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
	public String getRoleNameLike() {
		return roleNameLike;
	}
	public void setRoleNameLike(String roleNameLike) {
		this.roleNameLike = roleNameLike;
	}
	
	
}
