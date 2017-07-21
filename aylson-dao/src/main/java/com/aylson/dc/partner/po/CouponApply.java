package com.aylson.dc.partner.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 优惠券申请配置
 * @author Administrator
 *
 */
public class CouponApply implements Serializable{

	private static final long serialVersionUID = 2861776652808265722L;
	
	
	private Integer id;                             //主键
	private Integer applierId;                      //申请人id
	private Integer applyCouponId;                  //申请优惠券id
	private String applyCouponName;                 //申请优惠券名称
	private String ownerName;                       //业主名称
	private String ownerPhone;                      //业主电话
	private String province;                        //省会
	private Integer provinceId;                     //省会id
	private String city;                            //城市
	private Integer cityId;                         //城市id
	private String area;                            //区域
	private Integer areaId;                         //区域id
	private String street;                          //街道
	private String unit;                            //单元
	private String houseStyle;                      //房子风格	
	private String houseType;                       //户型
	private String houseArea;                       //房子面积
	private Integer progress;                       //进度
	private Integer state;                          //状态
	private Integer applyNum;                       //申请数量
	private Date applyTime;                         //申请时间
	private Boolean isBind;                         //是否绑定
	private Date auditTime;                         //审核时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getApplierId() {
		return applierId;
	}
	public void setApplierId(Integer applierId) {
		this.applierId = applierId;
	}
	
	public Integer getApplyCouponId() {
		return applyCouponId;
	}
	public void setApplyCouponId(Integer applyCouponId) {
		this.applyCouponId = applyCouponId;
	}
	
	public String getApplyCouponName() {
		return applyCouponName;
	}
	public void setApplyCouponName(String applyCouponName) {
		this.applyCouponName = applyCouponName;
	}
	
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
	public String getOwnerPhone() {
		return ownerPhone;
	}
	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
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
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getHouseStyle() {
		return houseStyle;
	}
	public void setHouseStyle(String houseStyle) {
		this.houseStyle = houseStyle;
	}
	
	public String getHouseType() {
		return houseType;
	}
	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
	
	public String getHouseArea() {
		return houseArea;
	}
	public void setHouseArea(String houseArea) {
		this.houseArea = houseArea;
	}
	
	public Integer getProgress() {
		return progress;
	}
	public void setProgress(Integer progress) {
		this.progress = progress;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	public Integer getApplyNum() {
		return applyNum;
	}
	public void setApplyNum(Integer applyNum) {
		this.applyNum = applyNum;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	public Boolean getIsBind() {
		return isBind;
	}
	public void setIsBind(Boolean isBind) {
		this.isBind = isBind;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	
	
}
