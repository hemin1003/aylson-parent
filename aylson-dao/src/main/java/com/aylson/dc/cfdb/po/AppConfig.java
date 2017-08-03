package com.aylson.dc.cfdb.po;

import java.io.Serializable;

public class AppConfig implements Serializable{

	private static final long serialVersionUID = -3614595731018009677L;
	
	private String id;			//主键
	private String appFaqUrl;	//常见问题页面url
	private String appLearnUrl;	//红包攻略图url
	private String createDate;	//创建时间
	private String updateDate;	//更新时间
	private String createdBy;	//创建人
	private String updatedBy;	//更新人
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAppFaqUrl() {
		return appFaqUrl;
	}
	public void setAppFaqUrl(String appFaqUrl) {
		this.appFaqUrl = appFaqUrl;
	}
	public String getAppLearnUrl() {
		return appLearnUrl;
	}
	public void setAppLearnUrl(String appLearnUrl) {
		this.appLearnUrl = appLearnUrl;
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
