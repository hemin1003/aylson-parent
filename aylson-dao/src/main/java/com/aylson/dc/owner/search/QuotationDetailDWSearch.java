package com.aylson.dc.owner.search;

import com.aylson.core.frame.search.BaseSearch;

public class QuotationDetailDWSearch extends BaseSearch {

	private static final long serialVersionUID = 4166732089430318352L;
	
	//匹配查询
	private Integer id;                      //主键  
	private Integer appointId;               //预约id
	private Integer quotationId;             //报价单id
	//模糊查询
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getAppointId() {
		return appointId;
	}
	public void setAppointId(Integer appointId) {
		this.appointId = appointId;
	}
	
	public Integer getQuotationId() {
		return quotationId;
	}
	public void setQuotationId(Integer quotationId) {
		this.quotationId = quotationId;
	}
	
	
}
