package com.aylson.dc.partner.vo;

import com.aylson.dc.partner.po.Coupon;

public class CouponVo extends Coupon {

	private static final long serialVersionUID = -8724408789613210979L;
	
	private String ownerName;                   //业主名称
	private String ownerPhone;                  //业主电话
	private String province;                    //省会
	private Integer provinceId;                 //省会id
	private String city;                        //城市
	private Integer cityId;                     //城市id
	private String area;                        //区域
	private Integer areaId;                     //区域id
	private String street;                      //街道
	private String unit;                        //单元
	private String applyCouponName;             //申请优惠券名称
	private Integer couponValue;                //申请优惠券券值
	private String applier;                     //申请人名称
	private String applierPhone;                //申请人电话
	private Integer achieveMoney;               //满足多少钱可以使用
	
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
	
	public String getApplyCouponName() {
		return applyCouponName;
	}
	public void setApplyCouponName(String applyCouponName) {
		this.applyCouponName = applyCouponName;
	}
	
	public Integer getCouponValue() {
		return couponValue;
	}
	public void setCouponValue(Integer couponValue) {
		this.couponValue = couponValue;
	}
	
	public String getApplier() {
		return applier;
	}
	public void setApplier(String applier) {
		this.applier = applier;
	}
	
	public String getApplierPhone() {
		return applierPhone;
	}
	public void setApplierPhone(String applierPhone) {
		this.applierPhone = applierPhone;
	}
	
	public Integer getAchieveMoney() {
		return achieveMoney;
	}
	public void setAchieveMoney(Integer achieveMoney) {
		this.achieveMoney = achieveMoney;
	}
	
	
}
