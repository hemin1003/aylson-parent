package com.aylson.dc.mem.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class WithdrawalsApply implements Serializable {
	
	private static final long serialVersionUID = -7197681625173383069L;
	
	private Integer id;           		              //主键
	private Integer applierId;           		      //申请人id
	private Float amount;           		          //提现金额
	private String bankNum;           		          //银行卡号
	private Integer status;           		          //状态：1处理中2已转账
	private Date applyTime;                           //申请时间
	private String dealer;           		          //处理人
	private Integer dealerId;           		      //处理人id
	private Date dealTime;           		          //处理时间
	private String remark;           		          //备注
	private String transferProof;           		  //上传凭证
	private String bankholder;                        //持卡人姓名
	private String bankName;                          //所属银行
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getApplierId() {
		return applierId;
	}
	public void setApplierId(Integer applierId) {
		this.applierId = applierId;
	}
	
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	
	public String getBankNum() {
		return bankNum;
	}
	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	public String getDealer() {
		return dealer;
	}
	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
	
	public Integer getDealerId() {
		return dealerId;
	}
	public void setDealerId(Integer dealerId) {
		this.dealerId = dealerId;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getDealTime() {
		return dealTime;
	}
	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getTransferProof() {
		return transferProof;
	}
	public void setTransferProof(String transferProof) {
		this.transferProof = transferProof;
	}
	
	public String getBankholder() {
		return bankholder;
	}
	public void setBankholder(String bankholder) {
		this.bankholder = bankholder;
	}
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	
}
