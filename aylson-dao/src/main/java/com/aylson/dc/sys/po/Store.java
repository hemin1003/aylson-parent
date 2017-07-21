package com.aylson.dc.sys.po;

import java.io.Serializable;

public class Store implements Serializable{

	private static final long serialVersionUID = 3411307373301957389L;
	
	private Integer id;             		//主键  
	private String storeName;               //门店名称
	private String openTime;                //营业时间
	private String workPhone;               //工作电话
	private String address;                 //门店地址
	private String introduce;               //门店介绍
	private String thumbnail;               //缩略图
	private String storeImgs;               //门店图片展示
	private Float longitude;                //经度
	private Float latitude;                 //维度
	private Integer seq;             		//序号
	private Integer agentInfoId;            //代理商用户信息id
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	
	public String getWorkPhone() {
		return workPhone;
	}
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public String getStoreImgs() {
		return storeImgs;
	}
	public void setStoreImgs(String storeImgs) {
		this.storeImgs = storeImgs;
	}
	
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	public Integer getAgentInfoId() {
		return agentInfoId;
	}
	public void setAgentInfoId(Integer agentInfoId) {
		this.agentInfoId = agentInfoId;
	}
	

}
