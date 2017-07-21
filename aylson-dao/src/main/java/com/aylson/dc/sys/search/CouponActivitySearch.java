package com.aylson.dc.sys.search;

import com.aylson.core.frame.search.BaseSearch;

public class CouponActivitySearch extends BaseSearch{

	private static final long serialVersionUID = -549564898985574L;
	
	//匹配查询
	private Integer id;             		 
	private Integer activityType;   //类型：1线上活动    2线下活动    3电商活动
	private Integer state;	        //状态：0=未发布，默认值；1=已发布
	private Integer createdById;	//创建人id
	private Integer auditState;	    //审核状态：0未提交审核 1 审核中 2 审核通过 3审核不通过 
	private Integer userType;	    //用户类型：1 总部 2门店，来源于系统用户类型
	private String cityCode;              //城市编码
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getActivityType() {
		return activityType;
	}
	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}
	public Integer getCreatedById() {
		return createdById;
	}
	public void setCreatedById(Integer createdById) {
		this.createdById = createdById;
	}
	public Integer getAuditState() {
		return auditState;
	}
	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}
	
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	
}
