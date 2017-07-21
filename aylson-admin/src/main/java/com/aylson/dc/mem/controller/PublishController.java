package com.aylson.dc.mem.controller;

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
import com.aylson.dc.base.GeneralConstant.PublishStatus;
import com.aylson.dc.mem.search.PublishSearch;
import com.aylson.dc.mem.service.PublishService;
import com.aylson.dc.mem.vo.PublishVo;
import com.aylson.utils.StringUtil;

/**
 * 发布管理
 * @author wwx
 * @since  2017-03
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/mem/publish")
public class PublishController extends BaseController {
	
	@Autowired
	private PublishService publishService;     //发布管理服务
	
	
	/**
	 * 获取列表（分页）
	 * @param publishSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPage", method = RequestMethod.GET)
	public Result getPage(PublishSearch publishSearch) {
		Result result = new Result();
		Page<PublishVo> page = this.publishService.getPage(publishSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", page);
		return result;
	}
	
	/**
	 * 获取列表（不分页）
	 * @param publishSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public Result getList(PublishSearch publishSearch) {
		Result result = new Result();
		List<PublishVo> list = this.publishService.getList(publishSearch);
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
			//logger.error("接口：/sys/publish/getById，获取主键失败");
			return result;
		}
		PublishVo publishVo = this.publishService.getById(id);
		if(publishVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取信息失败，请稍后再试或者联系管理员！");
		}else{
			result.setOK(ResultCode.CODE_STATE_200, "操作成功", publishVo);
		}
		return result;
	}
	
	/**
	 * 新增
	 * @param publishVo
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(PublishVo publishVo) {
		Result result = new Result();
		try{
			if(StringUtil.isEmpty(publishVo.getTitle())){
				result.setError(ResultCode.CODE_STATE_4006, "标题不能为空");
				return result;
			}
			if(StringUtil.isEmpty(publishVo.getThumb())){
				result.setError(ResultCode.CODE_STATE_4006, "缩略图不能为空");
				return result;
			}
			publishVo.setCreateTime(new Date());
			Boolean flag = this.publishService.add(publishVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("接口：/sys/publish/add，获取主键失败"+e.getMessage());
			result.setError(ResultCode.CODE_STATE_500, "操作失败，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	/**
	 * 修改
	 * @param publishVo
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(PublishVo publishVo) {
		Result result = new Result();
		try {
			if(publishVo.getId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
				return result;
			}
			if(StringUtil.isEmpty(publishVo.getTitle())){
				result.setError(ResultCode.CODE_STATE_4006, "标题不能为空");
				return result;
			}
			Boolean flag = this.publishService.edit(publishVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("接口：/sys/publish/update，获取主键失败"+e.getMessage());
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
			PublishVo publishVo = this.publishService.getById(id);
			if(publishVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取记录信息失败，请稍后再试或者联系管理员！");
				return result;
			}
			if(publishVo.getStatus() != null && PublishStatus.DRAFT == publishVo.getStatus().intValue()){
				result.setError(ResultCode.CODE_STATE_4006, "该记录已经发布过了，不能删除！");
				return result;
			}
			Boolean flag = this.publishService.deleteById(id);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "删除成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "删除失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("接口：/sys/publish/deleteById，获取主键失败"+e.getMessage());
			result.setError(ResultCode.CODE_STATE_500, "操作失败，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	/**
	 * 发布
	 * @param publishVo
	 * @return
	 */
	@RequestMapping(value = "/publish", method = RequestMethod.POST)
	@ResponseBody
	public Result publish(Integer id) {
		Result result = new Result();
		try {
			if(id == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
				return result;
			}
			PublishVo publishVo = this.publishService.getById(id);
			if(publishVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "根据获取信息失败，请稍后再试或者联系管理员！");
				return result;
			}
			if(publishVo.getStatus().intValue() == PublishStatus.DRAFT){//如果当前状态：已经发布，修改为：结束发布
				publishVo.setPublishTime(new Date());
				publishVo.setStatus(PublishStatus.PUBLISH);
			}else if(publishVo.getStatus().intValue() == PublishStatus.PUBLISH){
				publishVo.setStatus(PublishStatus.FINISH_PUBLISH);
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "未知状态！");
				return result;
			}
			Boolean flag = this.publishService.edit(publishVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	
}
