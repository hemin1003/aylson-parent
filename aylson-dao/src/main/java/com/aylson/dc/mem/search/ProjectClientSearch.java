package com.aylson.dc.mem.search;

import com.aylson.core.frame.search.BaseSearch;

public class ProjectClientSearch extends BaseSearch {

	private static final long serialVersionUID = 892961190187902869L;

	//匹配查询
	private Integer id;                         //主键
	private Integer memberId;                   //会员id
	private Integer clientId;                   //业主资料id
	private Integer sourceType;                 //来源类型：1代理商 2 工长
	private Integer status;                     //状态：0 已分配 2 已添加 3 已忽略
	private String createTime;                  //创建时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	
	public Integer getSourceType() {
		return sourceType;
	}
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
}
