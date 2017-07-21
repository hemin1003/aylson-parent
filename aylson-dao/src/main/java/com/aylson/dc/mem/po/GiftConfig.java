package com.aylson.dc.mem.po;

import java.io.Serializable;

public class GiftConfig implements Serializable {
	
	
	private static final long serialVersionUID = 4940978634417960062L;
	
	private Integer id;           		          //主键
	private String giftName;           		      //礼品名称
	private String giftCode;           		      //礼品编码
	private String imgUrl;           		      //imgUrl
	private Integer integral;           		  //所需积分
	private String description;           		  //礼品描述
	private Integer status;           		      //状态:1创建 2上架 3下架
	private String parameters;                    //规格参数
	private String imgNavigation;                 //礼品图片导航
	private Integer matchObj;                     //适用兑换对象:0全部 1 设计师 2工长 3 开拓者联盟
	private Integer matchActivityId;              //适用活动类型：0默认全部适用 ,关联数据字典
	private String  matchActivity;                //适用活动类型：0默认全部适用 ,关联数据字典
	private String matchActivityCode;             //适用活动类型：0默认全部适用 ,关联数据字典
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getGiftName() {
		return giftName;
	}
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	
	public String getGiftCode() {
		return giftCode;
	}
	public void setGiftCode(String giftCode) {
		this.giftCode = giftCode;
	}
	
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	
	public String getImgNavigation() {
		return imgNavigation;
	}
	public void setImgNavigation(String imgNavigation) {
		this.imgNavigation = imgNavigation;
	}
	
	public Integer getMatchObj() {
		return matchObj;
	}
	public void setMatchObj(Integer matchObj) {
		this.matchObj = matchObj;
	}
	
	public Integer getMatchActivityId() {
		return matchActivityId;
	}
	public void setMatchActivityId(Integer matchActivityId) {
		this.matchActivityId = matchActivityId;
	}
	
	public String getMatchActivity() {
		return matchActivity;
	}
	public void setMatchActivity(String matchActivity) {
		this.matchActivity = matchActivity;
	}
	
	public String getMatchActivityCode() {
		return matchActivityCode;
	}
	public void setMatchActivityCode(String matchActivityCode) {
		this.matchActivityCode = matchActivityCode;
	}
	
	
}
