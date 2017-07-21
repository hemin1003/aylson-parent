package com.aylson.dc.owner.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Order implements Serializable{

	private static final long serialVersionUID = -771236097513431182L;
	
	
	private Integer id;                      //主键
	private String orderNo;                  //订单号
	private Date createTime;                 //创建时间
	private Date updateTime;                 //更新时间
	private Integer appointId;               //预约id，默认为0，表示无
	private String appointNo;                //预约单号
	private Integer state;                   //状态：1确认订单 2生产完成 3 产品入库中 4产品出库 5已经发货，预计5天后到达
	private String remark;                   //备注
	private String mobilePhone;              //电话号码
	private String name;                     //姓名（联系人）
	private String province;                 //省会
	private Integer provinceId;              //省会id
	private String city;                     //城市
	private Integer cityId;                  //城市id
	private String area;                     //区域
	private Integer areaId;                  //区域id
	private String address;                  //预约地址
	private String decorateProject;          //装修项目内容
	private String decorateProjectTypes;     //装修项目内容类型
	private Integer designId;                //设计id，默认为0，表示无
	private String designNo;                 //设计单号
	private Integer designType;              //设计类型
	private Boolean isSettle;                //是否结算
	private Integer partnerId;               //合伙人id，如果为null不是合伙人介绍的
	private Date settleTime;                 //结算时间
	private Float salesAmount;               //销售金额
	private Integer sourceType;              //订单来源类型：1 预约（默认） 2直接添加
	private Float reductionAcount;           //减免金额
	private Integer limitDays;               //工期天数
	private Integer level;                   //订单优先级：1 低 2 中 3 高
	private String byAgent;                  //所属代理商
	private Integer byAgentUserId;           //所属代理商用户id
	private Integer flowState;               //流程状态：0 经销商未确认 1经销商 已确认 2 总部未确认 
	private Float turnoverAmount;            //成交金额
	private String agentRemark;              //代理商备注
	private Date orderTime;                  //下单时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public Integer getAppointId() {
		return appointId;
	}
	public void setAppointId(Integer appointId) {
		this.appointId = appointId;
	}
	
	public String getAppointNo() {
		return appointNo;
	}
	public void setAppointNo(String appointNo) {
		this.appointNo = appointNo;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getDecorateProject() {
		return decorateProject;
	}
	public void setDecorateProject(String decorateProject) {
		this.decorateProject = decorateProject;
	}
	
	public String getDecorateProjectTypes() {
		return decorateProjectTypes;
	}
	public void setDecorateProjectTypes(String decorateProjectTypes) {
		this.decorateProjectTypes = decorateProjectTypes;
	}
	
	public Integer getDesignId() {
		return designId;
	}
	public void setDesignId(Integer designId) {
		this.designId = designId;
	}
	
	public String getDesignNo() {
		return designNo;
	}
	public void setDesignNo(String designNo) {
		this.designNo = designNo;
	}
	
	public Integer getDesignType() {
		return designType;
	}
	public void setDesignType(Integer designType) {
		this.designType = designType;
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
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getSettleTime() {
		return settleTime;
	}
	public void setSettleTime(Date settleTime) {
		this.settleTime = settleTime;
	}
	
	public Float getSalesAmount() {
		return salesAmount;
	}
	public void setSalesAmount(Float salesAmount) {
		this.salesAmount = salesAmount;
	}
	
	public Integer getSourceType() {
		return sourceType;
	}
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}
	
	public Float getReductionAcount() {
		return reductionAcount;
	}
	public void setReductionAcount(Float reductionAcount) {
		this.reductionAcount = reductionAcount;
	}
	
	public Integer getLimitDays() {
		return limitDays;
	}
	public void setLimitDays(Integer limitDays) {
		this.limitDays = limitDays;
	}
	
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public String getByAgent() {
		return byAgent;
	}
	public void setByAgent(String byAgent) {
		this.byAgent = byAgent;
	}
	
	public Integer getByAgentUserId() {
		return byAgentUserId;
	}
	public void setByAgentUserId(Integer byAgentUserId) {
		this.byAgentUserId = byAgentUserId;
	}
	
	public Integer getFlowState() {
		return flowState;
	}
	public void setFlowState(Integer flowState) {
		this.flowState = flowState;
	}
	
	public Float getTurnoverAmount() {
		return turnoverAmount;
	}
	public void setTurnoverAmount(Float turnoverAmount) {
		this.turnoverAmount = turnoverAmount;
	}
	
	public String getAgentRemark() {
		return agentRemark;
	}
	public void setAgentRemark(String agentRemark) {
		this.agentRemark = agentRemark;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	
	
}
