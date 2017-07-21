package com.aylson.dc.mem.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.mem.po.FeedBack;
import com.aylson.dc.mem.search.FeedBackSearch;
import com.aylson.dc.mem.vo.FeedBackReplyVo;

public interface FeedBackService extends BaseService<FeedBack,FeedBackSearch> {
	
	/**
	 * 回复反馈
	 * @param feedBackReplyVo
	 * @return
	 */
	public Result reply(FeedBackReplyVo feedBackReplyVo);
	
}
