package com.aylson.dc.mem.vo;

import com.aylson.dc.mem.po.WithdrawalsApply;

public class WithdrawalsApplyVo extends WithdrawalsApply {

	private static final long serialVersionUID = 4570780329290152276L;
	
	private String applier;                    //申请人账号名
	private String applierRealName;            //申请人姓名
	private String applierPhone;               //申请人电话
	private String wxOpenId;                   //微信id
	
	
	public String getApplier() {
		return applier;
	}
	public void setApplier(String applier) {
		this.applier = applier;
	}
	
	public String getApplierRealName() {
		return applierRealName;
	}
	public void setApplierRealName(String applierRealName) {
		this.applierRealName = applierRealName;
	}
	
	public String getApplierPhone() {
		return applierPhone;
	}
	public void setApplierPhone(String applierPhone) {
		this.applierPhone = applierPhone;
	}
	
	public String getWxOpenId() {
		return wxOpenId;
	}
	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}

	
}
