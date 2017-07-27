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
import com.aylson.dc.cfdb.search.WithdrawHisSearch;
import com.aylson.dc.cfdb.service.WithdrawHisService;
import com.aylson.dc.cfdb.vo.WithdrawHisVo;

/**
 * 用户提现审核管理
 * @author Minbo
 */
@Controller
@RequestMapping("/cfdb/withdrawHis")
public class WithdrawHisController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(WithdrawHisController.class);

	@Autowired
	private WithdrawHisService withdrawHisService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/cfdb/admin/withdrawHis/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(WithdrawHisSearch withdrawHisSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			withdrawHisSearch.setIsPage(true);
			List<WithdrawHisVo> list = this.withdrawHisService.getList(withdrawHisSearch);
			result.setTotal(this.withdrawHisService.getRowCount(withdrawHisSearch));
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
			WithdrawHisVo withdrawHisVo = this.withdrawHisService.getById(id);
			this.request.setAttribute("withdrawHisVo", withdrawHisVo);
		}
		return "/jsp/cfdb/admin/withdrawHis/add";
	}
	
	/**
	 * 后台-编辑保存
	 * @param withdrawHisVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(WithdrawHisVo withdrawHisVo) {
		Result result = new Result();
		try {
			result = this.withdrawHisService.updateWithdrawHisInfo(withdrawHisVo, this.request);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
}
