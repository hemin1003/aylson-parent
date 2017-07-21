package com.aylson.dc.owner.vo;

import java.util.List;

import com.aylson.dc.owner.po.Quotation;

public class QuotationVo extends Quotation {

	private static final long serialVersionUID = 4084143030992231619L;
	
	private String orderTimeStr;                  //下单时间
	private String deliveryTimeStr;               //交货时间
	private Integer designType;                   //设计类型：门、窗、阳光房
	List<QuotationDetailDWVo> detailDWVoList;     //
	List<QuotationDetailSRVo> detailSRVoList;     //
	private Integer designState;                  //状态
	private String drawUrl;                       //大样图

	public String getOrderTimeStr() {
		return orderTimeStr;
	}
	public void setOrderTimeStr(String orderTimeStr) {
		this.orderTimeStr = orderTimeStr;
	}
	
	public String getDeliveryTimeStr() {
		return deliveryTimeStr;
	}
	public void setDeliveryTimeStr(String deliveryTimeStr) {
		this.deliveryTimeStr = deliveryTimeStr;
	}
	
	public Integer getDesignType() {
		return designType;
	}
	public void setDesignType(Integer designType) {
		this.designType = designType;
	}
	
	public List<QuotationDetailDWVo> getDetailDWVoList() {
		return detailDWVoList;
	}
	public void setDetailDWVoList(List<QuotationDetailDWVo> detailDWVoList) {
		this.detailDWVoList = detailDWVoList;
	}
	
	public List<QuotationDetailSRVo> getDetailSRVoList() {
		return detailSRVoList;
	}
	public void setDetailSRVoList(List<QuotationDetailSRVo> detailSRVoList) {
		this.detailSRVoList = detailSRVoList;
	}
	
	public Integer getDesignState() {
		return designState;
	}
	public void setDesignState(Integer designState) {
		this.designState = designState;
	}
	
	public String getDrawUrl() {
		return drawUrl;
	}
	public void setDrawUrl(String drawUrl) {
		this.drawUrl = drawUrl;
	}
	
	
}
