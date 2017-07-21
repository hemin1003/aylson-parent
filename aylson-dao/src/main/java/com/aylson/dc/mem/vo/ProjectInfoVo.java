package com.aylson.dc.mem.vo;

import java.util.List;

import com.aylson.dc.mem.po.ProjectFlowNode;
import com.aylson.dc.mem.po.ProjectInfo;
import com.aylson.dc.sys.po.Attachment;

public class ProjectInfoVo extends ProjectInfo {

	private static final long serialVersionUID = 3923135374949502719L;
	
	private  ProjectDesignVo projectDesignVo;   //针对客户资料的方案设计
	private String oper;           		        //操作人
	private Integer operId;           		    //操作人id 
	private String attachments;                 //附件地址合集，以,隔开
	private Integer attachmentType;             //附件类型：1 工程方案附件 2 工程设计附件
	private Float amount;                       //结算金额
	private Integer projectDesignId;            //方案设计id
	private String memberName;                  //会员名称
	private Boolean isOnlySave = true;          //是否只修改，不提交客户资料
	
	private String accountName;                 //账号
	private String realName;                    //姓名
	private Integer referralId;                 //推荐人id
	private String referralName;                //推荐人姓名
	private String memberPhone;                 //会员移动电话
	private String auditOpition;                //审核意见    
	private String channel;                     //渠道
	private Integer memberType;                 //会员类型：1设计师 2产业工人
	private String wxOpenId;                    //微信用户id
	private List<ProjectFlowNode> flowNodeList; //流程节点
	private String productTypes;           	    //产品类型
	private String productTypeIds;           	//产品类型ids
	private List<Attachment> successCaseList;   //成功案例展示图片
	private Integer evalScore;           		//评价分数
	private String evalContent;           		//评价内容
 
	public ProjectDesignVo getProjectDesignVo() {
		return projectDesignVo;
	}
	public void setProjectDesignVo(ProjectDesignVo projectDesignVo) {
		this.projectDesignVo = projectDesignVo;
	}
	
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	
	public Integer getOperId() {
		return operId;
	}
	public void setOperId(Integer operId) {
		this.operId = operId;
	}
	
	public String getAttachments() {
		return attachments;
	}
	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}
	
	public Integer getAttachmentType() {
		return attachmentType;
	}
	public void setAttachmentType(Integer attachmentType) {
		this.attachmentType = attachmentType;
	}
	
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	
	public Integer getProjectDesignId() {
		return projectDesignId;
	}
	public void setProjectDesignId(Integer projectDesignId) {
		this.projectDesignId = projectDesignId;
	}
	
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
	public Boolean getIsOnlySave() {
		return isOnlySave;
	}
	public void setIsOnlySave(Boolean isOnlySave) {
		this.isOnlySave = isOnlySave;
	}
	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public Integer getReferralId() {
		return referralId;
	}
	public void setReferralId(Integer referralId) {
		this.referralId = referralId;
	}
	
	public String getReferralName() {
		return referralName;
	}
	public void setReferralName(String referralName) {
		this.referralName = referralName;
	}
	
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	
	public String getAuditOpition() {
		return auditOpition;
	}
	public void setAuditOpition(String auditOpition) {
		this.auditOpition = auditOpition;
	}
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
	
	public String getWxOpenId() {
		return wxOpenId;
	}
	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}
	
	public List<ProjectFlowNode> getFlowNodeList() {
		return flowNodeList;
	}
	public void setFlowNodeList(List<ProjectFlowNode> flowNodeList) {
		this.flowNodeList = flowNodeList;
	}
	
	public String getProductTypes() {
		return productTypes;
	}
	public void setProductTypes(String productTypes) {
		this.productTypes = productTypes;
	}
	
	public String getProductTypeIds() {
		return productTypeIds;
	}
	public void setProductTypeIds(String productTypeIds) {
		this.productTypeIds = productTypeIds;
	}
	
	public List<Attachment> getSuccessCaseList() {
		return successCaseList;
	}
	public void setSuccessCaseList(List<Attachment> successCaseList) {
		this.successCaseList = successCaseList;
	}
	
	public Integer getEvalScore() {
		return evalScore;
	}
	public void setEvalScore(Integer evalScore) {
		this.evalScore = evalScore;
	}
	
	public String getEvalContent() {
		return evalContent;
	}
	public void setEvalContent(String evalContent) {
		this.evalContent = evalContent;
	}
	
	
}
