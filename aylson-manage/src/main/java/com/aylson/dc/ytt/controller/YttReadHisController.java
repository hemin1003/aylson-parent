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
import com.aylson.dc.ytt.search.YttReadHisSearch;
import com.aylson.dc.ytt.service.YttReadHisService;
import com.aylson.dc.ytt.vo.YttReadHisVo;

/**
 * 阅读文章历史
 * @author Minbo
 */
@Controller
@RequestMapping("/ytt/readHis")
public class YttReadHisController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(YttReadHisController.class);

	@Autowired
	private YttReadHisService readHisService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/ytt/admin/readHis/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(YttReadHisSearch readHisSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			readHisSearch.setIsPage(true);
			List<YttReadHisVo> list = this.readHisService.getList(readHisSearch);
			result.setTotal(this.readHisService.getRowCount(readHisSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
}
