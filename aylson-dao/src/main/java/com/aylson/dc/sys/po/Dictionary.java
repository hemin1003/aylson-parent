package com.aylson.dc.sys.po;

import java.io.Serializable;

public class Dictionary implements Serializable {
	
private static final long serialVersionUID = 3660184499862071843L;
	
	private Integer id;             //主键
	private Integer parentId;       //父ID
	private Integer seq;            //序号
	private String dicName;         //字典名称
	private String dicValue;        //字典值
	private String dicType;         //字典类型
	private String dicGroup;        //所属组
	private String remark;          //备注说明
	private Integer levelNum;       //级别：辅助字段
	private Boolean isLeaf;         //是否叶子：辅助字段
	private Boolean isVisible;      //是否可见，用于控制显示列表

	
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

	public String getDicName() {
		return dicName;
	}
	public void setDicName(String dicName) {
		this.dicName = dicName;
	}

	public String getDicValue() {
		return dicValue;
	}
	public void setDicValue(String dicValue) {
		this.dicValue = dicValue;
	}

	public String getDicType() {
		return dicType;
	}
	public void setDicType(String dicType) {
		this.dicType = dicType;
	}

	public String getDicGroup() {
		return dicGroup;
	}
	public void setDicGroup(String dicGroup) {
		this.dicGroup = dicGroup;
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
	
	public Boolean getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}
	
}
