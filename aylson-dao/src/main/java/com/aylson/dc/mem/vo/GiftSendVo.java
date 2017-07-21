package com.aylson.dc.mem.vo;

import com.aylson.dc.mem.po.GiftSend;

public class GiftSendVo extends GiftSend {

	private static final long serialVersionUID = 2790484781775925929L;

	private String createTimeStr;        //创建时间
	private String updateTimeStr;        //更新时间
	private String detailIds;            //礼品详情id集合，以；隔开
	private String detailNames;          //礼品详情名称集合，已；隔开
	
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
	public String getUpdateTimeStr() {
		return updateTimeStr;
	}
	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}
	
	public String getDetailIds() {
		return detailIds;
	}
	public void setDetailIds(String detailIds) {
		this.detailIds = detailIds;
	}
	
	public String getDetailNames() {
		return detailNames;
	}
	public void setDetailNames(String detailNames) {
		this.detailNames = detailNames;
	}
	
	
}
