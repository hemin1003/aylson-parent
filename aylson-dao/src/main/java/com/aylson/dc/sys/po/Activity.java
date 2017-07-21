package com.aylson.dc.sys.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Activity implements Serializable{

	private static final long serialVersionUID = 242447528831983079L;

	private Integer id;                      //主键  
	private String actTheme;                 //活动主题
	private Date actBeginTime;               //活动开始时间
	private Date actEndTime;                 //活动结束时间
	private String actPlace;                 //活动地点
	private Integer actNums;                 //活动人数
	private String actIntroduce;             //活动介绍
	private String sponsorPhone;             //主办方联系电话
	private Integer state;                   //状态：1新建 2发布 3结束
	private String thumb;                    //活动缩略图
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getActTheme() {
		return actTheme;
	}
	public void setActTheme(String actTheme) {
		this.actTheme = actTheme;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getActBeginTime() {
		return actBeginTime;
	}
	public void setActBeginTime(Date actBeginTime) {
		this.actBeginTime = actBeginTime;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getActEndTime() {
		return actEndTime;
	}
	public void setActEndTime(Date actEndTime) {
		this.actEndTime = actEndTime;
	}
	
	public String getActPlace() {
		return actPlace;
	}
	public void setActPlace(String actPlace) {
		this.actPlace = actPlace;
	}
	
	public Integer getActNums() {
		return actNums;
	}
	public void setActNums(Integer actNums) {
		this.actNums = actNums;
	}
	
	public String getActIntroduce() {
		return actIntroduce;
	}
	public void setActIntroduce(String actIntroduce) {
		this.actIntroduce = actIntroduce;
	}
	
	public String getSponsorPhone() {
		return sponsorPhone;
	}
	public void setSponsorPhone(String sponsorPhone) {
		this.sponsorPhone = sponsorPhone;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	

}
