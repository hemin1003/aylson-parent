package com.aylson.dc.mem.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class GiftExchangeRecords implements Serializable {
	
	private static final long serialVersionUID = 281840304120585399L;
	
	private Integer id;           		          //主键
	private String exchangeCode;           		  //兑换码：6位,唯一
	private String exchangePhone;           	  //兑换人手机号
	private String exchanger;           		  //兑换人
	private Integer exchangerId;           		  //兑换人id
	private Integer giftId;           		      //礼品配置id
	private String giftName;           		      //礼品名称
	private Date exchangeTime;                    //兑换时间
	private Integer integral;                     //兑换积分
	private Date validTime;                       //有效领取时间
	private Integer matchObj;                     //适用兑换对象:0全部 1 设计师 2工长 3 开拓者联盟
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getExchangeCode() {
		return exchangeCode;
	}
	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}
	
	public String getExchangePhone() {
		return exchangePhone;
	}
	public void setExchangePhone(String exchangePhone) {
		this.exchangePhone = exchangePhone;
	}
	
	public String getExchanger() {
		return exchanger;
	}
	public void setExchanger(String exchanger) {
		this.exchanger = exchanger;
	}
	
	public Integer getExchangerId() {
		return exchangerId;
	}
	public void setExchangerId(Integer exchangerId) {
		this.exchangerId = exchangerId;
	}
	
	public Integer getGiftId() {
		return giftId;
	}
	public void setGiftId(Integer giftId) {
		this.giftId = giftId;
	}
	
	public String getGiftName() {
		return giftName;
	}
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getExchangeTime() {
		return exchangeTime;
	}
	public void setExchangeTime(Date exchangeTime) {
		this.exchangeTime = exchangeTime;
	}
	
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getValidTime() {
		return validTime;
	}
	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}
	
	public Integer getMatchObj() {
		return matchObj;
	}
	public void setMatchObj(Integer matchObj) {
		this.matchObj = matchObj;
	}
	
	
}
