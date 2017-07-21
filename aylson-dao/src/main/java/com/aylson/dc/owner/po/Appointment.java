package com.aylson.dc.owner.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Appointment implements Serializable{


	private static final long serialVersionUID = 3908240974590499677L;
	
	private Integer id;                      //主键  
	private String name;                     //姓名（联系人）
	private String mobilePhone;              //手机号码
	private String province;                 //省会
	private Integer provinceId;              //省会id
	private String city;                     //城市
	private Integer cityId;                  //城市id
	private String area;                     //区域
	private Integer areaId;                  //区域id
	private Date appointDate;                //预约时间
	private String address;                  //预约地址
	private String designer;                 //设计师名称
	private String designerPhone;            //设计师联系电话
	private String remark;                   //备注
	private Integer state;                   //状态：1 新建 2 已处理  3作废
	private Date homeTime;                   //上门时间
	private Date decoratingTime;             //预计装修时间
	private String byAgent;                  //所属代理商
	private Integer byAgentUserId;           //所属代理商用户id
	private String decorateProject;          //装修项目内容
	private String decorateProjectTypes;     //装修项目内容类型
	private String budgetRange;              //工程预算范围
	private String source;                   //添加来源：默认：wx web
	private String billCode;                 //流水单号
	private Date updateTime;                 //更新时间
	private Boolean isAgent;                 //是否代理商：false:直营    true:非直营
	private String dealer;                   //处理人
	private Integer dealerId;                //处理人id
	private String agentRemark;              //经销商备注
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
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
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getAppointDate() {
		return appointDate;
	}
	public void setAppointDate(Date appointDate) {
		this.appointDate = appointDate;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getDesigner() {
		return designer;
	}
	public void setDesigner(String designer) {
		this.designer = designer;
	}
	
	public String getDesignerPhone() {
		return designerPhone;
	}
	public void setDesignerPhone(String designerPhone) {
		this.designerPhone = designerPhone;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getHomeTime() {
		return homeTime;
	}
	public void setHomeTime(Date homeTime) {
		this.homeTime = homeTime;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getDecoratingTime() {
		return decoratingTime;
	}
	public void setDecoratingTime(Date decoratingTime) {
		this.decoratingTime = decoratingTime;
	}
	
	public String getByAgent() {
		return byAgent;
	}
	public void setByAgent(String byAgent) {
		this.byAgent = byAgent;
	}
	
	public Integer getByAgentUserId() {
		return byAgentUserId;
	}
	public void setByAgentUserId(Integer byAgentUserId) {
		this.byAgentUserId = byAgentUserId;
	}
	
	public String getDecorateProject() {
		return decorateProject;
	}
	public void setDecorateProject(String decorateProject) {
		this.decorateProject = decorateProject;
	}
	
	public String getDecorateProjectTypes() {
		return decorateProjectTypes;
	}
	public void setDecorateProjectTypes(String decorateProjectTypes) {
		this.decorateProjectTypes = decorateProjectTypes;
	}
	
	public String getBudgetRange() {
		return budgetRange;
	}
	public void setBudgetRange(String budgetRange) {
		this.budgetRange = budgetRange;
	}
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getBillCode() {
		return billCode;
	}
	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public Boolean getIsAgent() {
		return isAgent;
	}
	public void setIsAgent(Boolean isAgent) {
		this.isAgent = isAgent;
	}
	
	public String getDealer() {
		return dealer;
	}
	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
	
	public Integer getDealerId() {
		return dealerId;
	}
	public void setDealerId(Integer dealerId) {
		this.dealerId = dealerId;
	}
	
	public String getAgentRemark() {
		return agentRemark;
	}
	public void setAgentRemark(String agentRemark) {
		this.agentRemark = agentRemark;
	}
	

}
