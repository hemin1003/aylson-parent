package com.aylson.dc.sys.vo;

import com.aylson.dc.sys.po.CouponRule;

public class CouponRuleVo extends CouponRule {

	private static final long serialVersionUID = 4513156465465465521L;
	
	private String startTimeStr;             		
	private String endTimeStr;
	
	public String getStartTimeStr() {
		return startTimeStr;
	}
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
}
