package com.aylson.dc.sys.po;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = -490896280361896195L;
	
	private Integer id;                //主键
	private String userName;           //用户名
	private String pwd;                //密码
	private Integer type;              //用户类型：1组织机构 2 代理商
	private Integer status;            //用户状态 
	private Integer roleId;            //角色id
	private String roleName;           //角色名称
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}
