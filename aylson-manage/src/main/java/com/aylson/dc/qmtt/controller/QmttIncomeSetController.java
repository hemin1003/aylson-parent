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
import com.aylson.dc.qmtt.search.QmttIncomeSetSearch;
import com.aylson.dc.qmtt.service.QmttIncomeSetService;
import com.aylson.dc.qmtt.vo.QmttIncomeSetVo;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.utils.DateUtil2;
import com.aylson.utils.UUIDUtils;

/**
 * 提现金额配置
 * @author Minbo
 */
@Controller
@RequestMapping("/qmtt/qmttIncomeSet")
public class QmttIncomeSetController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(QmttIncomeSetController.class);

	@Autowired
	private QmttIncomeSetService qmttIncomeSetService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/qmtt/admin/qmttIncomeSet/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(QmttIncomeSetSearch qmttIncomeSetSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			qmttIncomeSetSearch.setIsPage(true);
			List<QmttIncomeSetVo> list = this.qmttIncomeSetService.getList(qmttIncomeSetSearch);
			result.setTotal(this.qmttIncomeSetService.getRowCount(qmttIncomeSetSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 后台-添加页面
	 * @param qmttIncomeSetVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(QmttIncomeSetVo qmttIncomeSetVo) {
		this.request.setAttribute("qmttIncomeSetVo", qmttIncomeSetVo);
		return "/jsp/qmtt/admin/qmttIncomeSet/add";
	}
	
	/**
	 * 后台-添加保存
	 * @param qmttIncomeSetVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(QmttIncomeSetVo qmttIncomeSetVo) {
		Result result = new Result();
		try{
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			qmttIncomeSetVo.setId(UUIDUtils.create());
			String cTime = DateUtil2.getCurrentLongDateTime();
			qmttIncomeSetVo.setCreatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
			qmttIncomeSetVo.setCreateDate(cTime);
			qmttIncomeSetVo.setUpdateDate(cTime);
			Boolean flag = this.qmttIncomeSetService.add(qmttIncomeSetVo);
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
			QmttIncomeSetVo qmttIncomeSetVo = this.qmttIncomeSetService.getById(id);
			this.request.setAttribute("qmttIncomeSetVo", qmttIncomeSetVo);
		}
		return "/jsp/qmtt/admin/qmttIncomeSet/add";
	}
	
	/**
	 * 后台-编辑保存
	 * @param qmttIncomeSetVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(QmttIncomeSetVo qmttIncomeSetVo) {
		Result result = new Result();
		try {
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			qmttIncomeSetVo.setUpdatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
			qmttIncomeSetVo.setUpdateDate(DateUtil2.getCurrentLongDateTime());
			Boolean flag = this.qmttIncomeSetService.edit(qmttIncomeSetVo);
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
			Boolean flag = this.qmttIncomeSetService.deleteById(id);
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
	public Result changeStatus(QmttIncomeSetVo qmttIncomeSetVo) {
		Result result = new Result();
		try {
			if(qmttIncomeSetVo.getStatus() == null){
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
				return result;
			}
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			qmttIncomeSetVo.setUpdatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
			qmttIncomeSetVo.setUpdateDate(DateUtil2.getCurrentLongDateTime());
			Boolean flag = this.qmttIncomeSetService.edit(qmttIncomeSetVo);
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
