package com.aylson.dc.mem.vo;

import com.aylson.dc.mem.po.ArticleRead;

public class ArticleReadVo extends ArticleRead {

	private static final long serialVersionUID = -7843319147617430903L;
	
	private String  photo;                         //会员头像

	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	
}
