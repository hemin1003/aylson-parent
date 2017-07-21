package com.aylson.dc.sys.vo;

import com.aylson.dc.sys.po.Dictionary;

public class DictionaryVo extends Dictionary {

	private static final long serialVersionUID = -6700657930845530836L;
	
	private String state="colsed";
	private Boolean ck = false;     //是否选中

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public Boolean getCk() {
		return ck;
	}
	public void setCk(Boolean ck) {
		this.ck = ck;
	}
	
	
}
