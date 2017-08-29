package com.aylson.dc.qmtt.search;

import com.aylson.core.frame.search.BaseSearch;

public class QmttWithdrawHisSearch  extends BaseSearch{

	private static final long serialVersionUID = 7258302646362351840L;
	
	//匹配查询
	private Integer withdrawType;	//提现类型
	private Integer statusType;		//提现状态标识
	
	//模糊查询
	private String nameLike;		//姓名
	private String accountLike;	//账户名
	private String phoneNumLike;	//手机号码
	
	public Integer getWithdrawType() {
		return withdrawType;
	}
	public void setWithdrawType(Integer withdrawType) {
		this.withdrawType = withdrawType;
	}
	public String getNameLike() {
		return nameLike;
	}
	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}
	public String getAccountLike() {
		return accountLike;
	}
	public void setAccountLike(String accountLike) {
		this.accountLike = accountLike;
	}
	public Integer getStatusType() {
		return statusType;
	}
	public void setStatusType(Integer statusType) {
		this.statusType = statusType;
	}
	public String getPhoneNumLike() {
		return phoneNumLike;
	}
	public void setPhoneNumLike(String phoneNumLike) {
		this.phoneNumLike = phoneNumLike;
	}
}
