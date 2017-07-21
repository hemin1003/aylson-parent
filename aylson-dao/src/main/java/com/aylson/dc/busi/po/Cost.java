package com.aylson.dc.busi.po;

import java.io.Serializable;

public class Cost implements Serializable{

	private static final long serialVersionUID = -7280012400304236581L;
	
	private Integer id;             		//主键  
	private Integer costType;             	//费用类型  
	private String costName;             	//费用名称  
	private Float costValue;             	//费用明细值
	private String desc;             		//描述说明
	private Integer sourceType;             //项目来源类型 
	private Integer sourceId;             	//项目来源id 
	private String remark;             		//备注
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getCostType() {
		return costType;
	}
	public void setCostType(Integer costType) {
		this.costType = costType;
	}
	
	public String getCostName() {
		return costName;
	}
	public void setCostName(String costName) {
		this.costName = costName;
	}
	
	public Float getCostValue() {
		return costValue;
	}
	public void setCostValue(Float costValue) {
		this.costValue = costValue;
	}
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public Integer getSourceType() {
		return sourceType;
	}
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}
	
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
