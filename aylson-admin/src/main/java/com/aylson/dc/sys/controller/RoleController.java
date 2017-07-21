package com.aylson.dc.sys.controller;

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
import com.aylson.dc.sys.search.AgentUserSearch;
import com.aylson.dc.sys.search.RoleSearch;
import com.aylson.dc.sys.service.RoleService;
import com.aylson.dc.sys.vo.AgentUserVo;
import com.aylson.dc.sys.vo.RoleVo;

/**
 * 角色管理
 * @author wwx
 * @since  2016-08
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/sys/role")
public class RoleController extends BaseController {
	
	@Autowired
	private RoleService roleService;     //菜单服务
	
	
	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/sys/admin/role/index";
	}	
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(RoleSearch roleSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			roleSearch.setIsPage(true);
			List<RoleVo> list = this.roleService.getList(roleSearch);
			result.setTotal(this.roleService.getRowCount(roleSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据条件获取供应商列表信息
	 * @param dictionarySearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<RoleVo> getList(RoleSearch roleSearch) {
		List<RoleVo> list = this.roleService.getList(roleSearch);
		return list;
	}
	
	/**
	 * 后台-添加页面
	 * @param roleVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(RoleVo roleVo) {
		this.request.setAttribute("roleVo",roleVo);
		return "/jsp/sys/admin/role/add";
	}	
	
	/**
	 * 后台-添加保存
	 * @param roleVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(RoleVo roleVo) {
		Result result = new Result();
		try{
			Boolean flag = this.roleService.add(roleVo);
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
		if(id != null){
			RoleVo roleVo = this.roleService.getById(id);
			this.request.setAttribute("roleVo",roleVo);
		}
		return "/jsp/sys/admin/role/add";
	}	
	
	/**
	 * 后台-编辑保存
	 * @param roleVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(RoleVo roleVo) {
		Result result = new Result();
		try {
			Boolean flag = this.roleService.edit(roleVo);
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
			Boolean flag = this.roleService.deleteById(id);
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
	
	/**
	 * 后台-授权页面
	 * @return
	 */
	@RequestMapping(value = "/admin/toAuthorize", method = RequestMethod.GET)
	public String toAuthorize() {
		return "/jsp/sys/admin/role/authorize";
	}
	
}
