package com.aylson.dc.mem.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ProjectInfo implements Serializable {
	
	private static final long serialVersionUID = 7239623887019314955L;
	
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
	private String clientAddress;           	  //客户地址
	private String mobilePhone;           		  //客户移动电话
	private String budgetRange;           		  //工程预算范围
	private Integer budgetRangeValue;             //工程预算范围值
	private Integer budgetRangeId;                //工程预算范围id
	private String byAgent;           		      //所属代理商
	private Integer byAgentId;           		  //所属代理商id
	private Integer status;           		      //状态
	private Date createTime;                      //创建时间
	private String remark;           		      //备注
	private Boolean isSent;                       //是否已赠送过积分
	private Integer integral;                     //赠送的积分
	private Integer gold;                         //项目花费的金币
	private Boolean isSuccessfulCase;             //是否成功案例
	private Date updateTime;                      //更新时间
	private String successCaseImg;                //成功案例主题图片
	
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
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	
	public Boolean getIsSent() {
		return isSent;
	}
	public void setIsSent(Boolean isSent) {
		this.isSent = isSent;
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
	
	public Boolean getIsSuccessfulCase() {
		return isSuccessfulCase;
	}
	public void setIsSuccessfulCase(Boolean isSuccessfulCase) {
		this.isSuccessfulCase = isSuccessfulCase;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getSuccessCaseImg() {
		return successCaseImg;
	}
	public void setSuccessCaseImg(String successCaseImg) {
		this.successCaseImg = successCaseImg;
	}
	
	
}
