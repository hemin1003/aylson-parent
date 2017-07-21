package com.aylson.dc.sys.search;

import com.aylson.core.frame.search.BaseSearch;

public class AttachmentSearch extends BaseSearch {

	private static final long serialVersionUID = -5403475524940813905L;
	
	//匹配查询
	private Integer id;                      //主键
	private Integer type;                    //来源类型
	private Integer sourceId;                //来源id
	private Integer status;                  //状态（0:作废，1:有效，）
	private Boolean isLock;                  //是否锁定，默认为：false不锁定
	private Boolean isEffect;                //是否有效，默认为：true 有效 
	private Integer sourceType;              //来源信息表
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Boolean getIsLock() {
		return isLock;
	}
	public void setIsLock(Boolean isLock) {
		this.isLock = isLock;
	}
	
	public Boolean getIsEffect() {
		return isEffect;
	}
	public void setIsEffect(Boolean isEffect) {
		this.isEffect = isEffect;
	}
	
	public Integer getSourceType() {
		return sourceType;
	}
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}
	
	
}
