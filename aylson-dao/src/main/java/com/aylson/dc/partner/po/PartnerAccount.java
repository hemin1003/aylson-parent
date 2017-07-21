package com.aylson.dc.partner.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 合伙人账号信息
 * @author Administrator
 *
 */
public class PartnerAccount implements Serializable{

	
	private static final long serialVersionUID = 2165059683346933638L;
	
	private Integer id;                             //主键
	private String partnerName;                     //合伙人姓名
	private String mobilePhone;                     //手机号
	private String pwd;                             //密码
	private String photo;                           //个人照片
	private Integer industryType;                   //行业类型
	private String storeBrand;                      //卖场/品牌
	private String referralName;                    //推荐人姓名
	private String referralPhone;                   //推荐人电话
	private String agentName;                       //代理商名称
	private String agentPhone;                      //代理商电话
	private Date registerTime;                      //注册时间
	private String province;                        //省会
	private Integer provinceId;                     //省会id
	private String city;                            //城市
	private Integer cityId;                         //城市id
	private String wxNickName;                      //微信昵称
	private String wxOpenId;                        //微信openId
	private String wxHeadPhoto;                     //微信头像
	private String wxUnionId;                       //微信unionId
	private Float wallet;                           //钱包
	private Integer state;                          //状态
	private String cardID;                          //身份证号
	private String bankName;                        //开户行
	private String bankNo;                          //银行账号
	private String bankAccountName;                 //户名
	private String qrCode;                          //我的（合伙人）二维码
	private Integer referralId;                     //推荐人id
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public Integer getIndustryType() {
		return industryType;
	}
	public void setIndustryType(Integer industryType) {
		this.industryType = industryType;
	}
	
	public String getStoreBrand() {
		return storeBrand;
	}
	public void setStoreBrand(String storeBrand) {
		this.storeBrand = storeBrand;
	}
	
	public String getReferralName() {
		return referralName;
	}
	public void setReferralName(String referralName) {
		this.referralName = referralName;
	}
	
	public String getReferralPhone() {
		return referralPhone;
	}
	public void setReferralPhone(String referralPhone) {
		this.referralPhone = referralPhone;
	}
	
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	public String getAgentPhone() {
		return agentPhone;
	}
	public void setAgentPhone(String agentPhone) {
		this.agentPhone = agentPhone;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
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
	
	public String getWxNickName() {
		return wxNickName;
	}
	public void setWxNickName(String wxNickName) {
		this.wxNickName = wxNickName;
	}
	
	public String getWxOpenId() {
		return wxOpenId;
	}
	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}
	
	public String getWxHeadPhoto() {
		return wxHeadPhoto;
	}
	public void setWxHeadPhoto(String wxHeadPhoto) {
		this.wxHeadPhoto = wxHeadPhoto;
	}
	
	public String getWxUnionId() {
		return wxUnionId;
	}
	public void setWxUnionId(String wxUnionId) {
		this.wxUnionId = wxUnionId;
	}
	
	public Float getWallet() {
		return wallet;
	}
	public void setWallet(Float wallet) {
		this.wallet = wallet;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	public String getCardID() {
		return cardID;
	}
	public void setCardID(String cardID) {
		this.cardID = cardID;
	}
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	
	public String getBankAccountName() {
		return bankAccountName;
	}
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}
	
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	
	public Integer getReferralId() {
		return referralId;
	}
	public void setReferralId(Integer referralId) {
		this.referralId = referralId;
	}
	
	
}
