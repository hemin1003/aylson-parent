package com.aylson.dc.partner.search;

import com.aylson.core.frame.search.BaseSearch;

public class CouponApplySearch extends BaseSearch {
	
	private static final long serialVersionUID = -1501528547700525736L;
	
	
	//匹配查询
	private Integer id;                             //主键
	private Integer state;                          //状态
	private Integer applierId;                      //申请人id
	private Integer applyCouponId;                  //申请优惠券id
	private String ownerPhone;                      //业主电话
	private Integer provinceId;                     //省会id
	private Integer cityId;                         //城市id
	private Integer areaId;                         //区域id
	private Integer progress;                       //进度
	private String applyTime;                       //申请时间
	private Boolean isBind;                         //是否绑定
	private Boolean isClient = false;               //是否客户
	//模糊查询
	private String applierLike;                     //申请人名称
	private String applierPhoneLike;                //申请人电话
	private String ownerNameLike;                   //业主名称
	private String ownerPhoneLike;                  //业主电话
	private String applyCouponNameLike;             //申请优惠券名称
	
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
	
	public String getOwnerPhone() {
		return ownerPhone;
	}
	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
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
	
	public Integer getProgress() {
		return progress;
	}
	public void setProgress(Integer progress) {
		this.progress = progress;
	}
	
	public Boolean getIsBind() {
		return isBind;
	}
	public void setIsBind(Boolean isBind) {
		this.isBind = isBind;
	}
	
	public String getApplierLike() {
		return applierLike;
	}
	public void setApplierLike(String applierLike) {
		this.applierLike = applierLike;
	}
	
	public String getApplierPhoneLike() {
		return applierPhoneLike;
	}
	public void setApplierPhoneLike(String applierPhoneLike) {
		this.applierPhoneLike = applierPhoneLike;
	}
	
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	
	public String getOwnerNameLike() {
		return ownerNameLike;
	}
	public void setOwnerNameLike(String ownerNameLike) {
		this.ownerNameLike = ownerNameLike;
	}
	
	public String getOwnerPhoneLike() {
		return ownerPhoneLike;
	}
	public void setOwnerPhoneLike(String ownerPhoneLike) {
		this.ownerPhoneLike = ownerPhoneLike;
	}
	
	public String getApplyCouponNameLike() {
		return applyCouponNameLike;
	}
	public void setApplyCouponNameLike(String applyCouponNameLike) {
		this.applyCouponNameLike = applyCouponNameLike;
	}
	
	public Boolean getIsClient() {
		return isClient;
	}
	public void setIsClient(Boolean isClient) {
		this.isClient = isClient;
	}
	
	
}
