package com.aylson.dc.mem.search;

import com.aylson.core.frame.search.BaseSearch;

public class MemWalletDetailSearch extends BaseSearch {

	private static final long serialVersionUID = 3264153299345637991L;
	
	//匹配查询
	private Integer id;           		  //主键
	private Integer memberId;          	  //会员id
	private Integer type;           	  //进出类别
	private Integer sourceType;           //来源类型
	private Integer sourceId;             //来源id
	
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
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	
	
}
