package com.aylson.dc.mem.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.easyui.EasyuiDataGridJson;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.base.OwnerGeneralConstant.FeedBackState;
import com.aylson.dc.mem.search.FeedBackReplySearch;
import com.aylson.dc.mem.search.FeedBackSearch;
import com.aylson.dc.mem.service.FeedBackReplyService;
import com.aylson.dc.mem.service.FeedBackService;
import com.aylson.dc.mem.vo.FeedBackReplyVo;
import com.aylson.dc.mem.vo.FeedBackVo;

/**
 * 发布管理
 * @author wwx
 * @since  2016-08
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
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex(Integer type) {
		this.request.setAttribute("type", type);
		return "/jsp/mem/admin/feedBack/index";
	}	
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(FeedBackSearch feedBackSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			feedBackSearch.setIsPage(true);
			List<FeedBackVo> list = this.feedBackService.getList(feedBackSearch);
			result.setTotal(this.feedBackService.getRowCount(feedBackSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据条件获取发布列表信息
	 * @param feedBackSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<FeedBackVo> getList(FeedBackSearch feedBackSearch) {
		List<FeedBackVo> list = this.feedBackService.getList(feedBackSearch);
		return list;
	}
	
	/**
	 * 后台-添加页面
	 * @param feedBackVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toReply", method = RequestMethod.GET)
	public String toAdd(Integer id) {
		if(id!=null){
			FeedBackVo feedBackVo = this.feedBackService.getById(id);
			FeedBackReplySearch feedBackReplySearch = new FeedBackReplySearch();
			feedBackReplySearch.setFeedBackId(id);
			List<FeedBackReplyVo> feedBackReplyList = this.feedBackReplyService.getList(feedBackReplySearch);
			feedBackVo.setFeedBackReplyList(feedBackReplyList);
			this.request.setAttribute("feedBackVo",feedBackVo);
		}
		return "/jsp/mem/admin/feedBack/reply";
	}	
	
	/**
	 * 后台-添加保存
	 * @param feedBackVo
	 * @return
	 */
	@RequestMapping(value = "/admin/reply", method = RequestMethod.POST)
	@ResponseBody
	public Result reply(FeedBackReplyVo feedBackReplyVo) {
		Result result = new Result();
		try{
			Date curDate = new Date();
			feedBackReplyVo.setReplyTime(curDate);
			feedBackReplyVo.setReplyType(2);
			Boolean flag = this.feedBackReplyService.add(feedBackReplyVo);
			if(flag){
				FeedBackVo feedBackVo = new FeedBackVo();
				feedBackVo.setId(feedBackReplyVo.getFeedBackId());
				feedBackVo.setState(FeedBackState.HAD_REPLY);
				feedBackVo.setReplyTime(curDate);
				flag = this.feedBackService.edit(feedBackVo);
			}
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 查看明细
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/toView", method = RequestMethod.GET)
	public String toView(Integer id) {
		if(id!=null){
			FeedBackVo feedBackVo = this.feedBackService.getById(id);
			FeedBackReplySearch feedBackReplySearch = new FeedBackReplySearch();
			feedBackReplySearch.setFeedBackId(id);
			List<FeedBackReplyVo> feedBackReplyList = this.feedBackReplyService.getList(feedBackReplySearch);
			feedBackVo.setFeedBackReplyList(feedBackReplyList);
			this.request.setAttribute("feedBackVo",feedBackVo);
		}
		return "/jsp/mem/admin/feedBack/view";
	}
	
	
}
