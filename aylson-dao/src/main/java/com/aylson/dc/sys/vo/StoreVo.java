package com.aylson.dc.sys.vo;

import java.util.List;

import com.aylson.dc.sys.po.Store;

public class StoreVo extends Store {

	private static final long serialVersionUID = 4122099033294081892L;
	
	private List<String> storeImgList;        //门店图片地址集合

	public List<String> getStoreImgList() {
		return storeImgList;
	}
	public void setStoreImgList(List<String> storeImgList) {
		this.storeImgList = storeImgList;
	}

	
	
}
