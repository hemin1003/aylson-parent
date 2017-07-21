package com.aylson.dc.partner.search;

import com.aylson.core.frame.search.BaseSearch;

public class PartnerAccountSearch extends BaseSearch {

	
	private static final long serialVersionUID = -9022523250926364959L;

	
	//匹配查询
	private Integer id;                              //id
	private String registerTime;                     //注册时间
	private Integer industryType;                    //行业类型
	private Integer state;                           //状态
	private String wxOpenId;                         //微信用户id
	private Integer userId;                          //系统用户id
	private Boolean isBonus;                         //是否分红
	//模糊查询
	private String partnerNameLike;                  //合伙人姓名
	private String mobilePhoneLike;                  //合伙人手机
	private String referralNameLike;                 //推荐人
	private String referralPhoneLike;                //推荐人电话
	private String agentNameLike;                    //代理商
	private String agentPhoneLike;                   //代理商电话
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	
	public Integer getIndustryType() {
		return industryType;
	}
	public void setIndustryType(Integer industryType) {
		this.industryType = industryType;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	public String getWxOpenId() {
		return wxOpenId;
	}
	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}
	
	public String getPartnerNameLike() {
		return partnerNameLike;
	}
	public void setPartnerNameLike(String partnerNameLike) {
		this.partnerNameLike = partnerNameLike;
	}
	
	public String getMobilePhoneLike() {
		return mobilePhoneLike;
	}
	public void setMobilePhoneLike(String mobilePhoneLike) {
		this.mobilePhoneLike = mobilePhoneLike;
	}
	
	public String getReferralNameLike() {
		return referralNameLike;
	}
	public void setReferralNameLike(String referralNameLike) {
		this.referralNameLike = referralNameLike;
	}
	
	public String getReferralPhoneLike() {
		return referralPhoneLike;
	}
	public void setReferralPhoneLike(String referralPhoneLike) {
		this.referralPhoneLike = referralPhoneLike;
	}
	
	public String getAgentNameLike() {
		return agentNameLike;
	}
	public void setAgentNameLike(String agentNameLike) {
		this.agentNameLike = agentNameLike;
	}
	
	public String getAgentPhoneLike() {
		return agentPhoneLike;
	}
	public void setAgentPhoneLike(String agentPhoneLike) {
		this.agentPhoneLike = agentPhoneLike;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Boolean getIsBonus() {
		return isBonus;
	}
	public void setIsBonus(Boolean isBonus) {
		this.isBonus = isBonus;
	}
	
	
}
