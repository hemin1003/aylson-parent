package com.aylson.dc.mem.search;

import com.aylson.core.frame.search.BaseSearch;

public class MemAccountSearch extends BaseSearch {

	private static final long serialVersionUID = 3264153299345637991L;
	//匹配查询
	private Integer id;           		  //主键
	private String accountName;           //账号
	private String realName;              //姓名
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
	private Integer level;                //会员级别
	private Integer status;               //状态
	private Integer memberType;           //会员类型：1设计师 2产业工人
	private String wxOpenId;              //微信用户id
	private Integer byAgentUserId;        //所属代理商用户id
	//模糊查询
	private String addressLike;           //地址
	private String accountNameLike;       //账号
	private String realNameLike;          //姓名
	private String mobilePhoneLike;       //移动电话
	private String referralNameLike;      //推荐人姓名
	
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
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
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
	
	public String getWxOpenId() {
		return wxOpenId;
	}
	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}
	
	public Integer getByAgentUserId() {
		return byAgentUserId;
	}
	public void setByAgentUserId(Integer byAgentUserId) {
		this.byAgentUserId = byAgentUserId;
	}
	
	public String getAddressLike() {
		return addressLike;
	}
	public void setAddressLike(String addressLike) {
		this.addressLike = addressLike;
	}
	
	public String getAccountNameLike() {
		return accountNameLike;
	}
	public void setAccountNameLike(String accountNameLike) {
		this.accountNameLike = accountNameLike;
	}
	
	public String getRealNameLike() {
		return realNameLike;
	}
	public void setRealNameLike(String realNameLike) {
		this.realNameLike = realNameLike;
	}
	
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
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
	
	
}
