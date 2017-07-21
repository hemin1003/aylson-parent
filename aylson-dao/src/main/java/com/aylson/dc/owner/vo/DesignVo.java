package com.aylson.dc.owner.vo;

import java.util.Date;
import java.util.List;

import com.aylson.dc.owner.po.Design;
import com.aylson.utils.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class DesignVo extends Design {

	private static final long serialVersionUID = -7619520008437377799L;
	
	private String orderTimeStr;                  //下单时间
	private Integer appointState;                 //预约状态
	private Boolean isAgent;                      //是否代理商：false:直营    true:非直营
	private List<DesignDetailDWVo> designDetailDWList;
	private DesignDetailSRVo designDetailSRVo;
	
	private Date quoOrderTime;                    //下单时间
	private Float orderAmount;                    //订单金额
	private Float realAmount;                     //实付金额
	private Float benefitAmount;                  //优惠金额
	private Float deposit;                        //定金
	private Float salesAmount;                    //销售金额
	private String payMode;                       //支付方式
	private Date deliveryTime;                    //交货时间
	private Integer couponValue;                  //优惠券值
	private Integer partnerCouponValue;           //合伙人申请优惠券值
	
	private String drawOpitionHistory;            //大样图历史意见
	private Integer byAgentUserId;                //所属代理商用户id
	private QuotationVo quotationVo;              //报价单信息

	public String getOrderTimeStr() {
		return orderTimeStr;
	}
	public void setOrderTimeStr(String orderTimeStr) {
		this.orderTimeStr = orderTimeStr;
	}
	
	public Integer getAppointState() {
		return appointState;
	}
	public void setAppointState(Integer appointState) {
		this.appointState = appointState;
	}
	
	public Boolean getIsAgent() {
		return isAgent;
	}
	public void setIsAgent(Boolean isAgent) {
		this.isAgent = isAgent;
	}
	
	public List<DesignDetailDWVo> getDesignDetailDWList() {
		return designDetailDWList;
	}
	public void setDesignDetailDWList(List<DesignDetailDWVo> designDetailDWList) {
		this.designDetailDWList = designDetailDWList;
	}
	
	public DesignDetailSRVo getDesignDetailSRVo() {
		return designDetailSRVo;
	}
	public void setDesignDetailSRVo(DesignDetailSRVo designDetailSRVo) {
		this.designDetailSRVo = designDetailSRVo;
	}
	
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getQuoOrderTime() {
		return quoOrderTime;
	}
	public void setQuoOrderTime(Date quoOrderTime) {
		this.quoOrderTime = quoOrderTime;
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
	
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	
	public String getDrawOpitionHistory() {
		return drawOpitionHistory;
	}
	public void setDrawOpitionHistory(String drawOpitionHistory) {
		this.drawOpitionHistory = drawOpitionHistory;
	}
	
	public Integer getByAgentUserId() {
		return byAgentUserId;
	}
	public void setByAgentUserId(Integer byAgentUserId) {
		this.byAgentUserId = byAgentUserId;
	}
	
	public QuotationVo getQuotationVo() {
		return quotationVo;
	}
	public void setQuotationVo(QuotationVo quotationVo) {
		this.quotationVo = quotationVo;
	}
	
	public Integer getCouponValue() {
		if(this.couponValue == null) return 0;
		return couponValue;
	}
	public void setCouponValue(Integer couponValue) {
		this.couponValue = couponValue;
	}
	
	public Integer getPartnerCouponValue() {
		if(this.partnerCouponValue == null) return 0;
		return partnerCouponValue;
	}
	public void setPartnerCouponValue(Integer partnerCouponValue) {
		this.partnerCouponValue = partnerCouponValue;
	}
	
	
	
	
}
