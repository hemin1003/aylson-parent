package com.aylson.dc.mem.search;

import com.aylson.core.frame.search.BaseSearch;

public class GiftExchangeRecordsSearch extends BaseSearch {

	private static final long serialVersionUID = -7501416479301116147L;
	
	//匹配查询
	private Integer id;           		        //主键
	private Integer exchangerId;           		//兑换人id
	private String exchangeTime;                //兑换时间
	private Integer matchObj;                   //适用兑换对象: 1 设计师 2工长 3 开拓者联盟

	//模糊查询
	private String exchangeCodeLike;           	//兑换码：6位,唯一
	private String exchangePhoneLike;           //兑换人手机号
	private String exchangerLike;           	//兑换人
	private String giftNameLike;           		//礼品名称
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getExchangerId() {
		return exchangerId;
	}
	public void setExchangerId(Integer exchangerId) {
		this.exchangerId = exchangerId;
	}
	
	public String getExchangeTime() {
		return exchangeTime;
	}
	public void setExchangeTime(String exchangeTime) {
		this.exchangeTime = exchangeTime;
	}
	
	public Integer getMatchObj() {
		return matchObj;
	}
	public void setMatchObj(Integer matchObj) {
		this.matchObj = matchObj;
	}
	
	public String getExchangeCodeLike() {
		return exchangeCodeLike;
	}
	public void setExchangeCodeLike(String exchangeCodeLike) {
		this.exchangeCodeLike = exchangeCodeLike;
	}
	
	public String getExchangePhoneLike() {
		return exchangePhoneLike;
	}
	public void setExchangePhoneLike(String exchangePhoneLike) {
		this.exchangePhoneLike = exchangePhoneLike;
	}
	
	public String getExchangerLike() {
		return exchangerLike;
	}
	public void setExchangerLike(String exchangerLike) {
		this.exchangerLike = exchangerLike;
	}
	
	public String getGiftNameLike() {
		return giftNameLike;
	}
	public void setGiftNameLike(String giftNameLike) {
		this.giftNameLike = giftNameLike;
	}
	
	
}
