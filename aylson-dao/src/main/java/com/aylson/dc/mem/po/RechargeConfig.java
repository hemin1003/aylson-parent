package com.aylson.dc.mem.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class RechargeConfig implements Serializable {
	
	private static final long serialVersionUID = 3320544777592910484L;
	
	private Integer id;           		          //主键
	private String title;           		      //标题
	private Float amount;           		      //支付金额
	private Integer buyGold;           		      //购买金币
	private Integer sendGold;           		  //赠送金币
	private Integer status;           		      //状态:1：新建；2：上架 3：下架
	private Date publishTime;                     //发布时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	
	public Integer getBuyGold() {
		return buyGold;
	}
	public void setBuyGold(Integer buyGold) {
		this.buyGold = buyGold;
	}
	
	public Integer getSendGold() {
		return sendGold;
	}
	public void setSendGold(Integer sendGold) {
		this.sendGold = sendGold;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	
	
}
