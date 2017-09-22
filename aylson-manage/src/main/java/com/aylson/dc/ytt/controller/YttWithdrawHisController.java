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
import com.aylson.dc.ytt.search.YttWithdrawHisSearch;
import com.aylson.dc.ytt.service.YttWithdrawHisService;
import com.aylson.dc.ytt.vo.YttWithdrawHisVo;

/**
 * 用户提现审核管理
 * @author Minbo
 */
@Controller
@RequestMapping("/ytt/yttWithdrawHis")
public class YttWithdrawHisController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(YttWithdrawHisController.class);

	@Autowired
	private YttWithdrawHisService yttWithdrawHisService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/ytt/admin/yttWithdrawHis/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(YttWithdrawHisSearch yttWithdrawHisSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			yttWithdrawHisSearch.setIsPage(true);
			List<YttWithdrawHisVo> list = this.yttWithdrawHisService.getList(yttWithdrawHisSearch);
			result.setTotal(this.yttWithdrawHisService.getRowCount(yttWithdrawHisSearch));
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
			YttWithdrawHisVo yttWithdrawHisVo = this.yttWithdrawHisService.getById(id);
			this.request.setAttribute("yttWithdrawHisVo", yttWithdrawHisVo);
		}
		return "/jsp/ytt/admin/yttWithdrawHis/add";
	}
	
	/**
	 * 后台-编辑保存
	 * @param yttWithdrawHisVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(YttWithdrawHisVo yttWithdrawHisVo) {
		Result result = new Result();
		try {
			result = this.yttWithdrawHisService.updateWithdrawHisInfo(yttWithdrawHisVo, this.request);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
}
