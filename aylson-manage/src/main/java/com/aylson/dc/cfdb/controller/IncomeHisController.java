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
import com.aylson.dc.cfdb.search.IncomeHisSearch;
import com.aylson.dc.cfdb.service.IncomeHisService;
import com.aylson.dc.cfdb.vo.IncomeHisVo;

/**
 * 用户收益记录查询
 * @author Minbo
 */
@Controller
@RequestMapping("/cfdb/incomeHis")
public class IncomeHisController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(IncomeHisController.class);

	@Autowired
	private IncomeHisService incomeHisService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/cfdb/admin/incomeHis/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(IncomeHisSearch IncomeHisSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			IncomeHisSearch.setIsPage(true);
			List<IncomeHisVo> list = this.incomeHisService.getList(IncomeHisSearch);
			result.setTotal(this.incomeHisService.getRowCount(IncomeHisSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
}
