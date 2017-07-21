package com.aylson.dc.mem.po;

import java.io.Serializable;

public class Bank implements Serializable {
	
	private static final long serialVersionUID = -4674648129154173270L;
	
	
	private Integer id;                  //主键
	private Integer memberId;            //会员id
	private String bankholder;           //持卡人姓名
	private String bankNum;              //银行卡号
	private String bankName;             //银行名字
	private Integer dicId;               //数据字典id
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
	public String getBankholder() {
		return bankholder;
	}
	public void setBankholder(String bankholder) {
		this.bankholder = bankholder;
	}
	
	public String getBankNum() {
		return bankNum;
	}
	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public Integer getDicId() {
		return dicId;
	}
	public void setDicId(Integer dicId) {
		this.dicId = dicId;
	}
	
	
}
