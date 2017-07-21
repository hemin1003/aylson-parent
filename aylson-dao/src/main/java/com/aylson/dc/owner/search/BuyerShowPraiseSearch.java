package com.aylson.dc.owner.search;

import com.aylson.core.frame.search.BaseSearch;

public class BuyerShowPraiseSearch extends BaseSearch {

	
	private static final long serialVersionUID = 1855720012967900325L;
	
	//匹配查询
	private Integer id;                      //主键
	private Integer buyershowId;             //买家秀id
	private String operater;                 //操作人
	//模糊查询
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getBuyershowId() {
		return buyershowId;
	}
	public void setBuyershowId(Integer buyershowId) {
		this.buyershowId = buyershowId;
	}
	
	public String getOperater() {
		return operater;
	}
	public void setOperater(String operater) {
		this.operater = operater;
	}
	
	
}
