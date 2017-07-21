package com.aylson.dc.pioneer.search;

import com.aylson.core.frame.search.BaseSearch;

public class PioneerAgentSaleInfoSearch extends BaseSearch {

	private static final long serialVersionUID = -87350005451865853L;
	
	private Integer agentId;                         //代理商id
	private String year;                             //年份

	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	
}
