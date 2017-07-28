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
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.cfdb.search.AppUpgradeSearch;
import com.aylson.dc.cfdb.service.AppUpgradeService;
import com.aylson.dc.cfdb.vo.AppUpgradeVo;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.utils.DateUtil2;
import com.aylson.utils.UUIDUtils;

/**
 * APP版本升级管理
 * @author Minbo
 */
@Controller
@RequestMapping("/cfdb/appUpgrade")
public class AppUpgradeController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(AppUpgradeController.class);

	@Autowired
	private AppUpgradeService appUpgradeService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/cfdb/admin/appUpgrade/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(AppUpgradeSearch appUpgradeSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			appUpgradeSearch.setIsPage(true);
			List<AppUpgradeVo> list = this.appUpgradeService.getList(appUpgradeSearch);
			result.setTotal(this.appUpgradeService.getRowCount(appUpgradeSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 后台-添加页面
	 * @param appUpgradeVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(AppUpgradeVo appUpgradeVo) {
		this.request.setAttribute("appUpgradeVo", appUpgradeVo);
		return "/jsp/cfdb/admin/appUpgrade/add";
	}
	
	/**
	 * 后台-添加保存
	 * @param appUpgradeVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(AppUpgradeVo appUpgradeVo) {
		Result result = new Result();
		try{
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			appUpgradeVo.setId(UUIDUtils.create());
			String cTime = DateUtil2.getCurrentLongDateTime();
			appUpgradeVo.setCreatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
			appUpgradeVo.setCreateDate(cTime);
			appUpgradeVo.setUpdateDate(cTime);
			Boolean flag = this.appUpgradeService.add(appUpgradeVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 后台-编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/toEdit", method = RequestMethod.GET)
	public String toEdit(String id) {
		if(id != null){
			AppUpgradeVo appUpgradeVo = this.appUpgradeService.getById(id);
			this.request.setAttribute("appUpgradeVo", appUpgradeVo);
		}
		return "/jsp/cfdb/admin/appUpgrade/add";
	}
	
	/**
	 * 后台-编辑保存
	 * @param appUpgradeVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(AppUpgradeVo appUpgradeVo) {
		Result result = new Result();
		try {
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			appUpgradeVo.setUpdatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
			appUpgradeVo.setUpdateDate(DateUtil2.getCurrentLongDateTime());
			Boolean flag = this.appUpgradeService.edit(appUpgradeVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
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
			Boolean flag = this.appUpgradeService.deleteById(id);
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
	
	@RequestMapping(value = "/admin/changeStatus", method = RequestMethod.POST)
	@ResponseBody
	public Result changeStatus(AppUpgradeVo appUpgradeVo) {
		Result result = new Result();
		try {
			if(appUpgradeVo.getStatus() == null){
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
				return result;
			}
			appUpgradeVo.setUpdateDate(DateUtil2.getCurrentLongDateTime());
			Boolean flag = this.appUpgradeService.edit(appUpgradeVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
}
