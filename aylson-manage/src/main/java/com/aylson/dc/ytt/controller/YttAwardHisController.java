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
import com.aylson.dc.ytt.search.YttAwardHisSearch;
import com.aylson.dc.ytt.service.YttAwardHisService;
import com.aylson.dc.ytt.vo.YttAwardHisVo;

/**
 * 金币奖励记录
 * @author Minbo
 */
@Controller
@RequestMapping("/ytt/awardHis")
public class YttAwardHisController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(YttAwardHisController.class);

	@Autowired
	private YttAwardHisService awardHisService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/ytt/admin/awardHis/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(YttAwardHisSearch awardHisSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			awardHisSearch.setIsPage(true);
			List<YttAwardHisVo> list = this.awardHisService.getList(awardHisSearch);
			result.setTotal(this.awardHisService.getRowCount(awardHisSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
}
