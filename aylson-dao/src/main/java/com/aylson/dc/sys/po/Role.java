package com.aylson.dc.sys.po;

import java.io.Serializable;

public class Role implements Serializable{

	private static final long serialVersionUID = -4935736690153534641L;
	
	private Integer id;           //主键
	private String roleName;      //角色名称
	private String roleCode;      //角色编码
	private String remark;        //角色描述
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
