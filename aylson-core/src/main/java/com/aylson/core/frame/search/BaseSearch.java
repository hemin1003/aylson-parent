package com.aylson.core.frame.search;

import java.io.Serializable;

public class BaseSearch implements Serializable {

	private static final long serialVersionUID = 9057824370152750360L;

	/**
	 * 当前页
	 */
	private int page = 1;
	/**
	 * 每页显示记录数
	 */
	private int rows = 10;
	/**
	 * 排序字段名
	 */
	private String sort = null; 
	/**
	 * 按什么排序(asc,desc)
	 */
	private String order = "asc";  
	/**
	 * 是否分页
	 */
	private Boolean isPage = false;
	/**
	 * mysql分页辅助
	 */
	private int offset;
	/**
	 * mysql分页辅助
	 */
	private int limit;
	
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
	public Boolean getIsPage() {
		return isPage;
	}
	public void setIsPage(Boolean isPage) {
		this.isPage = isPage;
	} 
	
	public int getOffset() {
		int offset = (this.page-1)*this.rows;
		if(offset < 0)offset = 0;
		return offset;
	}
	
	public int getLimit() {
		return this.rows;
	}
	
	

}
