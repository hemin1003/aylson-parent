package com.aylson.dc.sys.search;

import com.aylson.core.frame.search.BaseSearch;

public class NoticeSearch extends BaseSearch{

	private static final long serialVersionUID = -5492369098434657574L;
	
	//匹配查询
	private Integer id;             		//主键  
	private Integer state;             		//状态  
	private Integer noticeObj;             	//通知对象：1：设计师 2：工长 3：开拓者 4：业主 5：艾臣合伙人  
	private String createTime;             	//创建时间
	private String publishTime;             //更新时间
	private Integer readerId;               //读取人id
	//模糊查询
	private String titleLike;               //标题
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	public Integer getNoticeObj() {
		return noticeObj;
	}
	public void setNoticeObj(Integer noticeObj) {
		this.noticeObj = noticeObj;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	
	public Integer getReaderId() {
		return readerId;
	}
	public void setReaderId(Integer readerId) {
		this.readerId = readerId;
	}
	
	public String getTitleLike() {
		return titleLike;
	}
	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}
	
	
}
