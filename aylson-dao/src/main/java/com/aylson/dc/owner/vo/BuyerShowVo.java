package com.aylson.dc.owner.vo;

import com.aylson.dc.owner.po.BuyerShow;

public class BuyerShowVo extends BuyerShow {

	private static final long serialVersionUID = 4116100612706334363L;
	
	
	private String createTimeStr;                  //创建时间
	private Integer praiseNum;                     //点赞数
	private String wxNickName;                     //微信昵称
	private String wxHeadPhoto;                    //微信头像
	private Boolean isPraise = false;              //是否点过赞

	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
	public Integer getPraiseNum() {
		return praiseNum;
	}
	public void setPraiseNum(Integer praiseNum) {
		this.praiseNum = praiseNum;
	}
	
	public String getWxNickName() {
		return wxNickName;
	}
	public void setWxNickName(String wxNickName) {
		this.wxNickName = wxNickName;
	}
	
	public String getWxHeadPhoto() {
		return wxHeadPhoto;
	}
	public void setWxHeadPhoto(String wxHeadPhoto) {
		this.wxHeadPhoto = wxHeadPhoto;
	}
	
	public Boolean getIsPraise() {
		return isPraise;
	}
	public void setIsPraise(Boolean isPraise) {
		this.isPraise = isPraise;
	}
	
	
}
