package com.aylson.dc.mem.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.mem.po.GiftConfig;
import com.aylson.dc.mem.search.GiftConfigSearch;

public interface GiftConfigService extends BaseService<GiftConfig,GiftConfigSearch> {
	
	/**
	 * 获取积分礼品兑换列表
	 * @param giftConfigSearch
	 * @return
	 */
	public Result getIntegralGiftList(GiftConfigSearch giftConfigSearch, String memberId);
	
	/**
	 * 获取礼品详情
	 * @param giftId
	 * @return
	 */
	public Result getGiftDetail(Integer giftId);
	
	
}
