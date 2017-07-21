package com.aylson.dc.owner.search;

import com.aylson.core.frame.search.BaseSearch;

public class OwnerInfoSearch extends BaseSearch {

	private static final long serialVersionUID = -757638120801918036L;
	
	//匹配查询
	private Integer createrId;                   //创建人用户id
	private Integer sourceType;                  //客户来源：1 预约订单  2 后台新增
	//模糊查询
	private String nameLike;                     //客户姓名
	private String mobilePhoneLike;              //客户电话
	private String nameOrPhoneLike;              //名称或电话
	
	
	public Integer getCreaterId() {
		return createrId;
	}
	public void setCreaterId(Integer createrId) {
		this.createrId = createrId;
	}
	
	public String getNameLike() {
		return nameLike;
	}
	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}
	
	public String getMobilePhoneLike() {
		return mobilePhoneLike;
	}
	public void setMobilePhoneLike(String mobilePhoneLike) {
		this.mobilePhoneLike = mobilePhoneLike;
	}
	
	public String getNameOrPhoneLike() {
		return nameOrPhoneLike;
	}
	public void setNameOrPhoneLike(String nameOrPhoneLike) {
		this.nameOrPhoneLike = nameOrPhoneLike;
	}
	
	public Integer getSourceType() {
		return sourceType;
	}
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}
	
	
}
