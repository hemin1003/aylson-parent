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
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.qmtt.search.QmttWithdrawHisSearch;
import com.aylson.dc.qmtt.service.QmttWithdrawHisService;
import com.aylson.dc.qmtt.vo.QmttWithdrawHisVo;

/**
 * 用户提现审核管理
 * @author Minbo
 */
@Controller
@RequestMapping("/qmtt/qmttWithdrawHis")
public class QmttWithdrawHisController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(QmttWithdrawHisController.class);

	@Autowired
	private QmttWithdrawHisService qmttWithdrawHisService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/qmtt/admin/qmttWithdrawHis/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(QmttWithdrawHisSearch qmttWithdrawHisSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			qmttWithdrawHisSearch.setIsPage(true);
			List<QmttWithdrawHisVo> list = this.qmttWithdrawHisService.getList(qmttWithdrawHisSearch);
			result.setTotal(this.qmttWithdrawHisService.getRowCount(qmttWithdrawHisSearch));
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
			QmttWithdrawHisVo qmttWithdrawHisVo = this.qmttWithdrawHisService.getById(id);
			this.request.setAttribute("qmttWithdrawHisVo", qmttWithdrawHisVo);
		}
		return "/jsp/qmtt/admin/qmttWithdrawHis/add";
	}
	
	/**
	 * 后台-编辑保存
	 * @param qmttWithdrawHisVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(QmttWithdrawHisVo qmttWithdrawHisVo) {
		Result result = new Result();
		try {
			result = this.qmttWithdrawHisService.updateWithdrawHisInfo(qmttWithdrawHisVo, this.request);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
}
