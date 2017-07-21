package com.aylson.dc.sys.search;

import com.aylson.core.frame.search.BaseSearch;

public class MemoSearch extends BaseSearch{

	private static final long serialVersionUID = -5492369098434657574L;
	
	//匹配查询
	private Integer id;             		//主键  
	private Integer userId;                 //备忘人id  
	private Integer memoLevel;             	//备忘级别  
	private String createTime;              //创建时间  
	private String createTimeStart;         //创建时间-开始 
	private String createTimeEnd;           //创建时间-结束
	private String memoTime;                //备忘时间  
	private String memoTimeStart;           //备忘时间-开始 
	private String cmemoTimeEnd;            //备忘时间-结束
	private String memoTimeGt;              //备忘时间-大于
	//模糊查询
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Integer getMemoLevel() {
		return memoLevel;
	}
	public void setMemoLevel(Integer memoLevel) {
		this.memoLevel = memoLevel;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getCreateTimeStart() {
		return createTimeStart;
	}
	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}
	
	public String getCreateTimeEnd() {
		return createTimeEnd;
	}
	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	
	public String getMemoTime() {
		return memoTime;
	}
	public void setMemoTime(String memoTime) {
		this.memoTime = memoTime;
	}
	
	public String getMemoTimeStart() {
		return memoTimeStart;
	}
	public void setMemoTimeStart(String memoTimeStart) {
		this.memoTimeStart = memoTimeStart;
	}
	
	public String getCmemoTimeEnd() {
		return cmemoTimeEnd;
	}
	public void setCmemoTimeEnd(String cmemoTimeEnd) {
		this.cmemoTimeEnd = cmemoTimeEnd;
	}
	
	public String getMemoTimeGt() {
		return memoTimeGt;
	}
	public void setMemoTimeGt(String memoTimeGt) {
		this.memoTimeGt = memoTimeGt;
	}
	
	
}
