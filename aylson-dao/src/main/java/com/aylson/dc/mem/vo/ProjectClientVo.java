package com.aylson.dc.mem.vo;

import com.aylson.dc.mem.po.ProjectClient;

public class ProjectClientVo extends ProjectClient {

	private static final long serialVersionUID = -8109351852507586546L;
	
	private String clientName;           		  //客户名称
	private String projectName;           		  //工程名称
	private String province;           		      //省会
	private Integer provinceId;           		  //省会Id
	private String city;           		          //城市
	private Integer cityId;           		      //城市Id
	private String area;           		          //区域
	private Integer areaId;           		      //区域Id
	private String clientAddress;           	  //客户地址
	private String mobilePhone;           		  //客户移动电话
	private String budgetRange;           		  //工程预算范围
	private Integer budgetRangeValue;             //工程预算范围值
	private Integer budgetRangeId;                //工程预算范围id
	private String byAgent;           		      //所属代理商
	private Integer byAgentId;           		  //所属代理商id
	
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
	
	public String getClientAddress() {
		return clientAddress;
	}
	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}
	
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	public String getBudgetRange() {
		return budgetRange;
	}
	public void setBudgetRange(String budgetRange) {
		this.budgetRange = budgetRange;
	}
	
	public Integer getBudgetRangeValue() {
		return budgetRangeValue;
	}
	public void setBudgetRangeValue(Integer budgetRangeValue) {
		this.budgetRangeValue = budgetRangeValue;
	}
	
	public Integer getBudgetRangeId() {
		return budgetRangeId;
	}
	public void setBudgetRangeId(Integer budgetRangeId) {
		this.budgetRangeId = budgetRangeId;
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
	
	

	
}
