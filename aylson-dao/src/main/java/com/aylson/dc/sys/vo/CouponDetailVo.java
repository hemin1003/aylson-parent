package com.aylson.dc.sys.vo;

import java.util.List;

import com.aylson.dc.sys.po.CouponDetail;
import com.aylson.dc.sys.po.CouponRule;

public class CouponDetailVo extends CouponDetail {

	private static final long serialVersionUID = 457446546546709521L;
	
	private String startTimeStr;           //有效开始时间        		
	private String endTimeStr;             //有效结束时间
	List<CouponRule> couponRuleList;       //规则列表
	
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
	
	public List<CouponRule> getCouponRuleList() {
		return couponRuleList;
	}
	public void setCouponRuleList(List<CouponRule> couponRuleList) {
		this.couponRuleList = couponRuleList;
	}
	
	
}
