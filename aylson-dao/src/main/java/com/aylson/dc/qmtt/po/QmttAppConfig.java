package com.aylson.dc.qmtt.po;

import java.io.Serializable;

public class QmttAppConfig implements Serializable{
	
	private static final long serialVersionUID = -557043907464264969L;

	private String id;			//主键
	
	private Integer goldLimit;			//每日金币领取上限
	private Integer goldAuto;			//触发自动金币转换零钱的金币阈值
	private String goldRange;			//阅读金币随机范围
	private Integer firstReadGold;		//首次有效阅读奖励金币
	private Integer firstShareGold;		//首次分享奖励金币
	private Integer firstInviteGold;		//首次邀请奖励金币
	private String androidUrl;			//安卓分享下载链接Url
	private String iphoneUrl;			//苹果分享下载链接Url
	
	private String createDate;	//创建时间
	private String createdBy;	//创建人
	private String updateDate;	//更新时间
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
	public Integer getGoldLimit() {
		return goldLimit;
	}
	public void setGoldLimit(Integer goldLimit) {
		this.goldLimit = goldLimit;
	}
	public Integer getGoldAuto() {
		return goldAuto;
	}
	public void setGoldAuto(Integer goldAuto) {
		this.goldAuto = goldAuto;
	}
	public String getGoldRange() {
		return goldRange;
	}
	public void setGoldRange(String goldRange) {
		this.goldRange = goldRange;
	}
	public Integer getFirstReadGold() {
		return firstReadGold;
	}
	public void setFirstReadGold(Integer firstReadGold) {
		this.firstReadGold = firstReadGold;
	}
	public Integer getFirstShareGold() {
		return firstShareGold;
	}
	public void setFirstShareGold(Integer firstShareGold) {
		this.firstShareGold = firstShareGold;
	}
	public Integer getFirstInviteGold() {
		return firstInviteGold;
	}
	public void setFirstInviteGold(Integer firstInviteGold) {
		this.firstInviteGold = firstInviteGold;
	}
	public String getAndroidUrl() {
		return androidUrl;
	}
	public void setAndroidUrl(String androidUrl) {
		this.androidUrl = androidUrl;
	}
	public String getIphoneUrl() {
		return iphoneUrl;
	}
	public void setIphoneUrl(String iphoneUrl) {
		this.iphoneUrl = iphoneUrl;
	}
	
}
