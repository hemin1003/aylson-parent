package com.aylson.dc.sys.search;

import com.aylson.core.frame.search.BaseSearch;

public class StoreSearch extends BaseSearch{

	private static final long serialVersionUID = 3500114741854068281L;

	//匹配查询
	private Integer id;             		//主键
	private Integer agentInfoId;            //代理商用户信息id
	private Integer cityId;                 //所属城市
	//模糊查询
	private String storeNameLike;           //门店名称
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getStoreNameLike() {
		return storeNameLike;
	}
	public void setStoreNameLike(String storeNameLike) {
		this.storeNameLike = storeNameLike;
	}
	
	public Integer getAgentInfoId() {
		return agentInfoId;
	}
	public void setAgentInfoId(Integer agentInfoId) {
		this.agentInfoId = agentInfoId;
	}
	
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	

}
