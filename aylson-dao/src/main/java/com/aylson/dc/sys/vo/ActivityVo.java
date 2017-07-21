package com.aylson.dc.sys.vo;

import com.aylson.core.frame.base.Page;
import com.aylson.dc.sys.po.Activity;

public class ActivityVo extends Activity {

	private static final long serialVersionUID = -6682256210531282177L;
	
	private String actBeginTimeStr;               //活动开始时间
	private String actEndTimeStr;                 //活动结束时间
	private Page<ActivityRegisterVo> registerList;//报名列表
	
	public String getActBeginTimeStr() {
		return actBeginTimeStr;
	}
	public void setActBeginTimeStr(String actBeginTimeStr) {
		this.actBeginTimeStr = actBeginTimeStr;
	}
	
	public String getActEndTimeStr() {
		return actEndTimeStr;
	}
	public void setActEndTimeStr(String actEndTimeStr) {
		this.actEndTimeStr = actEndTimeStr;
	}
	
	public Page<ActivityRegisterVo> getRegisterList() {
		return registerList;
	}
	public void setRegisterList(Page<ActivityRegisterVo> registerList) {
		this.registerList = registerList;
	}
	
	
}
