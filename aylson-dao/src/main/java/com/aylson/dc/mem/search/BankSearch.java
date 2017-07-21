package com.aylson.dc.mem.search;

import com.aylson.core.frame.search.BaseSearch;

public class BankSearch extends BaseSearch {

	private static final long serialVersionUID = 2109708179682264602L;

	//匹配查询
	private Integer id;                  //主键
	private Integer memberId;            //会员id
	
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
	
	
}
