package com.aylson.dc.partner.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 合伙人账单明细
 * @author Administrator
 *
 */
public class PartnerWalletBill implements Serializable{

	private static final long serialVersionUID = 8146725854220540077L;

	private Integer id;           		  //主键
	private Integer accountId;            //账号id
	private Integer type;           	  //进出类别
	private Float wallet;           	  //进出钱包值
	private Date createTime;           	  //进出时间
	private String description;           //描述说明
	private Integer sourceType;           //来源类型
	private Integer sourceId;             //来源id
	private String attachUrl;             //附件地址
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
	
	public Float getWallet() {
		return wallet;
	}
	public void setWallet(Float wallet) {
		this.wallet = wallet;
	}

	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	
	public String getAttachUrl() {
		return attachUrl;
	}
	public void setAttachUrl(String attachUrl) {
		this.attachUrl = attachUrl;
	}
	
	public Integer getAgentUserId() {
		return agentUserId;
	}
	public void setAgentUserId(Integer agentUserId) {
		this.agentUserId = agentUserId;
	}
	
	
}
