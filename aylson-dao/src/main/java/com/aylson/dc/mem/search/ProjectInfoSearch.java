package com.aylson.dc.mem.search;

import com.aylson.core.frame.search.BaseSearch;

public class ProjectInfoSearch extends BaseSearch {

	private static final long serialVersionUID = -8457667937420809881L;

	//匹配查询
	private Integer id;           		          //主键
	private Integer memberId;           		  //会员id
	private String clientName;           		  //客户名称
	private String projectName;           		  //工程名称
	private String province;           		      //省会
	private Integer provinceId;           		  //省会Id
	private String city;           		          //城市
	private Integer cityId;           		      //城市Id
	private String area;           		          //区域
	private Integer areaId;           		      //区域Id
	private String mobilePhone;           		  //移动电话
	private String byAgent;           		      //所属代理商
	private Integer byAgentId;           		  //所属代理商id
	private Integer status;           		      //状态
	private Integer isHadDesign;                  //是否已设计:0 未设计 1已设计
	private Integer isVerify;                     //是否已审核:0 未审核 1已审核
	private Integer memberType;                   //会员类型：1设计师 2产业工人
	private Boolean isSuccessfulCase;             //是否成功案例
	private String statusMerge;                   //状态合并
	//模糊查询
	private String projectNameLike;               //工程名称
	private String productTypesLike;           	  //产品类型
	private String mobilePhoneLike;           	  //移动电话
	private String addressLike;           	      //移动电话
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
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
	
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	public String getByAgent() {
		return byAgent;
	}
	public void setByAgent(String byAgent) {
		this.byAgent = byAgent;
	}
	
	public Integer getByAgentId() {
		return byAgentId;
	}
	public void setByAgentId(Integer byAgentId) {
		this.byAgentId = byAgentId;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getIsHadDesign() {
		return isHadDesign;
	}
	public void setIsHadDesign(Integer isHadDesign) {
		this.isHadDesign = isHadDesign;
	}
	
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
	
	public Integer getIsVerify() {
		return isVerify;
	}
	public void setIsVerify(Integer isVerify) {
		this.isVerify = isVerify;
	}
	
	public Boolean getIsSuccessfulCase() {
		return isSuccessfulCase;
	}
	public void setIsSuccessfulCase(Boolean isSuccessfulCase) {
		this.isSuccessfulCase = isSuccessfulCase;
	}
	
	public String getStatusMerge() {
		return statusMerge;
	}
	public void setStatusMerge(String statusMerge) {
		this.statusMerge = statusMerge;
	}
	
	public String getProjectNameLike() {
		return projectNameLike;
	}
	public void setProjectNameLike(String projectNameLike) {
		this.projectNameLike = projectNameLike;
	}
	
	public String getProductTypesLike() {
		return productTypesLike;
	}
	public void setProductTypesLike(String productTypesLike) {
		this.productTypesLike = productTypesLike;
	}
	
	public String getMobilePhoneLike() {
		return mobilePhoneLike;
	}
	public void setMobilePhoneLike(String mobilePhoneLike) {
		this.mobilePhoneLike = mobilePhoneLike;
	}
	
	public String getAddressLike() {
		return addressLike;
	}
	public void setAddressLike(String addressLike) {
		this.addressLike = addressLike;
	}
	
	
}
