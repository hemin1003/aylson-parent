package com.aylson.dc.sys.vo;

import java.util.Map;

import com.aylson.dc.sys.po.Product;

public class ProductVo extends Product {

	private static final long serialVersionUID = -2614282551052576898L;
	
	private Map<String, String> pictureInfo;       //图片展示信息:pictureShows:pictureDesc

	public Map<String, String> getPictureInfo() {
		return pictureInfo;
	}
	public void setPictureInfo(Map<String, String> pictureInfo) {
		this.pictureInfo = pictureInfo;
	}
	
	
}
