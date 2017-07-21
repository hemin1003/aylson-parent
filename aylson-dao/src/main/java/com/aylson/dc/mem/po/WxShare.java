package com.aylson.dc.mem.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class WxShare implements Serializable {
	
	
	private static final long serialVersionUID = 4024988759040309115L;
	
	private Integer id;           		      //主键
	private String inviterWxOpenId;           //分享人openId
	private String inviterWxUnionId;          //分享人unionId
	private String wxOpenId;           		  //邀请人openId
	private String wxUnionId;           	  //微信unionId
	private String wxHeadPhoto;           	  //微信头像地址
	private Integer sourceId;                 //来源id
	private Integer type;           		  //分享关系类型
	private String remark;           		  //备注说明
	private Date createTime;           		  //创建时间
	private String channel;           		  //渠道
	private String wxNickName;           	  //昵称
	private Integer assistValue;           	  //助力值
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getInviterWxOpenId() {
		return inviterWxOpenId;
	}
	public void setInviterWxOpenId(String inviterWxOpenId) {
		this.inviterWxOpenId = inviterWxOpenId;
	}
	
	public String getInviterWxUnionId() {
		return inviterWxUnionId;
	}
	public void setInviterWxUnionId(String inviterWxUnionId) {
		this.inviterWxUnionId = inviterWxUnionId;
	}
	
	public String getWxOpenId() {
		return wxOpenId;
	}
	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}
	
	public String getWxUnionId() {
		return wxUnionId;
	}
	public void setWxUnionId(String wxUnionId) {
		this.wxUnionId = wxUnionId;
	}
	
	public String getWxHeadPhoto() {
		return wxHeadPhoto;
	}
	public void setWxHeadPhoto(String wxHeadPhoto) {
		this.wxHeadPhoto = wxHeadPhoto;
	}
	
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public String getWxNickName() {
		return wxNickName;
	}
	public void setWxNickName(String wxNickName) {
		this.wxNickName = wxNickName;
	}
	
	public Integer getAssistValue() {
		return assistValue;
	}
	public void setAssistValue(Integer assistValue) {
		this.assistValue = assistValue;
	}
	
	
}
