package com.aylson.dc.cfdb.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.easyui.EasyuiDataGridJson;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.cfdb.search.TasklistSearch;
import com.aylson.dc.cfdb.service.TaskDetailService;
import com.aylson.dc.cfdb.service.TasklistService;
import com.aylson.dc.cfdb.vo.TaskDetailVo;
import com.aylson.dc.cfdb.vo.TasklistVo;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.utils.DateUtil2;

/**
 * 任务管理
 * @author Minbo
 */
@Controller
@RequestMapping("/cfdb/tasklist")
public class TasklistController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(TasklistController.class);

	@Autowired
	private TasklistService tasklistService;
	
	@Autowired
	private TaskDetailService taskDetailService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/cfdb/admin/tasklist/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(TasklistSearch tasklistSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			tasklistSearch.setIsPage(true);
			List<TasklistVo> list = this.tasklistService.getList(tasklistSearch);
			result.setTotal(this.tasklistService.getRowCount(tasklistSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 后台-添加页面
	 * @param tasklistVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(TasklistVo tasklistVo) {
		this.request.setAttribute("tasklistVo", tasklistVo);
		return "/jsp/cfdb/admin/tasklist/add";
	}
	
	/**
	 * 后台-添加保存
	 * @param tasklistVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(TasklistVo tasklistVo) {
		Result result = new Result();
		try{
			result = this.tasklistService.addListAndDetail(tasklistVo, this.request);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 后台-编辑页面
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value = "/admin/toEdit", method = RequestMethod.GET)
	public String toEdit(String taskId) {
		if(taskId != null){
			TasklistVo tasklistVo = this.tasklistService.getById(taskId);
			this.request.setAttribute("tasklistVo", tasklistVo);
		}
		return "/jsp/cfdb/admin/tasklist/add";
	}
	
	/**
	 * 后台-编辑保存
	 * @param tasklistVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(TasklistVo tasklistVo) {
		Result result = new Result();
		try {
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			tasklistVo.setUpdatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
			tasklistVo.setUpdateDate(DateUtil2.getCurrentLongDateTime());
			Boolean flag = this.tasklistService.edit(tasklistVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 后台-详情编辑页面
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value = "/admin/toDetail", method = RequestMethod.GET)
	public String toDetail(String taskId) {
		if(taskId != null){
			TaskDetailVo taskDetailVo = this.taskDetailService.getById(taskId);
			this.request.setAttribute("taskDetailVo", taskDetailVo);
		}
		return "/jsp/cfdb/admin/tasklist/addDetail";
	}
	
	/**
	 * 后台-详情编辑保存
	 * @param taskDetailVo
	 * @return
	 */
	@RequestMapping(value = "/admin/updateDetail", method = RequestMethod.POST)
	@ResponseBody
	public Result updateDetail(TaskDetailVo taskDetailVo) {
		Result result = new Result();
		try {
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			taskDetailVo.setUpdatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
			taskDetailVo.setUpdateDate(DateUtil2.getCurrentLongDateTime());
			Boolean flag = this.taskDetailService.edit(taskDetailVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 根据id删除
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value = "/admin/deleteById", method = RequestMethod.POST)
	@ResponseBody
	public Result deleteById(String taskId) {
		Result result = new Result();
		try{
			result = this.tasklistService.deleteListAndDetail(taskId);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = "/admin/changeStatus", method = RequestMethod.POST)
	@ResponseBody
	public Result changeStatus(TasklistVo tasklistVo) {
		Result result = new Result();
		try {
			if(tasklistVo.getStatus() == null){
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
				return result;
			}
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			tasklistVo.setUpdatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
			tasklistVo.setUpdateDate(DateUtil2.getCurrentLongDateTime());
			Boolean flag = this.tasklistService.edit(tasklistVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
}
