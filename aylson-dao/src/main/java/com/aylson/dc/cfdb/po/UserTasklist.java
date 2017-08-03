package com.aylson.dc.cfdb.po;

import java.io.Serializable;

public class UserTasklist implements Serializable{
	
	private static final long serialVersionUID = -8485875038217760514L;

	private String id;			//主键
	private String phoneId;		//手机唯一标识码
	private String taskId;		//任务唯一ID
	private String logoUrl;		//任务logo url地址
	private String taskName;		//任务名称
	private String taskTag;		//任务标签
	private Integer amount;		//任务剩余数量
	private String income;		//用户收益金额
	private Integer orderNo;		//排序编号
	private String goUrl;		//跳转url地址
	private String status;		//任务状态
	private Integer statusFlag;	//任务状态标识
	private String createDate;	//用户抢购时间
	private String updateDate;	//提交审核时间
	private Integer isChecked;	//是否查看过
	private String proveDate;	//审核完成时间
	private Integer isFirstSuc;	//是否有过成功审核标识，1=否；2=是
	private String proveName;	//待审批姓名
	private String proveMobile;	//待审批手机号码
	private String proveImagesUrl;	//待审批图片
	private String proveColumn1;		//扩展字段1
	private String proveColumn2;		//扩展字段2
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPhoneId() {
		return phoneId;
	}
	public void setPhoneId(String phoneId) {
		this.phoneId = phoneId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskTag() {
		return taskTag;
	}
	public void setTaskTag(String taskTag) {
		this.taskTag = taskTag;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getIncome() {
		return income;
	}
	public void setIncome(String income) {
		this.income = income;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public String getGoUrl() {
		return goUrl;
	}
	public void setGoUrl(String goUrl) {
		this.goUrl = goUrl;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getStatusFlag() {
		return statusFlag;
	}
	public void setStatusFlag(Integer statusFlag) {
		this.statusFlag = statusFlag;
	}
	public Integer getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(Integer isChecked) {
		this.isChecked = isChecked;
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
	public String getProveDate() {
		return proveDate;
	}
	public void setProveDate(String proveDate) {
		this.proveDate = proveDate;
	}
	public Integer getIsFirstSuc() {
		return isFirstSuc;
	}
	public void setIsFirstSuc(Integer isFirstSuc) {
		this.isFirstSuc = isFirstSuc;
	}
	public String getProveName() {
		return proveName;
	}
	public void setProveName(String proveName) {
		this.proveName = proveName;
	}
	public String getProveMobile() {
		return proveMobile;
	}
	public void setProveMobile(String proveMobile) {
		this.proveMobile = proveMobile;
	}
	public String getProveImagesUrl() {
		return proveImagesUrl;
	}
	public void setProveImagesUrl(String proveImagesUrl) {
		this.proveImagesUrl = proveImagesUrl;
	}
	public String getProveColumn1() {
		return proveColumn1;
	}
	public void setProveColumn1(String proveColumn1) {
		this.proveColumn1 = proveColumn1;
	}
	public String getProveColumn2() {
		return proveColumn2;
	}
	public void setProveColumn2(String proveColumn2) {
		this.proveColumn2 = proveColumn2;
	}
}
