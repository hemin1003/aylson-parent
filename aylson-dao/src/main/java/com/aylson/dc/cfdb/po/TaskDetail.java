package com.aylson.dc.cfdb.po;

import java.io.Serializable;

public class TaskDetail implements Serializable{
	
	private static final long serialVersionUID = -852498377417781207L;
	
	private String taskId;	//任务唯一ID
	private String taskDesc;	//任务详情
	private String stepUrl;	//详情步骤图Url
	private String steps;	//动态步骤图组合字段
	private String fields;	//动态验证组合字段
	private Integer isUpload;	//是否上传图片
	private Integer imagesNum;	//上传图片数量
	private String createDate;
	private String updateDate;
	private String createdBy;
	private String updatedBy;
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskDesc() {
		return taskDesc;
	}
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
	public String getStepUrl() {
		return stepUrl;
	}
	public void setStepUrl(String stepUrl) {
		this.stepUrl = stepUrl;
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
	public String getSteps() {
		return steps;
	}
	public void setSteps(String steps) {
		this.steps = steps;
	}
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	public Integer getIsUpload() {
		return isUpload;
	}
	public void setIsUpload(Integer isUpload) {
		this.isUpload = isUpload;
	}
	public Integer getImagesNum() {
		return imagesNum;
	}
	public void setImagesNum(Integer imagesNum) {
		this.imagesNum = imagesNum;
	}
}
