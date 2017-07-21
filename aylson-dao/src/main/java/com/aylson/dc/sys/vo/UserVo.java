package com.aylson.dc.sys.vo;

import com.aylson.dc.sys.po.User;

public class UserVo extends User {

	private static final long serialVersionUID = -4073787568962213703L;
	
	private String validateCode;   //验证码

	public String getValidateCode() {
		return validateCode;
	}
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	
	
}
