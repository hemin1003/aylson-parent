package com.aylson.dc.mem.search;

import com.aylson.core.frame.search.BaseSearch;

public class WxShareSearch extends BaseSearch {

	
	private static final long serialVersionUID = -2058848109917137421L;
	//匹配查询
	private Integer id;           		      //主键
	private String inviterWxOpenId;           //分享人openId
	private String wxOpenId;           		  //邀请人openId
	private String channel;           		  //渠道
	private Integer sourceId;                 //来源id
	private Integer type;           		  //分享关系类型
	//模糊查询
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getInviterWxOpenId() {
		return inviterWxOpenId;
	}
	public void setInviterWxOpenId(String inviterWxOpenId) {
		this.inviterWxOpenId = inviterWxOpenId;
	}
	
	public String getWxOpenId() {
		return wxOpenId;
	}
	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
}
