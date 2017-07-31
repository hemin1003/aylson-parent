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
import com.aylson.dc.cfdb.search.UserTasklistHisSearch;
import com.aylson.dc.cfdb.service.UserTasklistHisService;
import com.aylson.dc.cfdb.vo.UserTasklistHisVo;

/**
 * 任务操作历史查询
 * @author Minbo
 */
@Controller
@RequestMapping("/cfdb/userTasklistHis")
public class UserTasklistHisController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(UserTasklistHisController.class);

	@Autowired
	private UserTasklistHisService userTasklistHisService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/cfdb/admin/userTasklistHis/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(UserTasklistHisSearch userTasklistHisSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			userTasklistHisSearch.setIsPage(true);
			List<UserTasklistHisVo> list = this.userTasklistHisService.getList(userTasklistHisSearch);
			result.setTotal(this.userTasklistHisService.getRowCount(userTasklistHisSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
}
