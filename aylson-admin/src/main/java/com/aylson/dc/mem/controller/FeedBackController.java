package com.aylson.dc.mem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.frame.base.Page;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.mem.search.FeedBackReplySearch;
import com.aylson.dc.mem.search.FeedBackSearch;
import com.aylson.dc.mem.service.FeedBackReplyService;
import com.aylson.dc.mem.service.FeedBackService;
import com.aylson.dc.mem.vo.FeedBackReplyVo;
import com.aylson.dc.mem.vo.FeedBackVo;

/**
 * 发布管理
 * @author wwx
 * @since  2017-03
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/mem/feedBack")
public class FeedBackController extends BaseController {
	
	@Autowired
	private FeedBackService feedBackService;               //反馈管理服务
	@Autowired
	private FeedBackReplyService feedBackReplyService;     //反馈回复管理服务
	
	
	/**
	 * 获取列表（分页）
	 * @param feedBackSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPage", method = RequestMethod.GET)
	public Result getPage(FeedBackSearch feedBackSearch) {
		Result result = new Result();
		Page<FeedBackVo> page = this.feedBackService.getPage(feedBackSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", page);
		return result;
	}
	
	/**
	 * 获取列表（分页）
	 * @param feedBackSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public Result getList(FeedBackSearch feedBackSearch) {
		Result result = new Result();
		List<FeedBackVo> list = this.feedBackService.getList(feedBackSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", list);
		return result;
	}
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getById", method = RequestMethod.GET)
	public Result getById(Integer id) {
		Result result = new Result();
		if(id == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
			return result;
		}
		FeedBackVo feedBackVo = this.feedBackService.getById(id);
		if(feedBackVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取信息失败，请稍后再试或者联系管理员！");
			return result;
		}
		FeedBackReplySearch feedBackReplySearch = new FeedBackReplySearch();
		feedBackReplySearch.setFeedBackId(id);
		List<FeedBackReplyVo> feedBackReplyList = this.feedBackReplyService.getList(feedBackReplySearch);
		feedBackVo.setFeedBackReplyList(feedBackReplyList);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", feedBackVo);
		return result;
	}
	
	/**
	 * 回复反馈
	 * @param feedBackVo
	 * @return
	 */
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	@ResponseBody
	public Result reply(FeedBackReplyVo feedBackReplyVo) {
		Result result = null;
		try{
			result = this.feedBackService.reply(feedBackReplyVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍后再试或联系管理员");
		}
		return result;
	}
	
	
}
