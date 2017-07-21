package com.aylson.dc.mem.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.mem.po.GiftExchangeRecords;
import com.aylson.dc.mem.search.GiftExchangeRecordsSearch;
import com.aylson.dc.mem.vo.GiftExchangeRecordsVo;

public interface GiftExchangeRecordsService extends BaseService<GiftExchangeRecords,GiftExchangeRecordsSearch> {
	
	/**
	 * 兑换礼品
	 * @param giftExchangeRecords
	 * @return
	 */
	public Result exchangeGift(GiftExchangeRecordsVo giftExchangeRecordsVo);
	
	/**
	 * 通用性校验
	 * @param giftExchangeRecords
	 * @return
	 */
	public Result commonValid(GiftExchangeRecordsVo giftExchangeRecordsVo);
	
	/**
	 * 兑换礼品
	 * @param giftId
	 * @return
	 */
	public Result exchangeGift(Integer giftId, String memberId) throws Exception;
	
	
}
