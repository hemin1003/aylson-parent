package com.aylson.dc.owner.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Quotation implements Serializable{

	
	private static final long serialVersionUID = -1762948737352743247L;
	
	private Integer id;                      //主键
	private Integer appointId;               //预约id
	private Integer designId;                //设计信息表id
	private String clientName;               //客户姓名
	private String clientAddress;            //客户地址
	private String clientPhone;              //客户电话
	private String orderNo;                  //订单号
	private Date orderTime;                  //下单时间
	private String salesDep;                 //销售部门
	private String areaLeader;               //区域负责人
	private String shopPhone;                //专卖店联系电话
	private String shopContacter;            //专卖店联系人
	private Float orderAmount;               //订单金额
	private Float realAmount;                //实付金额
	private Float benefitAmount;             //优惠金额
	private Float deposit;                   //定金
	private Float salesAmount;               //销售金额
	private String payMode;                  //支付方式
	private Date deliveryTime;               //交货时间
	private String remark;                   //备注
	private String originator;               //制单人
	private String auditer;                  //审核人
	private String confirmer;                //确认人
	private String financer;                 //财务审核
	private String salesType;                //销售类型
	private Integer state;                   //状态
	private String billCode;                 //流水单号
	private Integer couponValue;             //优惠券值
	private Integer couponId;                //优惠券id
	private Float remainAmount;              //出厂前应付金额
	private Integer partnerCouponValue;      //合伙人减免优惠券值
	
	
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
	
	public Integer getDesignId() {
		return designId;
	}
	public void setDesignId(Integer designId) {
		this.designId = designId;
	}
	
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	public String getClientAddress() {
		return clientAddress;
	}
	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}
	
	public String getClientPhone() {
		return clientPhone;
	}
	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	
	public String getSalesDep() {
		return salesDep;
	}
	public void setSalesDep(String salesDep) {
		this.salesDep = salesDep;
	}
	
	public String getAreaLeader() {
		return areaLeader;
	}
	public void setAreaLeader(String areaLeader) {
		this.areaLeader = areaLeader;
	}
	
	public String getShopPhone() {
		return shopPhone;
	}
	public void setShopPhone(String shopPhone) {
		this.shopPhone = shopPhone;
	}
	
	public String getShopContacter() {
		return shopContacter;
	}
	public void setShopContacter(String shopContacter) {
		this.shopContacter = shopContacter;
	}
	
	public Float getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Float orderAmount) {
		this.orderAmount = orderAmount;
	}
	
	public Float getRealAmount() {
		return realAmount;
	}
	public void setRealAmount(Float realAmount) {
		this.realAmount = realAmount;
	}
	
	public Float getBenefitAmount() {
		return benefitAmount;
	}
	public void setBenefitAmount(Float benefitAmount) {
		this.benefitAmount = benefitAmount;
	}
	
	public Float getDeposit() {
		return deposit;
	}
	public void setDeposit(Float deposit) {
		this.deposit = deposit;
	}
	
	public Float getSalesAmount() {
		return salesAmount;
	}
	public void setSalesAmount(Float salesAmount) {
		this.salesAmount = salesAmount;
	}
	
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getOriginator() {
		return originator;
	}
	public void setOriginator(String originator) {
		this.originator = originator;
	}
	
	public String getAuditer() {
		return auditer;
	}
	public void setAuditer(String auditer) {
		this.auditer = auditer;
	}
	
	public String getConfirmer() {
		return confirmer;
	}
	public void setConfirmer(String confirmer) {
		this.confirmer = confirmer;
	}
	
	public String getFinancer() {
		return financer;
	}
	public void setFinancer(String financer) {
		this.financer = financer;
	}
	
	public String getSalesType() {
		return salesType;
	}
	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	public String getBillCode() {
		return billCode;
	}
	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
	
	public Integer getCouponValue() {
		//if(this.couponValue == null) return 0;
		return couponValue;
	}
	public void setCouponValue(Integer couponValue) {
		this.couponValue = couponValue;
	}
	
	public Integer getCouponId() {
		return couponId;
	}
	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}
	
	public Float getRemainAmount() {
		return remainAmount;
	}
	public void setRemainAmount(Float remainAmount) {
		this.remainAmount = remainAmount;
	}
	
	public Integer getPartnerCouponValue() {
		return partnerCouponValue;
	}
	public void setPartnerCouponValue(Integer partnerCouponValue) {
		this.partnerCouponValue = partnerCouponValue;
	}
	
	
}
