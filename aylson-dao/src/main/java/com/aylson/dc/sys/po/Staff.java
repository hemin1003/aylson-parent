package com.aylson.dc.sys.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Staff implements Serializable{

	private static final long serialVersionUID = -4752723837176502039L;

	private Integer id;                   //主键
	private Integer sourceType;           //来源类型：1 代理商 2内部人员
	private Integer sourceId;             //来源id
	private String source;                //来源：Agent,Org
	private String staffName;             //员工姓名
	private String staffPhone;            //员工电话
	private Boolean isDisable;            //是否禁用，默认：否
	private String remark;                //备注
	private Date createTime;              //创建时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	
	public String getStaffPhone() {
		return staffPhone;
	}
	public void setStaffPhone(String staffPhone) {
		this.staffPhone = staffPhone;
	}
	
	public Boolean getIsDisable() {
		return isDisable;
	}
	public void setIsDisable(Boolean isDisable) {
		this.isDisable = isDisable;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
