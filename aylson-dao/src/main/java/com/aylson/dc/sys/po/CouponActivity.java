package com.aylson.dc.sys.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class CouponActivity implements Serializable{

	private static final long serialVersionUID = -2227529898965806L;
	
	private Integer id;                      //主键  		 
	private Integer activityType;            //类型：1线上活动    2线下活动    3电商活动
	private String title;                    //活动标题 	
	private String summary;                  //活动摘要
	private Date startTime;                  //活动开始时间
	private Date endTime;                    //活动结束时间
	private String location;                 //活动地点
	private Integer limitNum;                //活动人数限制
	private String url;                      //活动图
	private String content;                  //活动内容
	private Integer state;	                 //状态：0=未发布，默认值；1=已发布
	private Date createdTime;                //创建时间
	private String createdBy;                //创建人
	private Date updatedTime;                //更新时间
	private String updatedBy;                //更新人
	private Integer couponFkid;              //优惠券配置id
	private String area;                     //区域
	private Integer createdById;			 //创建人id
	private Integer updatedById;			 //更新人id
	private String auditOpinion;			 //审核意见
	private Integer auditState;	    		 //审核状态：0未提交审核 1 审核中 2 审核通过 3审核不通过 
	private Integer userType;	   			 //用户类型：1 总部 2门店，来源于系统用户类型
	private String province;                 //省会
	private Integer provinceId;              //省会id
	private String city;                     //城市
	private Integer cityId;                  //城市id
	private String cityCode;                 //城市编码
	private Integer areaId;                  //区域id
	
	
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	public Integer getCouponFkid() {
		return couponFkid;
	}
	public void setCouponFkid(Integer couponFkid) {
		this.couponFkid = couponFkid;
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
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public Integer getLimitNum() {
		return limitNum;
	}
	public void setLimitNum(Integer limitNum) {
		this.limitNum = limitNum;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	
	public Integer getCreatedById() {
		return createdById;
	}
	public void setCreatedById(Integer createdById) {
		this.createdById = createdById;
	}
	
	public Integer getUpdatedById() {
		return updatedById;
	}
	public void setUpdatedById(Integer updatedById) {
		this.updatedById = updatedById;
	}
	
	public String getAuditOpinion() {
		return auditOpinion;
	}
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
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
	
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	
	
}