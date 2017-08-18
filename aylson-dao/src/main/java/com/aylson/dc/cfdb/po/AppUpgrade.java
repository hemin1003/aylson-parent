package com.aylson.dc.cfdb.po;

import java.io.Serializable;

public class AppUpgrade implements Serializable{

	private static final long serialVersionUID = -1352113044059692812L;
	
	private String id;			//主键
	private String version;		//版本号
	private String vdesc;		//版本描述
	private String apkUrl;		//包下载地址
	private Integer status;		//版本状态
	private String fields;		//渠道动态组合字段，0=打开;1=关闭
	private String platform;		//渠道标识
	private String createDate;	//创建时间
	private String updateDate;	//更新时间
	private String createdBy;	//创建人
	private String updatedBy;	//更新人
	
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	public String getApkUrl() {
		return apkUrl;
	}
	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getVdesc() {
		return vdesc;
	}
	public void setVdesc(String vdesc) {
		this.vdesc = vdesc;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
