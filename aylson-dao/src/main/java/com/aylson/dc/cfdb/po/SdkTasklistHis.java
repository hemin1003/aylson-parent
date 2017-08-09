package com.aylson.dc.cfdb.po;

import java.io.Serializable;

public class SdkTasklistHis implements Serializable{

	private static final long serialVersionUID = 903678502639630912L;
	
	private String hashid;	//唯一标识ID
	private String appid;	//开发者应用ID
	private String adid;		//广告ID
	private String adname;	//广告名称
	private String userid;	//开发者设置的用户ID
	private String mac;		//mac地址
	private String deviceid;	//设备唯一标识(IMEI)
	private String source;	//渠道来源
	private String point;	//奖励积分
	private String price;	//奖励金额
	private String time;		//时间戳
	private String timeStr;	//转译后时间戳
	private String appsecret;	//开发者设置的密钥
	private String checksum;		//签名结果	
	private String createDate;	//创建时间
	private String activeNum;	//签到标识
	private String result;		//md5校验结果
	private String resultSum;	//MD5校验签名值
	
	public String getActiveNum() {
		return activeNum;
	}
	public void setActiveNum(String activeNum) {
		this.activeNum = activeNum;
	}
	public String getResultSum() {
		return resultSum;
	}
	public void setResultSum(String resultSum) {
		this.resultSum = resultSum;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getHashid() {
		return hashid;
	}
	public void setHashid(String hashid) {
		this.hashid = hashid;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getAdid() {
		return adid;
	}
	public void setAdid(String adid) {
		this.adid = adid;
	}
	public String getAdname() {
		return adname;
	}
	public void setAdname(String adname) {
		this.adname = adname;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTimeStr() {
		return timeStr;
	}
	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}
	public String getAppsecret() {
		return appsecret;
	}
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	public String getChecksum() {
		return checksum;
	}
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
}
