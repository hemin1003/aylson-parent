package com.aylson.dc.mem.search;

import com.aylson.core.frame.search.BaseSearch;

public class WithdrawalsApplySearch extends BaseSearch {

	private static final long serialVersionUID = -3484473691806293941L;

	//匹配查询
	private Integer id;           		              //主键
	private Integer applierId;           		      //申请人id
	private Integer status;           		          //状态：1处理中2已转账
	private Integer dealerId;           		      //处理人id
	//模糊查询
	private String bankNumLike;           		      //银行卡号
	private String applierLike;           		      //银行卡号
	private String applierPhoneLike;           		  //银行卡号
	private String[] idsArray;                        //主键集合
	
	
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
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getDealerId() {
		return dealerId;
	}
	public void setDealerId(Integer dealerId) {
		this.dealerId = dealerId;
	}
	
	public String getBankNumLike() {
		return bankNumLike;
	}
	public void setBankNumLike(String bankNumLike) {
		this.bankNumLike = bankNumLike;
	}
	
	public String getApplierLike() {
		return applierLike;
	}
	public void setApplierLike(String applierLike) {
		this.applierLike = applierLike;
	}
	
	public String getApplierPhoneLike() {
		return applierPhoneLike;
	}
	public void setApplierPhoneLike(String applierPhoneLike) {
		this.applierPhoneLike = applierPhoneLike;
	}
	
	public String[] getIdsArray() {
		return idsArray;
	}
	public void setIdsArray(String[] idsArray) {
		this.idsArray = idsArray;
	}
	
	
	
	
}
