package com.aylson.dc.qmtt.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.easyui.EasyuiDataGridJson;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.dc.qmtt.search.AwardHisSearch;
import com.aylson.dc.qmtt.service.AwardHisService;
import com.aylson.dc.qmtt.vo.AwardHisVo;

/**
 * 金币奖励记录
 * @author Minbo
 */
@Controller
@RequestMapping("/qmtt/awardHis")
public class AwardHisController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(AwardHisController.class);

	@Autowired
	private AwardHisService awardHisService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/qmtt/admin/awardHis/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(AwardHisSearch awardHisSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			awardHisSearch.setIsPage(true);
			List<AwardHisVo> list = this.awardHisService.getList(awardHisSearch);
			result.setTotal(this.awardHisService.getRowCount(awardHisSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
}
