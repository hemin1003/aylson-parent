package com.aylson.dc.pioneer.vo;

import com.aylson.dc.pioneer.po.PioneerAgentSaleInfo;

public class PioneerAgentSaleInfoVo extends PioneerAgentSaleInfo{

	private static final long serialVersionUID = 6017528812010789132L;
	
	private Float rebateRate;                 //配置回扣率

	public Float getRebateRate() {
		return rebateRate;
	}
	public void setRebateRate(Float rebateRate) {
		this.rebateRate = rebateRate;
	}
	
	
}
