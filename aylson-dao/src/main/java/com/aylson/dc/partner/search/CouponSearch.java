
package com.aylson.dc.partner.search;

import com.aylson.core.frame.search.BaseSearch;

public class CouponSearch extends BaseSearch {
	
	private static final long serialVersionUID = -2621357917262978428L;
	
	//匹配查询
	private Integer id;                             //主键
	private Integer state;                          //状态
	private String createTime;                      //创建时间
	private String updateTime;                      //更新时间
	private Integer applyId;                        //申请id
	private String[] ids;                           //主键集合
	private String ownerPhone;                      //申请人电话
	private Boolean isClientCoupon = false;         //是否客户券，配合使用满足某些组合条件
	private Integer applierId;                      //申请人id
	private Integer orderId;                        //订单id
	private String orderCode;                       //订单号
	//模糊查询
	private String couponCodeLike;                  //券号
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
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	public Integer getApplyId() {
		return applyId;
	}
	public void setApplyId(Integer applyId) {
		this.applyId = applyId;
	}
	
	public String[] getIds() {
		return ids;
	}
	public void setIds(String[] ids) {
		this.ids = ids;
	}
	
	public String getOwnerPhone() {
		return ownerPhone;
	}
	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
	}
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	public String getCouponCodeLike() {
		return couponCodeLike;
	}
	public void setCouponCodeLike(String couponCodeLike) {
		this.couponCodeLike = couponCodeLike;
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
	
	public Boolean getIsClientCoupon() {
		return isClientCoupon;
	}
	public void setIsClientCoupon(Boolean isClientCoupon) {
		this.isClientCoupon = isClientCoupon;
	}
	
	public Integer getApplierId() {
		return applierId;
	}
	public void setApplierId(Integer applierId) {
		this.applierId = applierId;
	}
	
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	
}
