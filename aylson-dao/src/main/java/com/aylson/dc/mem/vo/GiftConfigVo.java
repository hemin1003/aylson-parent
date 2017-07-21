package com.aylson.dc.mem.vo;

import java.util.List;
import java.util.Map;

import com.aylson.dc.mem.po.GiftConfig;

public class GiftConfigVo extends GiftConfig {

	private static final long serialVersionUID = 3769382872179479759L;
	
	private Boolean whetherExchange = false;  //是否可以兑换
	private List<String> imgNavigationAddress;//礼品导航图片地址集合
	private Map<String,String> parameterMap;  //参数集合
	private Boolean isExchanged = false;      //是否已经领取

	public Boolean getWhetherExchange() {
		return whetherExchange;
	}
	public void setWhetherExchange(Boolean whetherExchange) {
		this.whetherExchange = whetherExchange;
	}
	
	public List<String> getImgNavigationAddress() {
		return imgNavigationAddress;
	}
	public void setImgNavigationAddress(List<String> imgNavigationAddress) {
		this.imgNavigationAddress = imgNavigationAddress;
	}
	
	public Map<String, String> getParameterMap() {
		return parameterMap;
	}
	public void setParameterMap(Map<String, String> parameterMap) {
		this.parameterMap = parameterMap;
	}
	
	public Boolean getIsExchanged() {
		return isExchanged;
	}
	public void setIsExchanged(Boolean isExchanged) {
		this.isExchanged = isExchanged;
	}
	
	
}
