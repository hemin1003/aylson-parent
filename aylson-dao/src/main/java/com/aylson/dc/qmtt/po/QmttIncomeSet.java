package com.aylson.dc.qmtt.po;

import java.io.Serializable;

public class QmttIncomeSet implements Serializable{
	
	private static final long serialVersionUID = 9110302105370862799L;
	
	private String id;			//主键
	private String income;		//提现金额
	private Integer status;		//记录状态，1=已下线，2=已上线
	private Integer saleFlag;	//销售标识，1=下线；2=上线；3=缺货
	private String createDate;	//创建时间
	private String createdBy;	//创建人
	private String updateDate;	//更新时间
	private String updatedBy;	//更新人
	
	public Integer getSaleFlag() {
		return saleFlag;
	}
	public void setSaleFlag(Integer saleFlag) {
		this.saleFlag = saleFlag;
	}
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
	public String getIncome() {
		return income;
	}
	public void setIncome(String income) {
		this.income = income;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
