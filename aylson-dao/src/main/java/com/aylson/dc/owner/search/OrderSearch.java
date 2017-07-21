package com.aylson.dc.owner.search;

import com.aylson.core.frame.search.BaseSearch;

public class OrderSearch extends BaseSearch {

	
	private static final long serialVersionUID = -1888456577798005640L;
	
	//匹配查询
	private Integer id;                       //主键
	private Integer appointId;                //预约id
	private String createTimeStr;             //下单时间
	private String updateTimeStr;             //更新时间
	private String mobilePhone;               //下单手机
	private String orderNo;                   //订单号
	private String phoneOrNo;                 //订单号或手机号
	private Boolean isSettle;                 //是否结算
	private Integer partnerId;                //合伙人id，如果为null不是合伙人介绍的
	private Integer designType;               //设计类型
	private Integer tab;                      //1 未发货 2已返货  3已收货
	private Integer byAgentUserId;            //所属代理商用户id
	private Boolean isWarn;                   //是否预警
	private Integer flowStateType;            //flowState集合查询：
	private Integer flowState;                //流程状态：0 经销商未确认 1经销商 已确认 2 总部未确认 
	//模糊查询
	private String orderNoLike;               //订单号
	private String appointNoLike;             //预约单号
	private String mobilePhoneLike;           //下单手机
	private String nameLike;                  //姓名
	private String designNoLike;              //设计单号
	private String phoneOrNameLike;           //客户名称或者电话


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getAppointId() {
		return appointId;
	}
	public void setAppointId(Integer appointId) {
		this.appointId = appointId;
	}
	
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
	public String getUpdateTimeStr() {
		return updateTimeStr;
	}
	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}
	
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getPhoneOrNo() {
		return phoneOrNo;
	}
	public void setPhoneOrNo(String phoneOrNo) {
		this.phoneOrNo = phoneOrNo;
	}
	
	public Integer getDesignType() {
		return designType;
	}
	public void setDesignType(Integer designType) {
		this.designType = designType;
	}
	
	public String getOrderNoLike() {
		return orderNoLike;
	}
	public void setOrderNoLike(String orderNoLike) {
		this.orderNoLike = orderNoLike;
	}
	
	public String getAppointNoLike() {
		return appointNoLike;
	}
	public void setAppointNoLike(String appointNoLike) {
		this.appointNoLike = appointNoLike;
	}
	
	public String getMobilePhoneLike() {
		return mobilePhoneLike;
	}
	public void setMobilePhoneLike(String mobilePhoneLike) {
		this.mobilePhoneLike = mobilePhoneLike;
	}
	
	public String getNameLike() {
		return nameLike;
	}
	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}
	
	public String getDesignNoLike() {
		return designNoLike;
	}
	public void setDesignNoLike(String designNoLike) {
		this.designNoLike = designNoLike;
	}
	
	public Boolean getIsSettle() {
		return isSettle;
	}
	public void setIsSettle(Boolean isSettle) {
		this.isSettle = isSettle;
	}
	
	public Integer getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}
	
	public Integer getTab() {
		return tab;
	}
	public void setTab(Integer tab) {
		this.tab = tab;
	}
	
	public Integer getByAgentUserId() {
		return byAgentUserId;
	}
	public void setByAgentUserId(Integer byAgentUserId) {
		this.byAgentUserId = byAgentUserId;
	}
	
	public String getPhoneOrNameLike() {
		return phoneOrNameLike;
	}
	public void setPhoneOrNameLike(String phoneOrNameLike) {
		this.phoneOrNameLike = phoneOrNameLike;
	}

	public Boolean getIsWarn() {
		return isWarn;
	}
	public void setIsWarn(Boolean isWarn) {
		this.isWarn = isWarn;
	}
	
	public Integer getFlowStateType() {
		return flowStateType;
	}
	public void setFlowStateType(Integer flowStateType) {
		this.flowStateType = flowStateType;
	}
	
	public Integer getFlowState() {
		return flowState;
	}
	public void setFlowState(Integer flowState) {
		this.flowState = flowState;
	}
	
	
}
