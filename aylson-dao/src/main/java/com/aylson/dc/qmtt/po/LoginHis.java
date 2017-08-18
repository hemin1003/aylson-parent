package com.aylson.dc.qmtt.po;

import java.io.Serializable;

public class LoginHis implements Serializable{

	private static final long serialVersionUID = -5349993237162943212L;
	
	private String id;			//主键
	private String phoneNum;		//手机号码
	private String deviceName;	//设备名
	private String imei;			//IMEI码
	private String ip;			//IP地址
	private String mac;			//Mac地址
	private String location;		//地理位置
	private String wifi;			//Wifi连接名
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
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getWifi() {
		return wifi;
	}
	public void setWifi(String wifi) {
		this.wifi = wifi;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

}
