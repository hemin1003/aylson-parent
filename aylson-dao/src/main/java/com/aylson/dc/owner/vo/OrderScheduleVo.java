package com.aylson.dc.owner.vo;

import com.aylson.dc.owner.po.OrderSchedule;

public class OrderScheduleVo extends OrderSchedule {

	private static final long serialVersionUID = 2462711285991479537L;
	
	private String createTimeStr;                  //创建时间

	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
	
}
