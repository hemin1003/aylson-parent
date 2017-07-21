package com.aylson.dc.pioneer.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class PioneerAgent implements Serializable{

	private static final long serialVersionUID = -5980592936936936506L;
	
	
	private Integer id;                          //主键
	private String agentName;                    //代理商名称
	private String province;                     //代理商所在省会
	private Integer provinceId;                  //代理商所在省会id
	private String city;                         //代理商所在城市
	private Integer cityId;                      //代理商所在城市id
	private String area;                         //代理商所在区域
	private Integer areaId;                      //代理商所在区域id
	private String contactPhone;                 //联系电话
	private String address;                      //地址
	private Date createTime;                     //创建时间
	private Date udpateTime;                     //更新时间
	private String shopProvince;                 //商店所在省会
	private Integer shopProvinceId;              //商店所在省会id
	private String shopCity;                     //商店所在城市
	private Integer shopCityId;                  //商店所在城市id
	private String shopArea;                     //商店所在区域
	private Integer shopAreaId;                  //商店所在区域id
	private String shopName;                     //商店名称
	private String shopAddress;                  //商店地址
	private Integer submitterId;                 //提交人id
	private Integer status;                      //状态
	private String remark;                       //备注
	private String historyRemark;                //历史备注
	private String shopImg;                      //门店图片
	private Float shopAreas;                     //商店面积
	private Integer signMode;                    //签约方式：1 独立介绍 2 非独立介绍
	private String agency;                       //之前代理品牌
	private String descs;                         //概况描述
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
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
	
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getUdpateTime() {
		return udpateTime;
	}
	public void setUdpateTime(Date udpateTime) {
		this.udpateTime = udpateTime;
	}
	
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
	public String getShopAddress() {
		return shopAddress;
	}
	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}
	
	public Integer getSubmitterId() {
		return submitterId;
	}
	public void setSubmitterId(Integer submitterId) {
		this.submitterId = submitterId;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getShopProvince() {
		return shopProvince;
	}
	public void setShopProvince(String shopProvince) {
		this.shopProvince = shopProvince;
	}
	
	public Integer getShopProvinceId() {
		return shopProvinceId;
	}
	public void setShopProvinceId(Integer shopProvinceId) {
		this.shopProvinceId = shopProvinceId;
	}
	
	public String getShopCity() {
		return shopCity;
	}
	public void setShopCity(String shopCity) {
		this.shopCity = shopCity;
	}
	
	public Integer getShopCityId() {
		return shopCityId;
	}
	public void setShopCityId(Integer shopCityId) {
		this.shopCityId = shopCityId;
	}
	
	public String getShopArea() {
		return shopArea;
	}
	public void setShopArea(String shopArea) {
		this.shopArea = shopArea;
	}
	
	public Integer getShopAreaId() {
		return shopAreaId;
	}
	public void setShopAreaId(Integer shopAreaId) {
		this.shopAreaId = shopAreaId;
	}
	
	public String getHistoryRemark() {
		return historyRemark;
	}
	public void setHistoryRemark(String historyRemark) {
		this.historyRemark = historyRemark;
	}
	
	public String getShopImg() {
		return shopImg;
	}
	public void setShopImg(String shopImg) {
		this.shopImg = shopImg;
	}
	
	public Float getShopAreas() {
		return shopAreas;
	}
	public void setShopAreas(Float shopAreas) {
		this.shopAreas = shopAreas;
	}
	
	public Integer getSignMode() {
		return signMode;
	}
	public void setSignMode(Integer signMode) {
		this.signMode = signMode;
	}
	
	public String getAgency() {
		return agency;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}
	
	public String getDescs() {
		return descs;
	}
	public void setDescs(String descs) {
		this.descs = descs;
	}
	

}
