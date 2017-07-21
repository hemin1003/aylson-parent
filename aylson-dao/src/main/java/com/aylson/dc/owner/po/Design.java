package com.aylson.dc.owner.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateSerializer;
import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Design implements Serializable{

	private static final long serialVersionUID = -1431795299752288039L;
	
	
	private Integer id;                      //主键
	private Integer appointId;               //预约id
	private String clientName;               //客户姓名
	private String clientAddress;            //客户地址
	private String clientPhone;              //客户电话
	private String orderNo;                  //订单号
	private Date orderTime;                  //下单时间
	private Integer designType;              //设计类型：门、窗、阳光房
	private String remark;                   //备注
	private Date createTime;                 //创建时间
	private String creater;                  //创建人
	private Integer state;                   //状态
	private String drawUrl;                  //大样图
	private String drawOpition;              //大样图确认意见
	private String billCode;                 //流水单号
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getAppointId() {
		return appointId;
	}
	public void setAppointId(Integer appointId) {
		this.appointId = appointId;
	}
	
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	public String getClientAddress() {
		return clientAddress;
	}
	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}
	
	public String getClientPhone() {
		return clientPhone;
	}
	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	
	public Integer getDesignType() {
		return designType;
	}
	public void setDesignType(Integer designType) {
		this.designType = designType;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	public String getDrawUrl() {
		return drawUrl;
	}
	public void setDrawUrl(String drawUrl) {
		this.drawUrl = drawUrl;
	}
	
	public String getDrawOpition() {
		return drawOpition;
	}
	public void setDrawOpition(String drawOpition) {
		this.drawOpition = drawOpition;
	}
	
	public String getBillCode() {
		return billCode;
	}
	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
	
	
}
