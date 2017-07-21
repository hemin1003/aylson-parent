package com.aylson.dc.mem.vo;

import com.aylson.dc.mem.po.RechargeRecords;

public class RechargeRecordsVo extends RechargeRecords {

	private static final long serialVersionUID = -2522444548438980108L;
	
	private Integer pingxxAmount;  //ping++支付金额值
	private String subject;        //商品的标题
	private String body;           //商品的描述信息
	private String sessionId;      //缓存id
	private String openId;         //微信openId
	private String channel;        //支付渠道
	

	public Integer getPingxxAmount() {
		return pingxxAmount;
	}
	public void setPingxxAmount(Integer pingxxAmount) {
		this.pingxxAmount = pingxxAmount;
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	

}
