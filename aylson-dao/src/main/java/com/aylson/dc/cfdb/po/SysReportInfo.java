package com.aylson.dc.cfdb.po;

import java.io.Serializable;

public class SysReportInfo implements Serializable{

	private static final long serialVersionUID = -5505821094931547023L;
	
	private String name;
	private String value;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
