package com.aylson.dc.qmtt.search;

import com.aylson.core.frame.search.BaseSearch;

public class ShareUserHisSearch  extends BaseSearch{

	private static final long serialVersionUID = -2219868499799737769L;
	
	//模糊查询
	private String masterPhoneNumLike;	//师傅手机号码
	private String studentPhoneNumLike;	//徒弟手机号码
	
	public String getMasterPhoneNumLike() {
		return masterPhoneNumLike;
	}
	public void setMasterPhoneNumLike(String masterPhoneNumLike) {
		this.masterPhoneNumLike = masterPhoneNumLike;
	}
	public String getStudentPhoneNumLike() {
		return studentPhoneNumLike;
	}
	public void setStudentPhoneNumLike(String studentPhoneNumLike) {
		this.studentPhoneNumLike = studentPhoneNumLike;
	}
	
}
