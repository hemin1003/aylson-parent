package com.aylson.dc.mem.search;

import com.aylson.core.frame.search.BaseSearch;

public class GiftConfigSearch extends BaseSearch {

	private static final long serialVersionUID = 3090695213137828628L;
	
	//匹配查询
	private Integer id;           		          //主键
	private Integer status;           		      //状态
	private Integer matchObj;                     //适用兑换对象:0全部 1 设计师 2工长 3 开拓者联盟
	private Boolean containAll;                   //是否包含全部的
	private String matchActivityCode;             //适用活动类型：0默认全部适用 ,关联数据字典
	private String[] Ids;                         //主键集合
	//模糊查询
	private String giftNameLike;                  //礼品名称
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getMatchObj() {
		return matchObj;
	}
	public void setMatchObj(Integer matchObj) {
		this.matchObj = matchObj;
	}
	
	public Boolean getContainAll() {
		return containAll;
	}
	public void setContainAll(Boolean containAll) {
		this.containAll = containAll;
	}
	
	public String getGiftNameLike() {
		return giftNameLike;
	}
	public void setGiftNameLike(String giftNameLike) {
		this.giftNameLike = giftNameLike;
	}
	
	public String getMatchActivityCode() {
		return matchActivityCode;
	}
	public void setMatchActivityCode(String matchActivityCode) {
		this.matchActivityCode = matchActivityCode;
	}
	
	public String[] getIds() {
		return Ids;
	}
	public void setIds(String[] ids) {
		Ids = ids;
	}
	
	
}
