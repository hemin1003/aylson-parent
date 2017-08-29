package com.aylson.dc.qmtt.po;

import java.io.Serializable;

public class InitConfig implements Serializable{
	
	private static final long serialVersionUID = -4066660374449732089L;
	
	private String id;			//主键
	private String faqUrl;			//常见问题页面url
	private String inviteUrl;		//邀请规则说明页面url
	private String registerInfoUrl;	//注册用户协议页面url
	private String duration;			//阅读文章控制时长
	private Integer frequency;		//阅读文章拖动次数
	private String createDate;	//创建时间
	private String createdBy;	//创建人
	private String updateDate;	//更新时间
	private String updatedBy;	//更新人
	
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public Integer getFrequency() {
		return frequency;
	}
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getFaqUrl() {
		return faqUrl;
	}
	public void setFaqUrl(String faqUrl) {
		this.faqUrl = faqUrl;
	}
	public String getInviteUrl() {
		return inviteUrl;
	}
	public void setInviteUrl(String inviteUrl) {
		this.inviteUrl = inviteUrl;
	}
	public String getRegisterInfoUrl() {
		return registerInfoUrl;
	}
	public void setRegisterInfoUrl(String registerInfoUrl) {
		this.registerInfoUrl = registerInfoUrl;
	}
	
}
