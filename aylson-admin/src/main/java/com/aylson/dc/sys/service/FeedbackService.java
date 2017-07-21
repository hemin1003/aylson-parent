package com.aylson.dc.sys.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.sys.po.Feedback;
import com.aylson.dc.sys.search.FeedbackSearch;
import com.aylson.dc.sys.vo.FeedbackVo;

public interface FeedbackService extends BaseService<Feedback,FeedbackSearch> {
	
	/**
	 * 添加反馈
	 * @param feedbackVo
	 * @return
	 */
	public Result addFeedBack(FeedbackVo feedbackVo, String memberId);
}
