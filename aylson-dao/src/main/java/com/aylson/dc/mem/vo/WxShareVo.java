package com.aylson.dc.mem.vo;

import com.aylson.dc.mem.po.WxShare;

public class WxShareVo extends WxShare {

	private static final long serialVersionUID = -409566859905862895L;
	
	private Integer sharesHad;                //已经分享总人数
	private Integer assistValueHad;           //已经助力总值
	private String createTimeStr;             //创建时间
	
	
	public Integer getSharesHad() {
		return sharesHad;
	}
	public void setSharesHad(Integer sharesHad) {
		this.sharesHad = sharesHad;
	}
	public Integer getAssistValueHad() {
		return assistValueHad;
	}
	public void setAssistValueHad(Integer assistValueHad) {
		this.assistValueHad = assistValueHad;
	}
	
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
	
}
