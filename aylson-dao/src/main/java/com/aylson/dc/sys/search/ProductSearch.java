package com.aylson.dc.sys.search;

import com.aylson.core.frame.search.BaseSearch;

public class ProductSearch extends BaseSearch{

	private static final long serialVersionUID = -6529524897612779439L;
	
	//匹配查询
	private Integer id;             		//主键
	private Integer category;             	//产品类别 
	//模糊查询
	private String productNameLike;         //产品名称
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	
	public String getProductNameLike() {
		return productNameLike;
	}
	public void setProductNameLike(String productNameLike) {
		this.productNameLike = productNameLike;
	}
	

}
