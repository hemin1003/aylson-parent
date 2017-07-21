package com.aylson.dc.sys.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Attachment implements Serializable{

	private static final long serialVersionUID = -5625316442883744667L;
	
	private Integer id;                      //主键
	private Integer type;                    //来源类型
	private Integer sourceId;                //来源id
	private String attachAddress;            //附件地址
	private String attachName;               //附件名称
	private Date createTime;                 //创建时间
	private String remark;                   //备注
	private Integer status;                  //状态（0:作废，1:有效 ）
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
	
	public String getAttachAddress() {
		return attachAddress;
	}
	public void setAttachAddress(String attachAddress) {
		this.attachAddress = attachAddress;
	}
	
	public String getAttachName() {
		return attachName;
	}
	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
