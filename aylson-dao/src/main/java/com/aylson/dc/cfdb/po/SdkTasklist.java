package com.aylson.dc.cfdb.po;

import java.io.Serializable;

public class SdkTasklist implements Serializable{

	private static final long serialVersionUID = 6292325208627176584L;
	
	private String adid;		//广告ID
	private String cid;		//广告主ID
	private String intro;	//应用简介
	private String url;		//广告APK下载地址
	private String icon;		//icon图标地址
	private String psize;	//app包大小
	private String title;	//广告名称
	private String text1;	//简介下载量
	private String text2;	//简介
	private String android_url;	//任务步骤描述
	private String active_time;	//广告激活时间	
	private String runtime;		//运行时间
	private String curr_note;	//当前步骤激活条件
	private String active_num;	//激活次数
	private String score;		//任务总积分
	private String createDate;	//创建时间
	private String updateDate;	//更新时间
	private String sdkType;		//广告来源类型
	
	public String getSdkType() {
		return sdkType;
	}
	public void setSdkType(String sdkType) {
		this.sdkType = sdkType;
	}
	
	public String getAdid() {
		return adid;
	}
	public void setAdid(String adid) {
		this.adid = adid;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getPsize() {
		return psize;
	}
	public void setPsize(String psize) {
		this.psize = psize;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText1() {
		return text1;
	}
	public void setText1(String text1) {
		this.text1 = text1;
	}
	public String getText2() {
		return text2;
	}
	public void setText2(String text2) {
		this.text2 = text2;
	}
	public String getAndroid_url() {
		return android_url;
	}
	public void setAndroid_url(String android_url) {
		this.android_url = android_url;
	}
	public String getActive_time() {
		return active_time;
	}
	public void setActive_time(String active_time) {
		this.active_time = active_time;
	}
	public String getRuntime() {
		return runtime;
	}
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}
	public String getCurr_note() {
		return curr_note;
	}
	public void setCurr_note(String curr_note) {
		this.curr_note = curr_note;
	}
	public String getActive_num() {
		return active_num;
	}
	public void setActive_num(String active_num) {
		this.active_num = active_num;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
}
