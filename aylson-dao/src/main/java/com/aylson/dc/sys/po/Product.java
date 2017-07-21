package com.aylson.dc.sys.po;

import java.io.Serializable;

public class Product implements Serializable{

	private static final long serialVersionUID = -8645534043686917292L;
	
	private Integer id;             		//主键 
	private Integer seq;             		//序号
	private Integer category;             	//产品类别 
	private String categoryName;            //产品类别名称
	private String thumbnail;             	//缩略图
	private String productName;             //产品名称 
	private String productProp;             //产品特性
	private String application;             //适用范围 
	private String params;             		//技术参数
	private String pictureShows;            //图片展示
	private String pictureDesc;             //图片描述 
	private String videoTitle;             	//视频标题 
	private String videoThum;             	//视频图片 
	private String videoDesc;             	//视频描述 
	private String videoUrl;             	//视频地址 
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getProductProp() {
		return productProp;
	}
	public void setProductProp(String productProp) {
		this.productProp = productProp;
	}
	
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	
	public String getPictureShows() {
		return pictureShows;
	}
	public void setPictureShows(String pictureShows) {
		this.pictureShows = pictureShows;
	}
	
	public String getPictureDesc() {
		return pictureDesc;
	}
	public void setPictureDesc(String pictureDesc) {
		this.pictureDesc = pictureDesc;
	}
	
	public String getVideoTitle() {
		return videoTitle;
	}
	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}
	
	public String getVideoThum() {
		return videoThum;
	}
	public void setVideoThum(String videoThum) {
		this.videoThum = videoThum;
	}
	
	public String getVideoDesc() {
		return videoDesc;
	}
	public void setVideoDesc(String videoDesc) {
		this.videoDesc = videoDesc;
	}
	
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	

}
