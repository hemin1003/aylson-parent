package com.aylson.dc.pioneer.vo;

import java.util.List;

import com.aylson.dc.pioneer.po.PioneerAgent;

public class PioneerAgentVo extends PioneerAgent{

	private static final long serialVersionUID = 6017528812010789132L;
	
	private String submitter;                    //提交人账号名
	private String submitterRealName;            //提交人姓名
	private String submitterPhone;               //提交人电话
	private Integer submitterReferral;           //提交人的推荐人
	private String wxOpenId;                     //微信id
	private List<String> shopImgList;            //门店图片列表

	public String getSubmitter() {
		return submitter;
	}
	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}
	
	public String getSubmitterRealName() {
		return submitterRealName;
	}
	public void setSubmitterRealName(String submitterRealName) {
		this.submitterRealName = submitterRealName;
	}
	
	public String getSubmitterPhone() {
		return submitterPhone;
	}
	public void setSubmitterPhone(String submitterPhone) {
		this.submitterPhone = submitterPhone;
	}
	
	public Integer getSubmitterReferral() {
		return submitterReferral;
	}
	public void setSubmitterReferral(Integer submitterReferral) {
		this.submitterReferral = submitterReferral;
	}
	
	public String getWxOpenId() {
		return wxOpenId;
	}
	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}
	
	public List<String> getShopImgList() {
		return shopImgList;
	}
	public void setShopImgList(List<String> shopImgList) {
		this.shopImgList = shopImgList;
	}
	
	
}
