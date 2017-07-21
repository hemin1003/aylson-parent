package com.aylson.dc.mem.search;

import com.aylson.core.frame.search.BaseSearch;

public class ProjectDesignSearch extends BaseSearch {

	private static final long serialVersionUID = -7922978738155076586L;

	private Integer id;           		          //主键
	private Integer projectId;           		  //工程id
	private String productTypes;           	      //产品类型
	private String productTypeIds;           	  //产品类型ids
	private String autoSys;           		      //智能系统
	private String autoSysIds;           		  //智能系统ids
	private Boolean isPay;           		      //是否已扣费
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	public String getProductTypes() {
		return productTypes;
	}
	public void setProductTypes(String productTypes) {
		this.productTypes = productTypes;
	}
	
	public String getProductTypeIds() {
		return productTypeIds;
	}
	public void setProductTypeIds(String productTypeIds) {
		this.productTypeIds = productTypeIds;
	}
	
	public String getAutoSys() {
		return autoSys;
	}
	public void setAutoSys(String autoSys) {
		this.autoSys = autoSys;
	}
	
	public String getAutoSysIds() {
		return autoSysIds;
	}
	public void setAutoSysIds(String autoSysIds) {
		this.autoSysIds = autoSysIds;
	}
	
	public Boolean getIsPay() {
		return isPay;
	}
	public void setIsPay(Boolean isPay) {
		this.isPay = isPay;
	}
	
	
}
