package com.aylson.dc.owner.vo;

import java.util.List;

import com.aylson.dc.owner.po.OwnerInfo;
import com.aylson.dc.sys.vo.AttachmentVo;

public class OwnerInfoVo extends OwnerInfo {

	private static final long serialVersionUID = 1653323699438232842L;
	
	
	private String createTimeStr;                  //创建时间
	private List<AttachmentVo> attachList;         //客户信息附件
	private List<OrderVo> orderList;               //客户订单信息
	private Float turnoverAmountTotal;             //订单总金额
	private Integer orderNum;                      //订单数
	

	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
	public List<AttachmentVo> getAttachList() {
		return attachList;
	}
	public void setAttachList(List<AttachmentVo> attachList) {
		this.attachList = attachList;
	}
	
	public List<OrderVo> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<OrderVo> orderList) {
		this.orderList = orderList;
	}
	
	public Float getTurnoverAmountTotal() {
		return turnoverAmountTotal;
	}
	public void setTurnoverAmountTotal(Float turnoverAmountTotal) {
		this.turnoverAmountTotal = turnoverAmountTotal;
	}
	
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	
	
}
