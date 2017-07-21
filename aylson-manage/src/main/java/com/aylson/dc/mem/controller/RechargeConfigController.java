package com.aylson.dc.mem.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.easyui.EasyuiDataGridJson;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.base.GeneralConstant.RechargeConfigStatus;
import com.aylson.dc.mem.search.RechargeConfigSearch;
import com.aylson.dc.mem.service.RechargeConfigService;
import com.aylson.dc.mem.vo.RechargeConfigVo;

/**
 * 充值配置管理
 * @author wwx
 * @since  2016-08
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/mem/rechargeConfig")
public class RechargeConfigController extends BaseController {
	
	@Autowired
	private RechargeConfigService rechargeConfigService;     //充值配置服务
	
	
	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/mem/admin/rechargeConfig/index";
	}	
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(RechargeConfigSearch rechargeConfigSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			rechargeConfigSearch.setIsPage(true);
			List<RechargeConfigVo> list = this.rechargeConfigService.getList(rechargeConfigSearch);
			result.setTotal(this.rechargeConfigService.getRowCount(rechargeConfigSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据条件获取发布列表信息
	 * @param rechargeConfigSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<RechargeConfigVo> getList(RechargeConfigSearch rechargeConfigSearch) {
		List<RechargeConfigVo> list = this.rechargeConfigService.getList(rechargeConfigSearch);
		return list;
	}
	
	/**
	 * 后台-添加页面
	 * @param rechargeConfigVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(RechargeConfigVo rechargeConfigVo) {
		this.request.setAttribute("rechargeConfigVo",rechargeConfigVo);
		return "/jsp/mem/admin/rechargeConfig/add";
	}	
	
	/**
	 * 后台-添加保存
	 * @param rechargeConfigVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(RechargeConfigVo rechargeConfigVo) {
		Result result = new Result();
		try{
			Boolean flag = this.rechargeConfigService.add(rechargeConfigVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
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
	public String toEdit(Integer id) {
		if(id!=null){
			RechargeConfigVo rechargeConfigVo = this.rechargeConfigService.getById(id);
			this.request.setAttribute("rechargeConfigVo",rechargeConfigVo);
		}
		return "/jsp/mem/admin/rechargeConfig/add";
	}	
	
	/**
	 * 后台-编辑保存
	 * @param rechargeConfigVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(RechargeConfigVo rechargeConfigVo) {
		Result result = new Result();
		try {
			Boolean flag = this.rechargeConfigService.edit(rechargeConfigVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = "/admin/changeStatus", method = RequestMethod.POST)
	@ResponseBody
	public Result rechargeConfig(RechargeConfigVo rechargeConfigVo) {
		Result result = new Result();
		try {
			if(rechargeConfigVo.getStatus() == null){
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
				return result;
			}
			if(RechargeConfigStatus.UP == rechargeConfigVo.getStatus()){
				rechargeConfigVo.setPublishTime(new Date());
			}
			Boolean flag = this.rechargeConfigService.edit(rechargeConfigVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	public Result deleteById(Integer id) {
		Result result = new Result();
		try{
			Boolean flag = this.rechargeConfigService.deleteById(id);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "删除成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "删除失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	
}
