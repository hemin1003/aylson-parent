package com.aylson.dc.owner.search;

import com.aylson.core.frame.search.BaseSearch;

public class OrderScheduleSearch extends BaseSearch {

	private static final long serialVersionUID = -2015186156036245714L;
	
	//匹配查询
	private Integer id;                      //主键
	private Integer orderId;                 //订单id
	//模糊查询


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	
}
