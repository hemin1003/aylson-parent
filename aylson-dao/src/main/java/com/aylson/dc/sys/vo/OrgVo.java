package com.aylson.dc.sys.vo;

import java.util.List;

import com.aylson.dc.sys.po.Org;

public class OrgVo extends Org {
	private static final long serialVersionUID = 5399707311609161588L;
	
	private String state="colsed";
	private Boolean ck;
	private List<OrgVo> children;
	private String text;               //conbotree显示

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
	
	public List<OrgVo> getChildren() {
		return children;
	}
	public void setChildren(List<OrgVo> children) {
		this.children = children;
	}
	
	public String getText() {
		return super.getOrgName();
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
	
	
}
