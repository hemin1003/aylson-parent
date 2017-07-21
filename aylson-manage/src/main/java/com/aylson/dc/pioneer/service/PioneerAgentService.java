package com.aylson.dc.pioneer.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.pioneer.po.PioneerAgent;
import com.aylson.dc.pioneer.search.PioneerAgentSearch;
import com.aylson.dc.pioneer.vo.PioneerAgentVo;

public interface PioneerAgentService extends BaseService<PioneerAgent,PioneerAgentSearch> {
	
	/**
	 * 审核代理商资料
	 * @param pioneerAgentVo
	 * @return
	 */
	public Result verify(PioneerAgentVo pioneerAgentVo);
	
	/**
	 * 签约代理商资料
	 * @param pioneerAgentVo
	 * @return
	 */
	public Result sign(PioneerAgentVo pioneerAgentVo);
	
	/**
	 * 开业
	 * @param pioneerAgentVo
	 * @return
	 */
	public Result openShop(PioneerAgentVo pioneerAgentVo);
}
