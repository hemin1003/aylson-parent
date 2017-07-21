package com.aylson.dc.mem.po;

import java.io.Serializable;

public class ProjectDesign implements Serializable {
	
	private static final long serialVersionUID = 1088776245811122983L;
	
	private Integer id;           		          //主键
	private Integer projectId;           		  //工程id
	private String description;           		  //设计需求说明
	private String productTypes;           	      //产品类型
	private String productTypeIds;           	  //产品类型ids
	private String autoSys;           		      //智能系统
	private String autoSysIds;           		  //智能系统ids
	private Boolean isPay;           		      //是否已扣费
	private Integer evalScore;           		  //评价分数
	private String evalContent;           		  //评价内容
	private String remark;           		      //备注
	private Float  amount;           		      //结算金额
	private Integer gold;           		      //花费金币值
	
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
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	
	public Integer getEvalScore() {
		return evalScore;
	}
	public void setEvalScore(Integer evalScore) {
		this.evalScore = evalScore;
	}
	
	public String getEvalContent() {
		return evalContent;
	}
	public void setEvalContent(String evalContent) {
		this.evalContent = evalContent;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	
	public Integer getGold() {
		return gold;
	}
	public void setGold(Integer gold) {
		this.gold = gold;
	}
	
	
}
