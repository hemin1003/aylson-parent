package com.aylson.core.easyui;

import java.util.ArrayList;
import java.util.List;

/**
 * 后台向前台返回JSON，用于easyui的datagrid
 * 
 * 
 */
public class EasyuiDataGridJson implements java.io.Serializable {


	private static final long serialVersionUID = 1214473395947964632L;
	
	private Long total = 0l;              // 总记录数
	private List rows = new ArrayList();  // 行记录
	private List footer = new ArrayList();

	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}

	public List getFooter() {
		return footer;
	}
	public void setFooter(List footer) {
		this.footer = footer;
	}
	
	
}

