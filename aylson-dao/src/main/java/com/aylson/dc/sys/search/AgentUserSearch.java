package com.aylson.dc.sys.search;

import com.aylson.core.frame.search.BaseSearch;

public class AgentUserSearch extends BaseSearch {

	private static final long serialVersionUID = -3965663447464032051L;
	
	//匹配查询
	private Integer provinceId;        		 //省会id
	private Integer cityId;            		 //城市id
	private Integer areaId;            		 //区域id
	private Integer status;            		 //用户状态
	private Boolean isAgent;           		 //是否代理商：false:直营    true:非直营
	
	//模糊查询
	private String userNameLike;       		 //账号名称
	private String agentNameLike;      		 //代理商名称
	private String contactPhoneLike;   		 //联系人电话
	private String addressLike;              //地址
	
	
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
	
	public Boolean getIsAgent() {
		return isAgent;
	}
	public void setIsAgent(Boolean isAgent) {
		this.isAgent = isAgent;
	}
	
	public String getUserNameLike() {
		return userNameLike;
	}
	public void setUserNameLike(String userNameLike) {
		this.userNameLike = userNameLike;
	}
	
	public String getAgentNameLike() {
		return agentNameLike;
	}
	public void setAgentNameLike(String agentNameLike) {
		this.agentNameLike = agentNameLike;
	}
	
	public String getContactPhoneLike() {
		return contactPhoneLike;
	}
	public void setContactPhoneLike(String contactPhoneLike) {
		this.contactPhoneLike = contactPhoneLike;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getAddressLike() {
		return addressLike;
	}
	public void setAddressLike(String addressLike) {
		this.addressLike = addressLike;
	}
	
	
}
