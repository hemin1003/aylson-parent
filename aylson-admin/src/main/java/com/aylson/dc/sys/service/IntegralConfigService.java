package com.aylson.dc.sys.service;

import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.sys.po.IntegralConfig;
import com.aylson.dc.sys.search.IntegralConfigSearch;
import com.aylson.dc.sys.vo.IntegralConfigVo;

public interface IntegralConfigService extends BaseService<IntegralConfig,IntegralConfigSearch> {
	
	/**
	 * 获取需要赠送的积分
	 * @param type
	 * @param amount
	 * @return
	 */
	public Integer getIntegral(Integer type, Float amount);
	
	/**
	 * 根据积分获取所属积分等级
	 * @param integral
	 * @return
	 */
	public Integer getIntegralLevel(Integer integral);
	
	/**
	 * 获取需要赠送的积分配置对象
	 * @param type
	 * @param rangeValue  范围值
	 * @return
	 */
	public IntegralConfigVo getIntegralConfig(Integer type, Float rangeValue);
	
}
