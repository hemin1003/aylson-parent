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
import com.aylson.dc.qmtt.search.QmttUserFeedbackSearch;
import com.aylson.dc.qmtt.service.QmttUserFeedbackService;
import com.aylson.dc.qmtt.vo.QmttUserFeedbackVo;

/**
 * 用户反馈信息
 * @author Minbo
 */
@Controller
@RequestMapping("/qmtt/qmttUserFeedback")
public class QmttUserFeedbackController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(QmttUserFeedbackController.class);

	@Autowired
	private QmttUserFeedbackService qmttUserFeedbackService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/qmtt/admin/qmttUserFeedback/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(QmttUserFeedbackSearch qmttUserFeedbackSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			qmttUserFeedbackSearch.setIsPage(true);
			List<QmttUserFeedbackVo> list = this.qmttUserFeedbackService.getList(qmttUserFeedbackSearch);
			result.setTotal(this.qmttUserFeedbackService.getRowCount(qmttUserFeedbackSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
}
