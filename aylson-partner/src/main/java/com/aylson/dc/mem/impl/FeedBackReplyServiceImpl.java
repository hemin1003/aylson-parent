package com.aylson.dc.mem.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aylson.core.exception.ServiceException;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.base.PartnerGeneralConstant.FeedBackState;
import com.aylson.dc.mem.FeedBackReplyService;
import com.aylson.dc.mem.FeedBackService;
import com.aylson.dc.mem.dao.FeedBackReplyDao;
import com.aylson.dc.mem.po.FeedBackReply;
import com.aylson.dc.mem.search.FeedBackReplySearch;
import com.aylson.dc.mem.vo.FeedBackReplyVo;
import com.aylson.dc.mem.vo.FeedBackVo;
import com.aylson.utils.StringUtil;

@Service
public class FeedBackReplyServiceImpl extends BaseServiceImpl<FeedBackReply,FeedBackReplySearch> implements FeedBackReplyService {
	
	private static final Logger logger = LoggerFactory.getLogger(FeedBackReplyServiceImpl.class);
	
	@Autowired
	private FeedBackReplyDao feedBackReplyDao;
	@Autowired
	private FeedBackService feedBackService;               //反馈管理服务
	
	@Override
	protected BaseDao<FeedBackReply,FeedBackReplySearch> getBaseDao() {
		return this.feedBackReplyDao;
	}

	@Override
	@Transactional
	public Result reply(FeedBackReplyVo feedBackReplyVo) {
		Result result = new Result();
		//信息校验
		if(feedBackReplyVo.getReplyType() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取回复人类型失败");
			return result;
		}
		if(feedBackReplyVo.getFeedBackId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取反馈信息失败");
			return result;
		}
		if(StringUtil.isEmpty(feedBackReplyVo.getReplyCont())){
			result.setError(ResultCode.CODE_STATE_4006, "回复的内容不能为空");
			return result;
		}
		//添加反馈内容
		Date curDate = new Date();
		feedBackReplyVo.setReplyTime(curDate);
		Boolean flag = this.feedBackReplyDao.insert(feedBackReplyVo);
		if(flag){
			FeedBackVo feedBackVo = new FeedBackVo();
			feedBackVo.setId(feedBackReplyVo.getFeedBackId());
			if(1 == feedBackReplyVo.getReplyType().intValue()){//如果是提问人回复，更新状态为：待回复
				feedBackVo.setState(FeedBackState.WAIT_REPLY);
			}else{//如果是提问人回复，更新状态为：已回答
				feedBackVo.setState(FeedBackState.HAD_REPLY);
			}
			feedBackVo.setReplyTime(curDate);
			flag = this.feedBackService.edit(feedBackVo);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "更新反馈状态失败");
				throw new ServiceException("更新反馈状态失败");
			}
			result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "保存失败");
			return result;
		}
		return result;
	}

	
}
