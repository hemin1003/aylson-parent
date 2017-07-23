package com.aylson.dc.cfdb.search;

import com.aylson.core.frame.search.BaseSearch;

public class WithdrawHisSearch  extends BaseSearch{

	private static final long serialVersionUID = 7258302646362351840L;
	
	//匹配查询
	private Integer withdrawType;	//提现类型
	private String name;		//姓名
	private String account;		//账户名
	private Integer statusType;	//提现状态标识
	
	public Integer getWithdrawType() {
		return withdrawType;
	}
	public void setWithdrawType(Integer withdrawType) {
		this.withdrawType = withdrawType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getStatusType() {
		return statusType;
	}
	public void setStatusType(Integer statusType) {
		this.statusType = statusType;
	}
}
