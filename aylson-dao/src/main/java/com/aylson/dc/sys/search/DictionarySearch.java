package com.aylson.dc.sys.search;

import com.aylson.core.frame.search.BaseSearch;

public class DictionarySearch extends BaseSearch {

	private static final long serialVersionUID = 7055314584366242417L;

	private Integer id;             //主键
	private Integer parentId;       //父ID
	private String dicName;         //字典名称
	private String dicValue;        //字典值
	private String dicType;         //字典类型
	private String dicGroup;        //所属组
	
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

	
}
