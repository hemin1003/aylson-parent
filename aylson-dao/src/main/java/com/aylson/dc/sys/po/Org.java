package com.aylson.dc.sys.po;

import java.io.Serializable;

public class Org implements Serializable {
	
	private static final long serialVersionUID = 1524961112742821837L;
	
	private Integer id;           //主键
	private Integer parentId;     //父id
	private Integer seq;          //序号
	private String orgName;       //组织名称
	private String orgCode;       //组织编号
	private Integer status;       //状态:1使用 2删除
	private String remark;        //备注
	private Integer levelNum;     //级别（辅助字段）
	private Boolean isLeaf;       //是否叶子（辅助字段）
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Integer getLevelNum() {
		return levelNum;
	}
	public void setLevelNum(Integer levelNum) {
		this.levelNum = levelNum;
	}
	
	public Boolean getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	
}
