package com.aylson.dc.owner.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateSerializer;
import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class OrderSchedule implements Serializable{

	private static final long serialVersionUID = -1431795299752288039L;
	
	private Integer id;                          //主键
	private Integer orderId;                     //订单id
	private Date createTime;                     //创建时间
	private String remark;                       //备注
	private Integer state;                       //状态：1确认订单 2生产完成 3 产品入库中 4产品出库 5已经发货，预计5天后到达
	
	
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
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	
}
