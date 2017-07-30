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
import com.aylson.dc.cfdb.search.SdkTasklistHisSearch;
import com.aylson.dc.cfdb.service.SdkTasklistHisService;
import com.aylson.dc.cfdb.vo.SdkTasklistHisVo;

/**
 * SDK平台广告回调查询
 * @author Minbo
 */
@Controller
@RequestMapping("/cfdb/sdkTasklistHis")
public class SdkTasklistHisController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(SdkTasklistHisController.class);

	@Autowired
	private SdkTasklistHisService sdkTasklistHisService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/cfdb/admin/sdkTasklistHis/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(SdkTasklistHisSearch sdkTasklistHisSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			sdkTasklistHisSearch.setIsPage(true);
			List<SdkTasklistHisVo> list = this.sdkTasklistHisService.getList(sdkTasklistHisSearch);
			result.setTotal(this.sdkTasklistHisService.getRowCount(sdkTasklistHisSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 后台-编辑页面
	 * @param hashid
	 * @return
	 */
	@RequestMapping(value = "/admin/toEdit", method = RequestMethod.GET)
	public String toEdit(String hashid) {
		if(hashid != null){
			SdkTasklistHisVo sdkTasklistHisVo = this.sdkTasklistHisService.getById(hashid);
			this.request.setAttribute("sdkTasklistHisVo", sdkTasklistHisVo);
		}
		return "/jsp/cfdb/admin/sdkTasklistHis/add";
	}
}
