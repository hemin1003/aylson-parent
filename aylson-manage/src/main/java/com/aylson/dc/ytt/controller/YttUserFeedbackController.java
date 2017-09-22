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
import com.aylson.dc.ytt.search.YttUserFeedbackSearch;
import com.aylson.dc.ytt.service.YttUserFeedbackService;
import com.aylson.dc.ytt.vo.YttUserFeedbackVo;

/**
 * 用户反馈信息
 * @author Minbo
 */
@Controller
@RequestMapping("/ytt/yttUserFeedback")
public class YttUserFeedbackController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(YttUserFeedbackController.class);

	@Autowired
	private YttUserFeedbackService yttUserFeedbackService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/ytt/admin/yttUserFeedback/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(YttUserFeedbackSearch yttUserFeedbackSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			yttUserFeedbackSearch.setIsPage(true);
			List<YttUserFeedbackVo> list = this.yttUserFeedbackService.getList(yttUserFeedbackSearch);
			result.setTotal(this.yttUserFeedbackService.getRowCount(yttUserFeedbackSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
}
