package com.aylson.dc.sys.search;

import com.aylson.core.frame.search.BaseSearch;

public class ProductIntentSearch extends BaseSearch{

	private static final long serialVersionUID = -5492369098434657574L;
	
	//匹配查询
	private Integer id;             		   		//主键  
	private Integer productType;          	        //产品类型：1安全门窗 2健康厨房
	private Integer state;                			//状态：0新建 1已回访
	private String createTime;                		//创建时间
	private String updateTime;               		//更新时间
	//模糊查询
	private String nameLike;              			//姓名
	private String mobilePhoneLike;               	//手机号码
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
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
	
	
}
