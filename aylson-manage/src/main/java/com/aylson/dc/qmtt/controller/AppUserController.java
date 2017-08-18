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
import com.aylson.dc.qmtt.search.AppUserSearch;
import com.aylson.dc.qmtt.service.AppUserService;
import com.aylson.dc.qmtt.vo.AppUserVo;

/**
 * 用户信息管理
 * @author Minbo
 */
@Controller
@RequestMapping("/qmtt/appUser")
public class AppUserController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(AppUserController.class);

	@Autowired
	private AppUserService appUserService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/qmtt/admin/appUser/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(AppUserSearch appUserSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			appUserSearch.setIsPage(true);
			List<AppUserVo> list = this.appUserService.getList(appUserSearch);
			result.setTotal(this.appUserService.getRowCount(appUserSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 后台-添加页面
	 * @param appUserVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(AppUserVo appUserVo) {
		this.request.setAttribute("appUserVo", appUserVo);
		return "/jsp/qmtt/admin/appUser/add";
	}
	
}
