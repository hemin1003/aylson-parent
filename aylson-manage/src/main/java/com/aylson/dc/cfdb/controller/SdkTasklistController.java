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
import com.aylson.dc.cfdb.search.SdkTasklistSearch;
import com.aylson.dc.cfdb.service.SdkTasklistService;
import com.aylson.dc.cfdb.vo.SdkTasklistVo;

/**
 * SDK平台广告配置
 * @author Minbo
 */
@Controller
@RequestMapping("/cfdb/sdkTasklist")
public class SdkTasklistController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(SdkTasklistController.class);

	@Autowired
	private SdkTasklistService sdkTasklistService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/cfdb/admin/sdkTasklist/index";
	}
	
	/**
	 * 后台-编辑页面
	 * @param adid
	 * @return
	 */
	@RequestMapping(value = "/admin/toEdit", method = RequestMethod.GET)
	public String toEdit(String adid) {
		if(adid != null){
			SdkTasklistVo sdkTasklistVo = this.sdkTasklistService.getById(adid);
			this.request.setAttribute("sdkTasklistVo", sdkTasklistVo);
		}
		return "/jsp/cfdb/admin/sdkTasklist/add";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(SdkTasklistSearch sdkTasklistSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			sdkTasklistSearch.setIsPage(true);
			List<SdkTasklistVo> list = this.sdkTasklistService.getList(sdkTasklistSearch);
			result.setTotal(this.sdkTasklistService.getRowCount(sdkTasklistSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
}
