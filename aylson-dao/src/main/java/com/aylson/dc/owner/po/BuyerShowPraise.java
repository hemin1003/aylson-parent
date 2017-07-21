package com.aylson.dc.owner.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class BuyerShowPraise implements Serializable{

	private static final long serialVersionUID = -771236097513431182L;
	
	
	private Integer id;                      //主键
	private Integer buyershowId;             //买家秀id
	private Date createTime;                 //创建时间
	private String operater;                 //操作人
	private Date operateTime;                //操作时间
	private Boolean isPraise;                //是否点赞，默认：是
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getBuyershowId() {
		return buyershowId;
	}
	public void setBuyershowId(Integer buyershowId) {
		this.buyershowId = buyershowId;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getOperater() {
		return operater;
	}
	public void setOperater(String operater) {
		this.operater = operater;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	
	public Boolean getIsPraise() {
		return isPraise;
	}
	public void setIsPraise(Boolean isPraise) {
		this.isPraise = isPraise;
	}
	
	
}
