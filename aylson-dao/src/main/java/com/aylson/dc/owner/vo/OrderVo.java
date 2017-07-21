package com.aylson.dc.owner.vo;

import java.util.Date;
import java.util.List;

import com.aylson.dc.busi.vo.CostVo;
import com.aylson.dc.owner.po.Order;
import com.aylson.dc.partner.vo.CouponVo;
import com.aylson.dc.sys.vo.AttachmentVo;
import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class OrderVo extends Order {

	private static final long serialVersionUID = 3164029041230765732L;

	private String createTimeStr;                  //创建时间
	private String updateTimeStr;                  //更新时间
	private String scheduleRemark;                 //进步备注
	private List<OrderScheduleVo> scheduleVoList;  //进度列表
	private Float quoSalesAmount;                  //订货单销售金额
	private Boolean hasBuyShow;                    //是否发布过买家秀
	private Boolean isOnlySave = true;            //是否只是保存，true 只保存 false 提交
	private List<CouponVo> couponList;            //现金券列表
	private List<CostVo> costVoList;              //预约消费列表
	private List<AttachmentVo> attachmentVoList;  //附件列表
	private String couponIds;                     //现金券id集合
	private Integer warnDays;                     //预警天数
	private String dealer;                        //处理人
	private Integer dealerId;                     //处理人id
	private Date finishTime;                      //完工时间

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
	
	public String getScheduleRemark() {
		return scheduleRemark;
	}
	public void setScheduleRemark(String scheduleRemark) {
		this.scheduleRemark = scheduleRemark;
	}
	
	public List<OrderScheduleVo> getScheduleVoList() {
		return scheduleVoList;
	}
	public void setScheduleVoList(List<OrderScheduleVo> scheduleVoList) {
		this.scheduleVoList = scheduleVoList;
	}
	
	public Float getQuoSalesAmount() {
		return quoSalesAmount;
	}
	public void setQuoSalesAmount(Float quoSalesAmount) {
		this.quoSalesAmount = quoSalesAmount;
	}
	
	public Boolean getHasBuyShow() {
		return hasBuyShow;
	}
	public void setHasBuyShow(Boolean hasBuyShow) {
		this.hasBuyShow = hasBuyShow;
	}
	
	public Boolean getIsOnlySave() {
		return isOnlySave;
	}
	public void setIsOnlySave(Boolean isOnlySave) {
		this.isOnlySave = isOnlySave;
	}
	
	public List<CouponVo> getCouponList() {
		return couponList;
	}
	public void setCouponList(List<CouponVo> couponList) {
		this.couponList = couponList;
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
	
	public String getCouponIds() {
		return couponIds;
	}
	public void setCouponIds(String couponIds) {
		this.couponIds = couponIds;
	}
	
	public Integer getWarnDays() {
		return warnDays;
	}
	public void setWarnDays(Integer warnDays) {
		this.warnDays = warnDays;
	}
	
	public String getDealer() {
		return dealer;
	}
	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
	
	public Integer getDealerId() {
		return dealerId;
	}
	public void setDealerId(Integer dealerId) {
		this.dealerId = dealerId;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	
	
}
