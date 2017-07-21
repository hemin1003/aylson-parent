package com.aylson.dc.agent.search;

import com.aylson.core.frame.search.BaseSearch;

public class VisitSignSearch extends BaseSearch {

	private static final long serialVersionUID = 475708148736449194L;
	
	//匹配查询
	private Integer id;             		//主键  
	private Integer agentId;            	//所属代理商id
	private String createTime;             	//创建时间 
	private String province;                //省会
	private Integer provinceId;             //省会id
	private String city;                 	//城市
	private Integer cityId;                 //城市id
	private String area;                 	//区域
	private Integer areaId;                 //区域id
	//模糊查询
	private String clientNameLike;          //客户姓名  
	private String mobilePhoneLike;         //客户电话
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	
	public String getClientNameLike() {
		return clientNameLike;
	}
	public void setClientNameLike(String clientNameLike) {
		this.clientNameLike = clientNameLike;
	}
	
	public String getMobilePhoneLike() {
		return mobilePhoneLike;
	}
	public void setMobilePhoneLike(String mobilePhoneLike) {
		this.mobilePhoneLike = mobilePhoneLike;
	}
	
	
}
