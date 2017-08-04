package com.aylson.dc.cfdb.po;

import java.io.Serializable;

/**
 * 短信历史记录
 * @author Minbo
 *
 */
public class UserSms implements Serializable{
	
	private static final long serialVersionUID = 5213205893092406637L;
	
	private String id;			//主键
	private String phoneId;		//手机唯一标识码
	private String phoneNum;		//手机号码
	private String msgCode;		//验证码
	private String createDate;	//创建时间
	private String createdBy;	//创建人
	private String updateDate;	//更新时间
	private String updatedBy;	//更新人
	
	public String getPhoneId() {
		return phoneId;
	}
	public void setPhoneId(String phoneId) {
		this.phoneId = phoneId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getMsgCode() {
		return msgCode;
	}
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
}
