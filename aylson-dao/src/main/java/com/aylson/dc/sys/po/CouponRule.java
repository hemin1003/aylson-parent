package com.aylson.dc.sys.po;

import java.io.Serializable;
import java.util.Date;

public class CouponRule implements Serializable{

	private static final long serialVersionUID = -3227541316548418L;
	
	private Integer id;             		//主键
	private Integer couponFkid;             //优惠券id
	private Integer startPrice;             //开始金额
	private Integer endPrice;               //结束金额
	private Integer deratePrice;            //减免金额
	private Date createdTime;               //创建时间
	private String createdBy;               //创建人
	private Date updatedTime;               //更新时间
	private String updatedBy;               //更新人
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCouponFkid() {
		return couponFkid;
	}
	public void setCouponFkid(Integer couponFkid) {
		this.couponFkid = couponFkid;
	}
	public Integer getStartPrice() {
		return startPrice;
	}
	public void setStartPrice(Integer startPrice) {
		this.startPrice = startPrice;
	}
	public Integer getEndPrice() {
		return endPrice;
	}
	public void setEndPrice(Integer endPrice) {
		this.endPrice = endPrice;
	}
	public Integer getDeratePrice() {
		return deratePrice;
	}
	public void setDeratePrice(Integer deratePrice) {
		this.deratePrice = deratePrice;
	}
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
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
}
