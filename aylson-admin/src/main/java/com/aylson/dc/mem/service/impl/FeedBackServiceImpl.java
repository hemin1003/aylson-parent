package com.aylson.dc.mem.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aylson.core.exception.ServiceException;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.base.OwnerGeneralConstant.FeedBackState;
import com.aylson.dc.mem.dao.FeedBackDao;
import com.aylson.dc.mem.po.FeedBack;
import com.aylson.dc.mem.search.FeedBackSearch;
import com.aylson.dc.mem.service.FeedBackReplyService;
import com.aylson.dc.mem.service.FeedBackService;
import com.aylson.dc.mem.vo.FeedBackReplyVo;
import com.aylson.dc.mem.vo.FeedBackVo;
import com.aylson.utils.StringUtil;

@Service
public class FeedBackServiceImpl extends BaseServiceImpl<FeedBack,FeedBackSearch> implements FeedBackService {
	
//	private static final Logger logger = LoggerFactory.getLogger(FeedBackServiceImpl.class);
	
	@Autowired
	private FeedBackDao feedBackDao;
	
	@Autowired
	private FeedBackReplyService feedBackReplyService;
	
	@Override
	protected BaseDao<FeedBack,FeedBackSearch> getBaseDao() {
		return this.feedBackDao;
	}

	@Override
	@Transactional
	public Result reply(FeedBackReplyVo feedBackReplyVo) {
		Result result = new Result();
		if(feedBackReplyVo.getFeedBackId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "反馈id不能为空");
			return result;
		}
		if(StringUtil.isEmpty(feedBackReplyVo.getReplyCont())){
			result.setError(ResultCode.CODE_STATE_4006, "反馈内容不能为空");
			return result;
		}
		Date curDate = new Date();
		feedBackReplyVo.equals(2);//客服人员回复
		feedBackReplyVo.setReplyTime(curDate);
		feedBackReplyVo.setReplyType(FeedBackState.HAD_REPLY);   //已回答
		Boolean flag = this.feedBackReplyService.add(feedBackReplyVo);
		if(flag){
			FeedBackVo feedBackVo = new FeedBackVo();
			feedBackVo.setId(feedBackReplyVo.getFeedBackId());
			feedBackVo.setState(FeedBackState.HAD_REPLY);
			feedBackVo.setReplyTime(curDate);
			flag = this.edit(feedBackVo);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "更新反馈记录失败");
				throw new ServiceException("更新反馈记录失败");
			}
			result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "添加回复记录失败");
		}
		return result;
	}	

	
}
