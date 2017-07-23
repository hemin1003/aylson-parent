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
import com.aylson.dc.cfdb.po.ImUsers;
import com.aylson.dc.cfdb.search.ImUsersSearch;
import com.aylson.dc.cfdb.service.ImUsersService;
import com.aylson.utils.DateUtil2;

/**
 * 手机IM用户配置
 * @author Minbo
 */
@Controller
@RequestMapping("/cfdb/imUsers")
public class ImUsersController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(ImUsersController.class);

	@Autowired
	private ImUsersService imUsersService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/cfdb/admin/imUsers/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(ImUsersSearch imUsersSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			imUsersSearch.setIsPage(true);
			List<ImUsers> list = this.imUsersService.getList(imUsersSearch);
			result.setTotal(this.imUsersService.getRowCount(imUsersSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 后台-编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/toEdit", method = RequestMethod.GET)
	public String toEdit(String id) {
		if(id != null){
			ImUsers imUsersVo = this.imUsersService.getById(id);
			this.request.setAttribute("imUsersVo", imUsersVo);
		}
		return "/jsp/cfdb/admin/imUsers/add";
	}
	
	/**
	 * 后台-编辑保存
	 * @param imUsersVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(ImUsers imUsersVo) {
		Result result = new Result();
		try {
			imUsersVo.setUpdateDate(DateUtil2.getCurrentLongDateTime());
			Boolean flag = this.imUsersService.edit(imUsersVo);
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
