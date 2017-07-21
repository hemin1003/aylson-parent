package com.aylson.dc.sys.controller;

import java.util.Date;
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
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.dc.sys.search.MemoSearch;
import com.aylson.dc.sys.service.MemoService;
import com.aylson.dc.sys.vo.MemoVo;
import com.aylson.utils.DateUtil;
import com.aylson.utils.StringUtil;

/**
 * 问题帮助管理
 * @author wwx
 * @since  2017-03
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/sys/memo")
public class MemoController extends BaseController {
	
	@Autowired
	private MemoService memoService;     //我的备忘服务
	
	
	/**
	 * 获取列表（分页）
	 * @param memoSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPage", method = RequestMethod.GET)
	public Result getPage(MemoSearch memoSearch) {
		Result result = new Result();
		SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
		if(sessionInfo == null || sessionInfo.getUser() == null){
			result.setError(ResultCode.CODE_STATE_4006, "登录失效，请重新登录再试");
			return result;
		}
		memoSearch.setUserId(sessionInfo.getUser().getId());
		Page<MemoVo> page = this.memoService.getPage(memoSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", page);
		return result;
	}
	
	/**
	 * 获取列表（不分页）
	 * @param memoSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public Result getList(MemoSearch memoSearch) {
		Result result = new Result();
		SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
		if(sessionInfo == null || sessionInfo.getUser() == null){
			result.setError(ResultCode.CODE_STATE_4006, "登录失效，请重新登录再试");
			return result;
		}
		memoSearch.setUserId(sessionInfo.getUser().getId());
		List<MemoVo> list = this.memoService.getList(memoSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", list);
		return result;
	}
	
	/**
	 * 根据id获取
	 * @param memoSearch
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
		MemoVo memoVo = this.memoService.getById(id);
		if(memoVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取信息失败，请稍后再试或者联系管理员！");
		}else{
			result.setOK(ResultCode.CODE_STATE_200, "操作成功", memoVo);
		}
		return result;
	}
	
	/**
	 * 新增
	 * @param memoVo
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(MemoVo memoVo) {
		Result result = new Result();
		try{
			if(StringUtil.isEmpty(memoVo.getContent())){
				result.setError(ResultCode.CODE_STATE_4006, "请添加备忘内容");
				return result;
			}
			if(StringUtil.isEmpty(memoVo.getMemoTimeStr())){
				result.setError(ResultCode.CODE_STATE_4006, "请选择备忘时间");
				return result;
			}
			if(memoVo.getMemoLevel() == null){
				result.setError(ResultCode.CODE_STATE_4006, "请选择优先级");
				return result;
			}
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			if(sessionInfo == null || sessionInfo.getUser() == null){
				result.setError(ResultCode.CODE_STATE_4006, "登录失效，请重新登录再试");
				return result;
			}
			memoVo.setUserId(sessionInfo.getUser().getId());
			memoVo.setMemoTime(DateUtil.format(memoVo.getMemoTimeStr()));
			memoVo.setCreateTime(new Date());
			Boolean flag = this.memoService.add(memoVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("接口：/sys/memo/add，获取主键失败"+e.getMessage());
			result.setError(ResultCode.CODE_STATE_500, "操作失败，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	/**
	 * 新增
	 * @param memoVo
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(MemoVo memoVo) {
		Result result = new Result();
		try{
			if(memoVo.getId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取备忘id失败");
				return result;
			}
			
			if(StringUtil.isNotEmpty(memoVo.getMemoTimeStr())){
				memoVo.setMemoTime(DateUtil.format(memoVo.getMemoTimeStr()));
			}
			Boolean flag = this.memoService.edit(memoVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("接口：/sys/memo/add，获取主键失败"+e.getMessage());
			result.setError(ResultCode.CODE_STATE_500, "操作失败，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteById", method = RequestMethod.POST)
	@ResponseBody
	public Result deleteById(Integer id) {
		Result result = new Result();
		try{
			if(id == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
				return result;
			}
			Boolean flag = this.memoService.deleteById(id);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "删除成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "删除失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("接口：/sys/memo/deleteById，获取主键失败"+e.getMessage());
			result.setError(ResultCode.CODE_STATE_500, "操作失败，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	
}
