package com.aylson.dc.mem.vo;

import java.util.List;

import com.aylson.dc.mem.po.ProjectDesign;
import com.aylson.dc.sys.po.Attachment;
import com.aylson.dc.sys.vo.DictionaryVo;

public class ProjectDesignVo extends ProjectDesign {

	private static final long serialVersionUID = -1004280322038500470L;
	
	private List<DictionaryVo> productTypesList;//产品类型列表
	private List<DictionaryVo> autoSysList;     //智能系统列表
	private List<Attachment> requirementList;   //工程方案需求附件
	private List<Attachment> designList;        //工程设计附件
	private Boolean isOnlySave = true;          //是否只保存，不提交 
	private String attachments;                 //附件地址:辅助
	private String channel = "官网";             //渠道:辅助
	
	public List<DictionaryVo> getProductTypesList() {
		return productTypesList;
	}
	public void setProductTypesList(List<DictionaryVo> productTypesList) {
		this.productTypesList = productTypesList;
	}
	
	public List<DictionaryVo> getAutoSysList() {
		return autoSysList;
	}
	public void setAutoSysList(List<DictionaryVo> autoSysList) {
		this.autoSysList = autoSysList;
	}
	
	public Boolean getIsOnlySave() {
		return isOnlySave;
	}
	public void setIsOnlySave(Boolean isOnlySave) {
		this.isOnlySave = isOnlySave;
	}
	
	public List<Attachment> getRequirementList() {
		return requirementList;
	}
	public void setRequirementList(List<Attachment> requirementList) {
		this.requirementList = requirementList;
	}
	
	public List<Attachment> getDesignList() {
		return designList;
	}
	public void setDesignList(List<Attachment> designList) {
		this.designList = designList;
	}
	
	public String getAttachments() {
		return attachments;
	}
	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	

}
