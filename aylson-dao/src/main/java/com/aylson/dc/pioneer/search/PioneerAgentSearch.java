package com.aylson.dc.pioneer.search;

import com.aylson.core.frame.search.BaseSearch;

public class PioneerAgentSearch extends BaseSearch {

	private static final long serialVersionUID = -87350005451865853L;
	
	//匹配查询
	private String statusMerge;                  //s1:审核中 s2:洽谈中 s3:已加盟
	private Integer submitterId;                 //提交人id
	private Integer status;                      //状态
	//模糊查询
	private String agentNameLike;                //代理商名称
	private String contactPhoneLike;             //代理商电话
	private String addressLike;                  //地址
	private String shopNameLike;                 //商店名称
	private String submitterLike;                //邀请人名称
	private String submitterPhoneLike;           //邀请人电话

	public String getStatusMerge() {
		return statusMerge;
	}
	public void setStatusMerge(String statusMerge) {
		this.statusMerge = statusMerge;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getSubmitterId() {
		return submitterId;
	}
	public void setSubmitterId(Integer submitterId) {
		this.submitterId = submitterId;
	}
	
	public String getAgentNameLike() {
		return agentNameLike;
	}
	public void setAgentNameLike(String agentNameLike) {
		this.agentNameLike = agentNameLike;
	}
	
	public String getContactPhoneLike() {
		return contactPhoneLike;
	}
	public void setContactPhoneLike(String contactPhoneLike) {
		this.contactPhoneLike = contactPhoneLike;
	}
	
	public String getAddressLike() {
		return addressLike;
	}
	public void setAddressLike(String addressLike) {
		this.addressLike = addressLike;
	}
	
	public String getShopNameLike() {
		return shopNameLike;
	}
	public void setShopNameLike(String shopNameLike) {
		this.shopNameLike = shopNameLike;
	}
	
	public String getSubmitterLike() {
		return submitterLike;
	}
	public void setSubmitterLike(String submitterLike) {
		this.submitterLike = submitterLike;
	}
	
	public String getSubmitterPhoneLike() {
		return submitterPhoneLike;
	}
	public void setSubmitterPhoneLike(String submitterPhoneLike) {
		this.submitterPhoneLike = submitterPhoneLike;
	}
	
	
}
