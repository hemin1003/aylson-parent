package com.aylson.dc.partner.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 优惠券配置
 * @author Administrator
 *
 */
public class CouponConfig implements Serializable{

	private static final long serialVersionUID = -5857077468528324772L;
	
	
	private Integer id;                             //主键
	private String couponName;                      //券名
	private Float couponValue;                      //券面值
	private Integer achieveMoney;                   //满足多少钱可以使用
	private Integer state;                          //状态：0新建 1已上架 2已下架
	private String remark;                          //备注
	private Integer effectDays;                     //有效天数
	private Date createTime;                        //创建时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	
	public Float getCouponValue() {
		return couponValue;
	}
	public void setCouponValue(Float couponValue) {
		this.couponValue = couponValue;
	}
	
	public Integer getAchieveMoney() {
		return achieveMoney;
	}
	public void setAchieveMoney(Integer achieveMoney) {
		this.achieveMoney = achieveMoney;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Integer getEffectDays() {
		return effectDays;
	}
	public void setEffectDays(Integer effectDays) {
		this.effectDays = effectDays;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
