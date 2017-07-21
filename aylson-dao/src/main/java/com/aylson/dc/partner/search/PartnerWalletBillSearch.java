package com.aylson.dc.partner.search;

import com.aylson.core.frame.search.BaseSearch;

public class PartnerWalletBillSearch extends BaseSearch {
	
private static final long serialVersionUID = 3264153299345637991L;
	
	//匹配查询
	private Integer id;           		  //主键
	private Integer accountId;            //账号id
	private Integer type;           	  //进出类别
	private Integer sourceType;           //来源类型
	private Integer sourceId;             //来源id
	private Integer agentUserId;          //代理商用户id，订单分红辅助订单
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public Integer getSourceType() {
		return sourceType;
	}
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}
	
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	
	public Integer getAgentUserId() {
		return agentUserId;
	}
	public void setAgentUserId(Integer agentUserId) {
		this.agentUserId = agentUserId;
	}
	
	
	
	
}
