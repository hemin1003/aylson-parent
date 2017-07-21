package com.aylson.dc.owner.vo;

import com.aylson.dc.owner.po.BuyerShowPraise;

public class BuyerShowPraiseVo extends BuyerShowPraise {


	private static final long serialVersionUID = -2760765356973038403L;
	
	private String createTimeStr;                  //创建时间

	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
	
}
