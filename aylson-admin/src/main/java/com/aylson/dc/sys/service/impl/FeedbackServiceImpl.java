package com.aylson.dc.sys.service.impl;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.mem.service.MemAccountService;
import com.aylson.dc.mem.vo.MemAccountVo;
import com.aylson.dc.sys.dao.FeedbackDao;
import com.aylson.dc.sys.po.Feedback;
import com.aylson.dc.sys.search.FeedbackSearch;
import com.aylson.dc.sys.service.FeedbackService;
import com.aylson.dc.sys.vo.FeedbackVo;
import com.aylson.utils.StringUtil;


@Service
public class FeedbackServiceImpl extends BaseServiceImpl<Feedback,FeedbackSearch> implements FeedbackService {

	@Autowired
	private FeedbackDao feedbackDao;
	@Autowired
	private MemAccountService memAccountService;

	@Override
	protected BaseDao<Feedback,FeedbackSearch> getBaseDao() {
		return feedbackDao;
	}

	@Override
	public Result addFeedBack(FeedbackVo feedbackVo, String memberId) {
		Result result = new Result();
		Boolean flag = false;
		//1、信息有效性校验  
		/*if(StringUtil.isEmpty(feedbackVo.getTitle())){
			result.setError(ResultCode.CODE_STATE_4006, "标题不能为空");
			return result;
		}*/
		if(StringUtil.isEmpty(feedbackVo.getDescribe())){
			result.setError(ResultCode.CODE_STATE_4006, "详细描述不能为空");
			return result;
		}
		if(StringUtil.isNotEmpty(memberId)){//如果是登陆进行的反馈，获取登录人的信息
			MemAccountVo memAccountVo = this.memAccountService.getById(Integer.parseInt(memberId));
			if(memAccountVo != null){
				feedbackVo.setFeedbacker(memAccountVo.getAccountName());
				feedbackVo.setFeedbackerId(memAccountVo.getId());
				if(StringUtil.isEmpty(feedbackVo.getFeedbackPhone())){
					feedbackVo.setFeedbackPhone(memAccountVo.getMobilePhone());
				}
			}
		}
		feedbackVo.setFeedbackTime(new Date());
		flag = this.add(feedbackVo);
		if(flag){
			result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "操作失败");
		}
		return result;
	}

	

}
