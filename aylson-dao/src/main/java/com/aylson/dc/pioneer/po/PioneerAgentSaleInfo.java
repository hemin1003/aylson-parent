package com.aylson.dc.pioneer.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class PioneerAgentSaleInfo implements Serializable{

	private static final long serialVersionUID = -7278778778394473203L;
	
	
	private Integer id;                              //主键
	private Integer agentId;                         //代理商id
	private String year;                             //年份
	private Float sales;                             //销售额
	private Float rebate;                            //回扣
	private String remark;                           //备注
	private Integer status;                          //状态
	private Date createTime;                         //创建时间
	private String creater;                          //创建人
	private Integer createId;                        //创建人id
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	public Float getSales() {
		return sales;
	}
	public void setSales(Float sales) {
		this.sales = sales;
	}
	
	public Float getRebate() {
		return rebate;
	}
	public void setRebate(Float rebate) {
		this.rebate = rebate;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	
	public Integer getCreateId() {
		return createId;
	}
	public void setCreateId(Integer createId) {
		this.createId = createId;
	}

	
}
