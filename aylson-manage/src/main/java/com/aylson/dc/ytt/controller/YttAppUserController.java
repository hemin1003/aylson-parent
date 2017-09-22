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
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.ytt.search.YttAppUserSearch;
import com.aylson.dc.ytt.service.YttAppUserService;
import com.aylson.dc.ytt.vo.YttAppUserVo;

/**
 * 用户信息管理
 * @author Minbo
 */
@Controller
@RequestMapping("/ytt/appUser")
public class YttAppUserController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(YttAppUserController.class);

	@Autowired
	private YttAppUserService appUserService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/ytt/admin/appUser/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(YttAppUserSearch appUserSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			appUserSearch.setIsPage(true);
			List<YttAppUserVo> list = this.appUserService.getList(appUserSearch);
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
	public String toAdd(YttAppUserVo appUserVo) {
		this.request.setAttribute("appUserVo", appUserVo);
		return "/jsp/ytt/admin/appUser/add";
	}
	
	
	/**
	 * 后台-编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/toEdit", method = RequestMethod.GET)
	public String toEdit(String phoneNum) {
		if(phoneNum != null){
			YttAppUserVo appUserVo = this.appUserService.getById(phoneNum);
			this.request.setAttribute("appUserVo", appUserVo);
		}
		return "/jsp/ytt/admin/appUser/add";
	}
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/deleteById", method = RequestMethod.POST)
	@ResponseBody
	public Result deleteById(String id) {
		Result result = new Result();
		try{
			Boolean flag = this.appUserService.deleteById(id);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "删除成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "删除失败");
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
}
