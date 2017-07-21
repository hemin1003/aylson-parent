package com.aylson.dc.sys.search;

import com.aylson.core.frame.search.BaseSearch;

public class StaffSearch extends BaseSearch{

	private static final long serialVersionUID = -1746375474902543142L;

	
	//匹配查询
	private Integer id;                   //主键
	private Integer sourceType;           //来源类型：1 代理商 2内部人员
	private Integer sourceId;             //来源id
	private String source;                //来源：Agent,Org
	private Boolean isDisable;            //是否禁用，默认：否
	//模糊查询
	private String staffNameLike;         //员工姓名
	private String staffPhoneLike;        //员工电话
	
	
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
	
	public String getStaffNameLike() {
		return staffNameLike;
	}
	public void setStaffNameLike(String staffNameLike) {
		this.staffNameLike = staffNameLike;
	}
	
	public String getStaffPhoneLike() {
		return staffPhoneLike;
	}
	public void setStaffPhoneLike(String staffPhoneLike) {
		this.staffPhoneLike = staffPhoneLike;
	}
	
	public Boolean getIsDisable() {
		return isDisable;
	}
	public void setIsDisable(Boolean isDisable) {
		this.isDisable = isDisable;
	}
	
	
}
