package com.aylson.dc.ytt.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.easyui.EasyuiDataGridJson;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.dc.ytt.search.YttBalanceHisSearch;
import com.aylson.dc.ytt.service.YttBalanceHisService;
import com.aylson.dc.ytt.vo.YttBalanceHisVo;

/**
 * 零钱兑换记录
 * @author Minbo
 */
@Controller
@RequestMapping("/ytt/balanceHis")
public class YttBalanceHisController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(YttBalanceHisController.class);

	@Autowired
	private YttBalanceHisService balanceHisService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/ytt/admin/balanceHis/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(YttBalanceHisSearch balanceHisSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			balanceHisSearch.setIsPage(true);
			List<YttBalanceHisVo> list = this.balanceHisService.getList(balanceHisSearch);
			result.setTotal(this.balanceHisService.getRowCount(balanceHisSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
}
