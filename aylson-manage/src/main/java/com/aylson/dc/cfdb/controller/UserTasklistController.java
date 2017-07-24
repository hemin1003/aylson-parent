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
import com.aylson.dc.cfdb.search.UserTasklistSearch;
import com.aylson.dc.cfdb.service.UserTasklistService;
import com.aylson.dc.cfdb.vo.UserTasklistVo;
import com.aylson.utils.DateUtil2;

/**
 * 用户任务管理
 * @author Minbo
 */
@Controller
@RequestMapping("/cfdb/userTasklist")
public class UserTasklistController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(UserTasklistController.class);

	@Autowired
	private UserTasklistService userTasklistService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/cfdb/admin/userTasklist/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(UserTasklistSearch userTasklistSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			userTasklistSearch.setIsPage(true);
			List<UserTasklistVo> list = this.userTasklistService.getList(userTasklistSearch);
			result.setTotal(this.userTasklistService.getRowCount(userTasklistSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 后台-编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/toEdit", method = RequestMethod.GET)
	public String toEdit(String id) {
		if(id != null){
			UserTasklistVo userTasklistVo = this.userTasklistService.getById(id);
			this.request.setAttribute("userTasklistVo", userTasklistVo);
		}
		return "/jsp/cfdb/admin/userTasklist/add";
	}
	
	/**
	 * 后台-编辑保存
	 * @param userTasklistVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(UserTasklistVo userTasklistVo) {
		Result result = new Result();
		try {
			userTasklistVo.setIsChecked(1);
			userTasklistVo.setUpdateDate(DateUtil2.getCurrentLongDateTime());
			Boolean flag = this.userTasklistService.edit(userTasklistVo);
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
