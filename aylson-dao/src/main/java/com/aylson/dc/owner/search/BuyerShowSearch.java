package com.aylson.dc.owner.search;

import com.aylson.core.frame.search.BaseSearch;

public class BuyerShowSearch extends BaseSearch {

	
	private static final long serialVersionUID = -757638120801918036L;
	
	
	//匹配查询
	private Integer id;                      //主键
	private Integer memberId;                //会员id，默认为-1，表示无
	private Integer orderId;                 //订单id，默认为-1，表示无
	private Integer appointId;               //预约id，默认为-1，表示无
	private Integer designId;                //设计id，默认为-1，表示无
	private String provinceCode;             //省会编码
	private Integer provinceId;              //省会id
	private String cityCode;                 //城市编码
	private Integer cityId;                  //城市id
	private String areaCode;                 //区域编码
	private Integer areaId;                  //区域id
	//模糊查询
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	public Integer getAppointId() {
		return appointId;
	}
	public void setAppointId(Integer appointId) {
		this.appointId = appointId;
	}
	
	public Integer getDesignId() {
		return designId;
	}
	public void setDesignId(Integer designId) {
		this.designId = designId;
	}
	
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	
	
}
