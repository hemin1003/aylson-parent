package com.aylson.dc.mem.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.mem.po.FeedBackReply;
import com.aylson.dc.mem.search.FeedBackReplySearch;
import com.aylson.dc.mem.vo.FeedBackReplyVo;

public interface FeedBackReplyService extends BaseService<FeedBackReply,FeedBackReplySearch> {
	
	/**
	 * 回复反馈
	 * @param feedBackReplyVo
	 * @return
	 */
	public Result reply(FeedBackReplyVo feedBackReplyVo);
}
