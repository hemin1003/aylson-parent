package com.aylson.dc.mem.vo;

import com.aylson.dc.mem.po.MemAccount;

public class MemAccountVo extends MemAccount {

	private static final long serialVersionUID = 8897369002286950066L;
	
	private String confirmPwd;         //确认密码
	private String validCode;          //手机验证码
	private String referralPhone;      //推荐人手机号
	private String birthdayStr;        //出生日期 
	private String appId;              //微信appId
	private Integer stillCount;        //还可以派送的数量
	private String channel;            //分享渠道
	private Float hadWithdrawlasAmout; //已提现金额

	public String getConfirmPwd() {
		return confirmPwd;
	}
	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}
	
	public String getValidCode() {
		return validCode;
	}
	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}
	
	public String getReferralPhone() {
		return referralPhone;
	}
	public void setReferralPhone(String referralPhone) {
		this.referralPhone = referralPhone;
	}
	
	public String getBirthdayStr() {
		return birthdayStr;
	}
	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	public Integer getStillCount() {
		return stillCount;
	}
	public void setStillCount(Integer stillCount) {
		this.stillCount = stillCount;
	}
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public Float getHadWithdrawlasAmout() {
		return hadWithdrawlasAmout;
	}
	public void setHadWithdrawlasAmout(Float hadWithdrawlasAmout) {
		this.hadWithdrawlasAmout = hadWithdrawlasAmout;
	}
	
	
}
