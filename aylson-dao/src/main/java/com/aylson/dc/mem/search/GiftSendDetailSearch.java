package com.aylson.dc.mem.search;

import com.aylson.core.frame.search.BaseSearch;

public class GiftSendDetailSearch extends BaseSearch {

	private static final long serialVersionUID = 127100857750485188L;
	
	//匹配查询
	private Integer id;           		          //主键
	private String channel;           		      //获奖渠道
	private String accountPk;           		  //获奖主键：微信openId
	private String createTime;           		  //创建时间
	private String updateTime;           		  //更新时间
	//模糊查询
	private String consigneePhoneLike;            //收货人手机号码
	private String consigneeLike;                 //收货人姓名
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public String getAccountPk() {
		return accountPk;
	}
	public void setAccountPk(String accountPk) {
		this.accountPk = accountPk;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getConsigneePhoneLike() {
		return consigneePhoneLike;
	}
	public void setConsigneePhoneLike(String consigneePhoneLike) {
		this.consigneePhoneLike = consigneePhoneLike;
	}
	
	public String getConsigneeLike() {
		return consigneeLike;
	}
	public void setConsigneeLike(String consigneeLike) {
		this.consigneeLike = consigneeLike;
	}
	
	
}
