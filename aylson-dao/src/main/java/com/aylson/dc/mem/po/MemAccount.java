package com.aylson.dc.mem.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class MemAccount implements Serializable {
	
	private static final long serialVersionUID = 3928503360340879572L;
	
	private Integer id;           		  //主键
	private String accountName;           //账号
	private String pwd;                   //密码
	private String realName;              //姓名
	private String photo;                 //头像
	private Integer referralId;           //推荐人id
	private String referralName;          //推荐人姓名
	private String companyName;           //单位名称
	private String province;              //省会
	private Integer provinceId;           //省会id
	private String city;                  //城市
	private Integer cityId;               //城市id
	private String area;                  //区域
	private Integer areaId;               //区域id
	private String address;               //地址
	private String mobilePhone;           //移动电话
	private String email;                 //电子邮箱
	private String qq;                    //qq号码
	private Date registerTime;            //注册时间
	private Integer integral;             //积分
	private Integer gold;                 //金币
	private Integer level;                //会员级别
	private Integer status;               //状态:0 待审核（暂时废弃） 1有效  2 禁用
	private String wxNickName;            //微信昵称
	private String wxOpenId;              //微信用户id
	private String wxHeadPhoto;           //微信头像
	private String wxUnionId;             //微信unionID机制
	private Boolean sex;                  //性别
	private Date birthday;                //出生日期
	private Integer memberType;           //会员类型：1设计师 2产业工人
	private String wxQrcodeTicket;        //关注二维码ticket
	private Integer totalIntegral;        //获取的总积分
	private Integer integralLevel;        //会员积分等级
	private Integer byAgentUserId;        //所属代理商用户id
	private String byAgent;               //所属代理商
	private Float wallet;                 //钱包
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public Integer getReferralId() {
		return referralId;
	}
	public void setReferralId(Integer referralId) {
		this.referralId = referralId;
	}
	
	public String getReferralName() {
		return referralName;
	}
	public void setReferralName(String referralName) {
		this.referralName = referralName;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	
	public Integer getGold() {
		return gold;
	}
	public void setGold(Integer gold) {
		this.gold = gold;
	}
	
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public void setWxOpenid(String wxOpenId) {
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
	public void setWxUnionid(String wxUnionId) {
		this.wxUnionId = wxUnionId;
	}
	
	public Boolean getSex() {
		return sex;
	}
	public void setSex(Boolean sex) {
		this.sex = sex;
	}
	
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}
	public void setWxUnionId(String wxUnionId) {
		this.wxUnionId = wxUnionId;
	}
	
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
	
	public String getWxQrcodeTicket() {
		return wxQrcodeTicket;
	}
	public void setWxQrcodeTicket(String wxQrcodeTicket) {
		this.wxQrcodeTicket = wxQrcodeTicket;
	}
	
	public Integer getTotalIntegral() {
		return totalIntegral;
	}
	public void setTotalIntegral(Integer totalIntegral) {
		this.totalIntegral = totalIntegral;
	}
	
	public Integer getIntegralLevel() {
		return integralLevel;
	}
	public void setIntegralLevel(Integer integralLevel) {
		this.integralLevel = integralLevel;
	}
	
	public Integer getByAgentUserId() {
		return byAgentUserId;
	}
	public void setByAgentUserId(Integer byAgentUserId) {
		this.byAgentUserId = byAgentUserId;
	}
	
	public String getByAgent() {
		return byAgent;
	}
	public void setByAgent(String byAgent) {
		this.byAgent = byAgent;
	}
	
	public Float getWallet() {
		return wallet;
	}
	public void setWallet(Float wallet) {
		this.wallet = wallet;
	}
	

}
