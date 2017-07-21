package com.aylson.dc.agent.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class VisitSign implements Serializable{

	private static final long serialVersionUID = 7314894121923384198L;
	
	private Integer id;             		//主键  
	private Integer agentId;            	//所属代理商id
	private String agentName;            	//所属代理商名称
	private String clientName;              //客户姓名  
	private String mobilePhone;             //客户电话
	private String address;           		//客户地址
	private Integer visitNum;           	//来访人数
	private Date  createTime;             	//创建时间 
	private String remark;                 	//备注
	private String province;                //省会
	private Integer provinceId;             //省会id
	private String city;                 	//城市
	private Integer cityId;                 //城市id
	private String area;                 	//区域
	private Integer areaId;                 //区域id
	private String projectName;             //工程名称
	private String budgetRange;             //工程预算范围
	private Integer budgetRangeValue;       //工程预算范围值
	private Integer budgetRangeId;          //工程预算范围id
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Integer getVisitNum() {
		return visitNum;
	}
	public void setVisitNum(Integer visitNum) {
		this.visitNum = visitNum;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
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
	

}
