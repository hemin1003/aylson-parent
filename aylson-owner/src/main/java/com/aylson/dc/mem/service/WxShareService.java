package com.aylson.dc.mem.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.mem.po.WxShare;
import com.aylson.dc.mem.search.WxShareSearch;

public interface WxShareService extends BaseService<WxShare,WxShareSearch> {
	
	/**
	 * 判断该活动渠道是否已经分享过了
	 * @param channel
	 * @param inviterWxOpenId
	 * @param inviterWxUnionId
	 * @return
	 */
	public Result isShared(String channel, String inviterWxOpenId, String wxOpenId);
	
	/**
	 * 
	 * @param sharersTotal       需要分享的总人数
	 * @param sharersHad         已经分享的总人数
	 * @param assistValueTotal   需要获取的总助力值
	 * @param assistValueHad     已经获取的总助力值
	 * @return
	 */
	public int getAssistValue(int sharersTotal,int sharersHad, int assistValueTotal, int assistValueHad);
	
}
