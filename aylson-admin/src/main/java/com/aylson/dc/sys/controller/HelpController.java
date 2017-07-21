package com.aylson.dc.sys.controller;

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
import com.aylson.dc.sys.search.HelpSearch;
import com.aylson.dc.sys.service.HelpService;
import com.aylson.dc.sys.vo.HelpVo;
import com.aylson.utils.StringUtil;

/**
 * 问题帮助管理
 * @author wwx
 * @since  2017-03
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/sys/help")
public class HelpController extends BaseController {
	
	@Autowired
	private HelpService helpService;     //帮助服务
	
	
	/**
	 * 获取列表（分页）
	 * @param helpSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPage", method = RequestMethod.GET)
	public Result getPage(HelpSearch helpSearch) {
		Result result = new Result();
		Page<HelpVo> page = this.helpService.getPage(helpSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", page);
		return result;
	}
	
	/**
	 * 获取列表（不分页）
	 * @param helpSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public Result getList(HelpSearch helpSearch) {
		Result result = new Result();
		List<HelpVo> list = this.helpService.getList(helpSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", list);
		return result;
	}
	
	/**
	 * 获取列表（不分页）
	 * @param helpSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getById", method = RequestMethod.GET)
	public Result getById(Integer id) {
		Result result = new Result();
		if(id == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
			//logger.error("接口：/sys/store/getById，获取主键失败");
			return result;
		}
		HelpVo helpVo = this.helpService.getById(id);
		if(helpVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取信息失败，请稍后再试或者联系管理员！");
		}else{
			result.setOK(ResultCode.CODE_STATE_200, "操作成功", helpVo);
		}
		return result;
	}
	
	/**
	 * 新增
	 * @param helpVo
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(HelpVo helpVo) {
		Result result = new Result();
		try{
			if(StringUtil.isEmpty(helpVo.getQuestion())){
				result.setError(ResultCode.CODE_STATE_4006, "问题描述不能为空");
				return result;
			}
			if(helpVo.getType() == null){
				result.setError(ResultCode.CODE_STATE_4006, "请选择问题类型");
				return result;
			}
			Boolean flag = this.helpService.add(helpVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("接口：/sys/help/add，获取主键失败"+e.getMessage());
			result.setError(ResultCode.CODE_STATE_500, "操作失败，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	/**
	 * 修改
	 * @param helpVo
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(HelpVo helpVo) {
		Result result = new Result();
		try {
			if(helpVo.getId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
				return result;
			}
			if(StringUtil.isEmpty(helpVo.getQuestion())){
				result.setError(ResultCode.CODE_STATE_4006, "问题描述不能为空");
				return result;
			}
			if(helpVo.getType() == null){
				result.setError(ResultCode.CODE_STATE_4006, "请选择问题类型");
				return result;
			}
			Boolean flag = this.helpService.edit(helpVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("接口：/sys/help/update，获取主键失败"+e.getMessage());
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
			Boolean flag = this.helpService.deleteById(id);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "删除成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "删除失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("接口：/sys/help/deleteById，获取主键失败"+e.getMessage());
			result.setError(ResultCode.CODE_STATE_500, "操作失败，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	
}
