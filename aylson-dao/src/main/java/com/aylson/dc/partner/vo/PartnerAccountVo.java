package com.aylson.dc.partner.vo;

import com.aylson.dc.partner.po.PartnerAccount;

public class PartnerAccountVo extends PartnerAccount {

	
	private static final long serialVersionUID = 44654137451978902L;
	
	
	private String confirmPwd;         //确认密码
	private String newPwd;             //新密码
	private String validCode;          //手机验证码
	private Float walletGet;           //已得当前经销商分红
	private Float walletHad;           //已转当前经销商分红


	public String getConfirmPwd() {
		return confirmPwd;
	}
	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}
	
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	
	public String getValidCode() {
		return validCode;
	}
	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}
	
	public Float getWalletHad() {
		if(this.walletHad == null)return 0.0f;
		return walletHad;
	}
	public void setWalletHad(Float walletHad) {
		this.walletHad = walletHad;
	}
	
	public Float getWalletGet() {
		return walletGet;
	}
	public void setWalletGet(Float walletGet) {
		this.walletGet = walletGet;
	}
	
	
}
