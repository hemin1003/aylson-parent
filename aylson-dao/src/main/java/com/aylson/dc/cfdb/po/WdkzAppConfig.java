package com.aylson.dc.cfdb.po;

import java.io.Serializable;

public class WdkzAppConfig implements Serializable{

	private static final long serialVersionUID = 1090676661505472298L;
	
	private String id;			//主键
	private String version;		//版本号
	private String versionDesc;	//版本说明
	private Integer status;		//记录状态
	private Integer isEnabled;	//是否启用
	
	private Integer displayFlag;	//开屏标识
	private Integer bannerFlag;	//横幅标识
	private Integer videoFlag;	//插频标识
	private Integer awardFlag;	//抽奖标识
	private Integer nosenseFlag;	//无感标识
	
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getVersionDesc() {
		return versionDesc;
	}
	public void setVersionDesc(String versionDesc) {
		this.versionDesc = versionDesc;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(Integer isEnabled) {
		this.isEnabled = isEnabled;
	}
	public Integer getDisplayFlag() {
		return displayFlag;
	}
	public void setDisplayFlag(Integer displayFlag) {
		this.displayFlag = displayFlag;
	}
	public Integer getBannerFlag() {
		return bannerFlag;
	}
	public void setBannerFlag(Integer bannerFlag) {
		this.bannerFlag = bannerFlag;
	}
	public Integer getVideoFlag() {
		return videoFlag;
	}
	public void setVideoFlag(Integer videoFlag) {
		this.videoFlag = videoFlag;
	}
	public Integer getAwardFlag() {
		return awardFlag;
	}
	public void setAwardFlag(Integer awardFlag) {
		this.awardFlag = awardFlag;
	}
	public Integer getNosenseFlag() {
		return nosenseFlag;
	}
	public void setNosenseFlag(Integer nosenseFlag) {
		this.nosenseFlag = nosenseFlag;
	}
}
