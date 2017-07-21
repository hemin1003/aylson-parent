package com.aylson.core.frame.base;

import java.util.ArrayList;
import java.util.List;

public class Page<T> implements java.io.Serializable{
	
	private static final long serialVersionUID = 2671449865087995341L;
	
	private	long total = 0l;                           //总条数
	private	long totalPage = 0l;                       //总页数
	private	int page = 1;                              //当前页
	private	int rows = 10;                             //每页显示几行
	private	List<T> rowsObject = new ArrayList<T>();   //每页对象集
	
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	
	public long getTotalPage() {
		if(this.rows <= 0)return 0;
		if(this.total%this.rows == 0) return this.total/this.rows;
		return this.total/this.rows+1;
	}
	
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
	
	public List<T> getRowsObject() {
		return rowsObject;
	}
	public void setRowsObject(List<T> rowsObject) {
		this.rowsObject = rowsObject;
	}
	
		
}
