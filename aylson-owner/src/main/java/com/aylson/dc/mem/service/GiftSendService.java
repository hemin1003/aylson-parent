package com.aylson.dc.mem.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.mem.po.GiftSend;
import com.aylson.dc.mem.search.GiftSendSearch;
import com.aylson.dc.mem.vo.GiftSendVo;

public interface GiftSendService extends BaseService<GiftSend,GiftSendSearch> {
	
	/**
	 * 添加礼品订货单
	 * @param giftSendVo
	 * @return
	 */
	public Result addGiftSend(GiftSendVo giftSendVo);
	
	/**
	 * 兑换活动礼品
	 * @param giftSendVo
	 * @return
	 */
	public Result exchangeActGift(GiftSendVo giftSendVo);
	
}
