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
import com.aylson.dc.sys.search.NoticeSearch;
import com.aylson.dc.sys.service.NoticeService;
import com.aylson.dc.sys.vo.NoticeVo;
import com.aylson.utils.StringUtil;

/**
 * 公告管理
 * @author wwx
 * @since  2017-03
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/sys/notice")
public class NoticeController extends BaseController {
	
	@Autowired
	private NoticeService noticeService;     //公告管理服务
	
	
	/**
	 * 获取列表（分页）
	 * @param noticeSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPage", method = RequestMethod.GET)
	public Result getPage(NoticeSearch noticeSearch) {
		Result result = new Result();
		Page<NoticeVo> page = this.noticeService.getPage(noticeSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", page);
		return result;
	}
	
	/**
	 * 获取列表（不分页）
	 * @param noticeSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public Result getList(NoticeSearch noticeSearch) {
		Result result = new Result();
		List<NoticeVo> list = this.noticeService.getList(noticeSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", list);
		return result;
	}
	
	/**
	 * 获取列表（不分页）
	 * @param noticeSearch
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
		NoticeVo noticeVo = this.noticeService.getById(id);
		if(noticeVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取信息失败，请稍后再试或者联系管理员！");
		}else{
			result.setOK(ResultCode.CODE_STATE_200, "操作成功", noticeVo);
		}
		return result;
	}
	
	/**
	 * 新增
	 * @param noticeVo
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(NoticeVo noticeVo) {
		Result result = new Result();
		try{
			if(StringUtil.isEmpty(noticeVo.getTitle())){
				result.setError(ResultCode.CODE_STATE_4006, "标题不能为空");
				return result;
			}
			if(StringUtil.isEmpty(noticeVo.getContent())){
				result.setError(ResultCode.CODE_STATE_4006, "内容不能为空");
				return result;
			}
			/*if(noticeVo.getNoticeObj() == null){
				result.setError(ResultCode.CODE_STATE_4006, "通知对象不能为空");
				return result;
			}*/
			noticeVo.setCreateTime(new Date());
			Boolean flag = this.noticeService.add(noticeVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("接口：/sys/notice/add，获取主键失败"+e.getMessage());
			result.setError(ResultCode.CODE_STATE_500, "操作失败，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	/**
	 * 修改
	 * @param noticeVo
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(NoticeVo noticeVo) {
		Result result = new Result();
		try {
			if(noticeVo.getId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
				return result;
			}
			if(StringUtil.isEmpty(noticeVo.getTitle())){
				result.setError(ResultCode.CODE_STATE_4006, "标题不能为空");
				return result;
			}
			if(StringUtil.isEmpty(noticeVo.getContent())){
				result.setError(ResultCode.CODE_STATE_4006, "内容不能为空");
				return result;
			}
			/*if(noticeVo.getNoticeObj() == null){
				result.setError(ResultCode.CODE_STATE_4006, "通知对象不能为空");
				return result;
			}*/
			Boolean flag = this.noticeService.edit(noticeVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("接口：/sys/notice/update，获取主键失败"+e.getMessage());
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
			NoticeVo noticeVo = this.noticeService.getById(id);
			if(noticeVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取信息失败，请稍后再试或者联系管理员！");
				return result;
			}
			if(noticeVo.getState() != null && noticeVo.getState().intValue() != 0){
				result.setError(ResultCode.CODE_STATE_4006, "已经发布过的不能删除！");
				return result;
			}
			Boolean flag = this.noticeService.deleteById(id);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "删除成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "删除失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "操作失败，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	/**
	 * 发布/取消发布
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/publish", method = RequestMethod.POST)
	@ResponseBody
	public Result publish(Integer id) {
		Result result = new Result();
		try{
			if(id == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
				return result;
			}
			NoticeVo noticeVo = this.noticeService.getById(id);
			if(noticeVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取信息失败，请稍后再试或者联系管理员！");
				return result;
			}
			if( 0 == noticeVo.getState().intValue()){//草稿
				noticeVo.setState(1);//发布
				noticeVo.setPublishTime(new Date());
			}else if( 1 == noticeVo.getState().intValue()){
				noticeVo.setState(2);//取消发布
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "不能再进行操作了！");
				return result;
			}
			Boolean flag = this.noticeService.edit(noticeVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "操作失败，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	
}
