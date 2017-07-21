package com.aylson.dc.pioneer.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.pioneer.po.PioneerAgentSaleInfo;
import com.aylson.dc.pioneer.search.PioneerAgentSaleInfoSearch;
import com.aylson.dc.pioneer.vo.PioneerAgentSaleInfoVo;

public interface PioneerAgentSaleInfoService extends BaseService<PioneerAgentSaleInfo,PioneerAgentSaleInfoSearch> {
	
	/**
	 * 添加销售明细
	 * @param pioneerAgentSaleInfoVo
	 * @return
	 */
	public Result addSaleInfo(PioneerAgentSaleInfoVo pioneerAgentSaleInfoVo);
	
	
}
