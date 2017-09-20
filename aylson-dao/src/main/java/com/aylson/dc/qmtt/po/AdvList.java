package com.aylson.dc.qmtt.po;

import java.io.Serializable;

/**
 * 广告来源配置表
 * @author Minbo
 */
public class AdvList implements Serializable{

	private static final long serialVersionUID = -6221382663816208139L;

	private String id;			//主键
	
	private String advFlag;		//唯一标识
	private String advName;		//渠道名称
	private Integer status;		//记录状态
	
	private String createDate;	//创建时间
	private String createdBy;	//创建人
	private String updateDate;	//更新时间
	private String updatedBy;	//更新人
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getAdvFlag() {
		return advFlag;
	}
	public void setAdvFlag(String advFlag) {
		this.advFlag = advFlag;
	}
	public String getAdvName() {
		return advName;
	}
	public void setAdvName(String advName) {
		this.advName = advName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
