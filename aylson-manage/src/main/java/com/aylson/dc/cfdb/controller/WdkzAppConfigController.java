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
import com.aylson.dc.cfdb.search.WdkzAppConfigSearch;
import com.aylson.dc.cfdb.service.WdkzAppConfigService;
import com.aylson.dc.cfdb.vo.TasklistVo;
import com.aylson.dc.cfdb.vo.WdkzAppConfigVo;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.utils.DateUtil2;
import com.aylson.utils.UUIDUtils;

/**
 * 微多开助手，app版本信息配置
 * @author Minbo
 */
@Controller
@RequestMapping("/cfdb/wdkzAppConfig")
public class WdkzAppConfigController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(WdkzAppConfigController.class);

	@Autowired
	private WdkzAppConfigService wdkzAppConfigService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/cfdb/admin/wdkzAppConfig/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(WdkzAppConfigSearch wdkzAppConfigSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			wdkzAppConfigSearch.setIsPage(true);
			List<WdkzAppConfigVo> list = this.wdkzAppConfigService.getList(wdkzAppConfigSearch);
			result.setTotal(this.wdkzAppConfigService.getRowCount(wdkzAppConfigSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 后台-添加页面
	 * @param wdkzAppConfigVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(WdkzAppConfigVo wdkzAppConfigVo) {
		this.request.setAttribute("wdkzAppConfigVo", wdkzAppConfigVo);
		return "/jsp/cfdb/admin/wdkzAppConfig/add";
	}
	
	/**
	 * 后台-添加保存
	 * @param wdkzAppConfigVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(WdkzAppConfigVo wdkzAppConfigVo) {
		Result result = new Result();
		try{
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			wdkzAppConfigVo.setId(UUIDUtils.create());
			String cTime = DateUtil2.getCurrentLongDateTime();
			wdkzAppConfigVo.setCreatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
			wdkzAppConfigVo.setCreateDate(cTime);
			wdkzAppConfigVo.setUpdateDate(cTime);
			Boolean flag = this.wdkzAppConfigService.add(wdkzAppConfigVo);
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
			WdkzAppConfigVo wdkzAppConfigVo = this.wdkzAppConfigService.getById(id);
			this.request.setAttribute("wdkzAppConfigVo", wdkzAppConfigVo);
		}
		return "/jsp/cfdb/admin/wdkzAppConfig/add";
	}
	
	/**
	 * 后台-编辑保存
	 * @param wdkzAppConfigVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(WdkzAppConfigVo wdkzAppConfigVo) {
		Result result = new Result();
		try {
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			wdkzAppConfigVo.setUpdatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
			wdkzAppConfigVo.setUpdateDate(DateUtil2.getCurrentLongDateTime());
			Boolean flag = this.wdkzAppConfigService.edit(wdkzAppConfigVo);
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
			Boolean flag = this.wdkzAppConfigService.deleteById(id);
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
	public Result changeStatus(WdkzAppConfigVo wdkzAppConfigVo) {
		Result result = new Result();
		try {
			if(wdkzAppConfigVo.getStatus() == null){
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
				return result;
			}
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			wdkzAppConfigVo.setUpdatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
			wdkzAppConfigVo.setUpdateDate(DateUtil2.getCurrentLongDateTime());
			Boolean flag = this.wdkzAppConfigService.edit(wdkzAppConfigVo);
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
