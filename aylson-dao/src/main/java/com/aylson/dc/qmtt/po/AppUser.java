package com.aylson.dc.qmtt.po;

import java.io.Serializable;

public class AppUser implements Serializable {
	
	private static final long serialVersionUID = -771302935941259817L;
	
	private String phoneNum;		//手机号码
	private String userPwd;		//密码
	private String gold;			//金币余额
	private String balance;		//零钱余额
	private String userName;		//姓名
	private String address;		//住址
	private String wechat;		//微信号
	private String qq;			//QQ号
	private String email;		//邮箱地址
	private String registerDate;	//注册时间
	private String lastLoginDate;	//最后一次登录时间
	private String updateDate;
	
	private Integer blackList;	//是否黑名单用户
	private Integer shareCount;	//已分享次数
	private Integer firstShare;	//首次分享标识
	private Integer firstInvite;	//首次邀请成功标识
	private Integer firstRead;	//首次阅读标识
	private Integer students;	//徒弟数
	private Integer dailyCheckIn;	//今日签到标识
	
	
	public Integer getBlackList() {
		return blackList;
	}
	public void setBlackList(Integer blackList) {
		this.blackList = blackList;
	}
	public Integer getShareCount() {
		return shareCount;
	}
	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}
	public Integer getFirstShare() {
		return firstShare;
	}
	public void setFirstShare(Integer firstShare) {
		this.firstShare = firstShare;
	}
	public Integer getFirstInvite() {
		return firstInvite;
	}
	public void setFirstInvite(Integer firstInvite) {
		this.firstInvite = firstInvite;
	}
	public Integer getFirstRead() {
		return firstRead;
	}
	public void setFirstRead(Integer firstRead) {
		this.firstRead = firstRead;
	}
	public Integer getStudents() {
		return students;
	}
	public void setStudents(Integer students) {
		this.students = students;
	}
	public Integer getDailyCheckIn() {
		return dailyCheckIn;
	}
	public void setDailyCheckIn(Integer dailyCheckIn) {
		this.dailyCheckIn = dailyCheckIn;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getGold() {
		return gold;
	}
	public void setGold(String gold) {
		this.gold = gold;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	public String getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
}
