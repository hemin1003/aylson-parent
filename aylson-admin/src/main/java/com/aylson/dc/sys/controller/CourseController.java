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
import com.aylson.dc.sys.search.CourseSearch;
import com.aylson.dc.sys.service.CourseService;
import com.aylson.dc.sys.vo.CourseVo;
import com.aylson.utils.StringUtil;

/**
 * 课程发布管理
 * @author wwx
 * @since  2017-03
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/sys/course")
public class CourseController extends BaseController {
	
	@Autowired
	private CourseService courseService;     //帮助服务
	
	
	/**
	 * 获取列表（分页）
	 * @param courseSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPage", method = RequestMethod.GET)
	public Result getPage(CourseSearch courseSearch) {
		Result result = new Result();
		Page<CourseVo> page = this.courseService.getPage(courseSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", page);
		return result;
	}
	
	/**
	 * 获取列表（不分页）
	 * @param courseSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public Result getList(CourseSearch courseSearch) {
		Result result = new Result();
		List<CourseVo> list = this.courseService.getList(courseSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", list);
		return result;
	}
	
	/**
	 * 获取列表（不分页）
	 * @param courseSearch
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
		CourseVo courseVo = this.courseService.getById(id);
		if(courseVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取信息失败，请稍后再试或者联系管理员！");
		}else{
			result.setOK(ResultCode.CODE_STATE_200, "操作成功", courseVo);
		}
		return result;
	}
	
	/**
	 * 新增
	 * @param courseVo
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(CourseVo courseVo) {
		Result result = new Result();
		try{
			SessionInfo sessionInfo = (SessionInfo) this.request.getSession().getAttribute("sessionInfo");
			if(sessionInfo == null){
				result.setError(ResultCode.CODE_STATE_4006, "你还没登陆或超时，请重新登陆");
				return result;
			}
			if(StringUtil.isEmpty(courseVo.getCourseName())){
				result.setError(ResultCode.CODE_STATE_4006, "课程名称不能为空");
				return result;
			}
			if(StringUtil.isEmpty(courseVo.getCourseUrl())){
				result.setError(ResultCode.CODE_STATE_4006, "课程地址不能为空");
				return result;
			}
			if(StringUtil.isEmpty(courseVo.getLecturer())){
				result.setError(ResultCode.CODE_STATE_4006, "讲师姓名不能为空");
				return result;
			}
			if(courseVo.getType() == null){
				result.setError(ResultCode.CODE_STATE_4006, "课程类型不能为空");
				return result;
			}
			courseVo.setCreateTime(new Date());
			courseVo.setCreaterId(sessionInfo.getUser().getId());
			Boolean flag = this.courseService.add(courseVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("接口：/sys/course/add，获取主键失败"+e.getMessage());
			result.setError(ResultCode.CODE_STATE_500, "操作失败，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	/**
	 * 修改
	 * @param courseVo
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(CourseVo courseVo) {
		Result result = new Result();
		try {
			SessionInfo sessionInfo = (SessionInfo) this.request.getSession().getAttribute("sessionInfo");
			if(sessionInfo == null){
				result.setError(ResultCode.CODE_STATE_4006, "你还没登陆或超时，请重新登陆");
				return result;
			}
			if(courseVo.getId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
				return result;
			}
			if(StringUtil.isEmpty(courseVo.getCourseName())){
				result.setError(ResultCode.CODE_STATE_4006, "课程名称不能为空");
				return result;
			}
			if(StringUtil.isEmpty(courseVo.getCourseUrl())){
				result.setError(ResultCode.CODE_STATE_4006, "课程地址不能为空");
				return result;
			}
			if(StringUtil.isEmpty(courseVo.getLecturer())){
				result.setError(ResultCode.CODE_STATE_4006, "讲师姓名不能为空");
				return result;
			}
			if(courseVo.getType() == null){
				result.setError(ResultCode.CODE_STATE_4006, "课程类型不能为空");
				return result;
			}
			Boolean flag = this.courseService.edit(courseVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("接口：/sys/course/update，获取主键失败"+e.getMessage());
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
			Boolean flag = this.courseService.deleteById(id);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "删除成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "删除失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("接口：/sys/course/deleteById，获取主键失败"+e.getMessage());
			result.setError(ResultCode.CODE_STATE_500, "操作失败，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	/**
	 * 根据id修改状态
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	@ResponseBody
	public Result change(Integer id) {
		Result result = new Result();
		Boolean flag = false;
		try{
			if(id == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
				return result;
			}
			CourseVo courseVo = this.courseService.getById(id);
			if(courseVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "根据id获取信息失败，请稍后再试或者联系管理员！");
				return result;
			}
			if(courseVo.getState() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取课程状态失败，请稍后再试或者联系管理员！");
				return result;
			}
			if(0 == courseVo.getState().intValue()){
				courseVo.setState(1);
			}else if(1 == courseVo.getState().intValue()){
				courseVo.setState(2);
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "不允许该操作！");
				return result;
			}
			flag = this.courseService.edit(courseVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "删除成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "删除失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("接口：/sys/course/deleteById，获取主键失败"+e.getMessage());
			result.setError(ResultCode.CODE_STATE_500, "操作失败，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	
}
