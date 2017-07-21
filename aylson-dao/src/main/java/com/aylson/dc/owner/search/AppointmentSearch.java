package com.aylson.dc.owner.search;

import com.aylson.core.frame.search.BaseSearch;

public class AppointmentSearch extends BaseSearch {

	private static final long serialVersionUID = 4254839442078943090L;
	
	//匹配查询
	private Integer id;                      //主键  
	private Integer state;                   //状态：1 新建 2 已处理  3作废
	private Integer provinceId;              //省会id
	private Integer cityId;                  //城市id
	private Integer areaId;                  //区域id
	private String appointDate;              //预约时间
	private String appointDateBegin;         //预约时间-开始
	private String appointDateEnd;           //预约时间-结束
	private String homeTime;                 //上门时间
	private String mobilePhone;              //手机号码
	private Integer byAgentUserId;           //所属代理商用户id
	private Integer stateType;               //0 报废 1待办 2已办
	private Integer userType;                //用户类型：1经销商 2总部
	//模糊查询
	private String mobilePhoneLike;          //手机号码
	private String designerLike;             //工程师姓名
	private String designerPhoneLike;        //工程师联系电话
	private String decorateProjectLike;      //装修项目
	private String billCodeLike;             //流水单号
	private String nameOrPhoneLike;          //客户名称或电话
	private String addressLike;              //地址
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	
	public String getAppointDate() {
		return appointDate;
	}
	public void setAppointDate(String appointDate) {
		this.appointDate = appointDate;
	}
	
	public String getAppointDateBegin() {
		return appointDateBegin;
	}
	public void setAppointDateBegin(String appointDateBegin) {
		this.appointDateBegin = appointDateBegin;
	}
	
	public String getAppointDateEnd() {
		return appointDateEnd;
	}
	public void setAppointDateEnd(String appointDateEnd) {
		this.appointDateEnd = appointDateEnd;
	}
	
	public String getHomeTime() {
		return homeTime;
	}
	public void setHomeTime(String homeTime) {
		this.homeTime = homeTime;
	}
	
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	public Integer getByAgentUserId() {
		return byAgentUserId;
	}
	public void setByAgentUserId(Integer byAgentUserId) {
		this.byAgentUserId = byAgentUserId;
	}
	
	public Integer getStateType() {
		return stateType;
	}
	public void setStateType(Integer stateType) {
		this.stateType = stateType;
	}
	
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	
	public String getMobilePhoneLike() {
		return mobilePhoneLike;
	}
	public void setMobilePhoneLike(String mobilePhoneLike) {
		this.mobilePhoneLike = mobilePhoneLike;
	}
	
	public String getDesignerLike() {
		return designerLike;
	}
	public void setDesignerLike(String designerLike) {
		this.designerLike = designerLike;
	}
	
	public String getDesignerPhoneLike() {
		return designerPhoneLike;
	}
	public void setDesignerPhoneLike(String designerPhoneLike) {
		this.designerPhoneLike = designerPhoneLike;
	}
	
	public String getDecorateProjectLike() {
		return decorateProjectLike;
	}
	public void setDecorateProjectLike(String decorateProjectLike) {
		this.decorateProjectLike = decorateProjectLike;
	}
	
	public String getBillCodeLike() {
		return billCodeLike;
	}
	public void setBillCodeLike(String billCodeLike) {
		this.billCodeLike = billCodeLike;
	}
	
	public String getNameOrPhoneLike() {
		return nameOrPhoneLike;
	}
	public void setNameOrPhoneLike(String nameOrPhoneLike) {
		this.nameOrPhoneLike = nameOrPhoneLike;
	}
	
	public String getAddressLike() {
		return addressLike;
	}
	public void setAddressLike(String addressLike) {
		this.addressLike = addressLike;
	}
	
	
}
