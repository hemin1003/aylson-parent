package com.aylson.dc.mem.po;

import java.io.Serializable;

public class GiftSendDetail implements Serializable {
	
	private static final long serialVersionUID = 3727436652118905854L;
	
	private Integer id;                             //主键
	private Integer sendId;                         //礼品送货主键
	private Integer giftId;                         //礼品主键
	private String giftName;                        //礼品名称
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getSendId() {
		return sendId;
	}
	public void setSendId(Integer sendId) {
		this.sendId = sendId;
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
	
	
}
