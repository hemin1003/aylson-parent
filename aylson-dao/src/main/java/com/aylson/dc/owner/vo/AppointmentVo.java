package com.aylson.dc.owner.vo;

import java.util.List;

import com.aylson.dc.busi.vo.CostVo;
import com.aylson.dc.busi.vo.FlowNodeVo;
import com.aylson.dc.owner.po.Appointment;
import com.aylson.dc.partner.vo.CouponVo;
import com.aylson.dc.sys.vo.AttachmentVo;

public class AppointmentVo extends Appointment {

	private static final long serialVersionUID = 3813103900972310930L;

	private String appointDateStr;                //预约时间
	private String homeTimeStr;                   //预约上门时间
	private String decoratingTimeStr;             //预计装修时间
	private List<DesignVo> designVoList;          //设计信息列表
	private List<CostVo> costVoList;              //预约消费列表
	private List<AttachmentVo> attachmentVoList;  //附件列表
	private Boolean isOnlySave = true;            //是否只是保存，true 只保存 false 提交
	private String couponIds;                     //现金券id集合
	private FlowNodeVo flowNodeVo;                //下一个节点信息 
	private OrderVo orderVo;                      //对应订单信息
	private List<CouponVo> couponList;            //现金券列表
	private Float turnoverAmount = 0.0f;          //经销商成交金额
	private Float couponValueSum = 0.0f;          //现金券减免金额
	private Float salesAmount = 0.0f;             //销售总金额

	public String getAppointDateStr() {
		return appointDateStr;
	}
	public void setAppointDateStr(String appointDateStr) {
		this.appointDateStr = appointDateStr;
	}
	
	public String getHomeTimeStr() {
		return homeTimeStr;
	}
	public void setHomeTimeStr(String homeTimeStr) {
		this.homeTimeStr = homeTimeStr;
	}
	
	public String getDecoratingTimeStr() {
		return decoratingTimeStr;
	}
	public void setDecoratingTimeStr(String decoratingTimeStr) {
		this.decoratingTimeStr = decoratingTimeStr;
	}
	
	public List<DesignVo> getDesignVoList() {
		return designVoList;
	}
	public void setDesignVoList(List<DesignVo> designVoList) {
		this.designVoList = designVoList;
	}
	
	public List<CostVo> getCostVoList() {
		return costVoList;
	}
	public void setCostVoList(List<CostVo> costVoList) {
		this.costVoList = costVoList;
	}
	
	public List<AttachmentVo> getAttachmentVoList() {
		return attachmentVoList;
	}
	public void setAttachmentVoList(List<AttachmentVo> attachmentVoList) {
		this.attachmentVoList = attachmentVoList;
	}
	
	public Boolean getIsOnlySave() {
		return isOnlySave;
	}
	public void setIsOnlySave(Boolean isOnlySave) {
		this.isOnlySave = isOnlySave;
	}
	
	public String getCouponIds() {
		return couponIds;
	}
	public void setCouponIds(String couponIds) {
		this.couponIds = couponIds;
	}
	
	public FlowNodeVo getFlowNodeVo() {
		return flowNodeVo;
	}
	public void setFlowNodeVo(FlowNodeVo flowNodeVo) {
		this.flowNodeVo = flowNodeVo;
	}
	
	public OrderVo getOrderVo() {
		return orderVo;
	}
	public void setOrderVo(OrderVo orderVo) {
		this.orderVo = orderVo;
	}
	
	public List<CouponVo> getCouponList() {
		return couponList;
	}
	public void setCouponList(List<CouponVo> couponList) {
		this.couponList = couponList;
	}
	
	public Float getTurnoverAmount() {
		return turnoverAmount;
	}
	public void setTurnoverAmount(Float turnoverAmount) {
		this.turnoverAmount = turnoverAmount;
	}
	
	public Float getCouponValueSum() {
		return couponValueSum;
	}
	public void setCouponValueSum(Float couponValueSum) {
		this.couponValueSum = couponValueSum;
	}
	
	public Float getSalesAmount() {
		return salesAmount;
	}
	public void setSalesAmount(Float salesAmount) {
		this.salesAmount = salesAmount;
	}
	
	
}
