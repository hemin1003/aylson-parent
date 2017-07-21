package com.aylson.dc.sys.vo;

import com.aylson.dc.sys.po.OrgUser;

public class OrgUserVo extends OrgUser {

	private static final long serialVersionUID = 3082276559298854496L;
	
	private String userName;           //用户名
	private String pwd;                //密码
	private String confirmPwd;         //确认密码
	private Integer type;              //用户类型
	private Integer status;            //用户状态 
	private String birthdayStr;        //出生日期
	private Integer roleId;            //角色id
	private String roleName;           //角色名称
	
	
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
	
	public String getConfirmPwd() {
		return confirmPwd;
	}
	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
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
	
	public String getBirthdayStr() {
		return birthdayStr;
	}
	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
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
