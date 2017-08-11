package com.aylson.dc.cfdb.search;

import com.aylson.core.frame.search.BaseSearch;

public class IncomeHisSearch extends BaseSearch {

	private static final long serialVersionUID = -2693649003991624099L;
	
	//匹配查询
	private Integer channel;		//收益渠道
	// 模糊查询
	private String phoneIdLike; // 手机唯一标识码

	public String getPhoneIdLike() {
		return phoneIdLike;
	}
	public void setPhoneIdLike(String phoneIdLike) {
		this.phoneIdLike = phoneIdLike;
	}
	public Integer getChannel() {
		return channel;
	}
	public void setChannel(Integer channel) {
		this.channel = channel;
	}
}
