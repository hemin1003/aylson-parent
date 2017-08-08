package com.aylson.dc.cfdb.po;

import java.io.Serializable;

public class SdkChannelConfig implements Serializable{

	private static final long serialVersionUID = 7768461280745025400L;
	
	private String id;			//主键
	private String channelFlag;	//SDK广告渠道标识
	private String channelName;	//SDK广告渠道名称
	private String cRate;		//换算比例
	private String sRate;		//分成比例
	private Integer orderNo;		//排序编号
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
	public String getChannelFlag() {
		return channelFlag;
	}
	public void setChannelFlag(String channelFlag) {
		this.channelFlag = channelFlag;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getcRate() {
		return cRate;
	}
	public void setcRate(String cRate) {
		this.cRate = cRate;
	}
	public String getsRate() {
		return sRate;
	}
	public void setsRate(String sRate) {
		this.sRate = sRate;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
}
