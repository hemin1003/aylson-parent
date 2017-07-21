package com.aylson.dc.sys.vo;

import com.aylson.dc.sys.po.CouponActivity;

public class CouponActivityVo extends CouponActivity {

	private static final long serialVersionUID = 454577187134709521L;
	
	private String startTimeStr;    //活动开始时间           		
	private String endTimeStr;      //活动结束时间
	private String curAuditOpinion;	//当前审核意见
	
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
	public String getCurAuditOpinion() {
		return curAuditOpinion;
	}
	public void setCurAuditOpinion(String curAuditOpinion) {
		this.curAuditOpinion = curAuditOpinion;
	}
	
}
