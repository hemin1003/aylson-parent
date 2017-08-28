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
import com.aylson.dc.qmtt.search.QmttAppConfigSearch;
import com.aylson.dc.qmtt.service.QmttAppConfigService;
import com.aylson.dc.qmtt.vo.QmttAppConfigVo;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.utils.DateUtil2;
import com.aylson.utils.UUIDUtils;

/**
 * 全局信息配置
 * @author Minbo
 */
@Controller
@RequestMapping("/qmtt/appConfig")
public class QmttAppConfigController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(QmttAppConfigController.class);

	@Autowired
	private QmttAppConfigService qmttAppConfigService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/qmtt/admin/appConfig/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(QmttAppConfigSearch appConfigSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			appConfigSearch.setIsPage(true);
			List<QmttAppConfigVo> list = this.qmttAppConfigService.getList(appConfigSearch);
			result.setTotal(this.qmttAppConfigService.getRowCount(appConfigSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 后台-添加页面
	 * @param appConfigVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(QmttAppConfigVo appConfigVo) {
		this.request.setAttribute("appConfigVo", appConfigVo);
		return "/jsp/qmtt/admin/appConfig/add";
	}
	
	/**
	 * 后台-添加保存
	 * @param appConfigVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(QmttAppConfigVo appConfigVo) {
		Result result = new Result();
		try{
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			appConfigVo.setId(UUIDUtils.create());
			String cTime = DateUtil2.getCurrentLongDateTime();
			appConfigVo.setCreatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
			appConfigVo.setCreateDate(cTime);
			appConfigVo.setUpdateDate(cTime);
			Boolean flag = this.qmttAppConfigService.add(appConfigVo);
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
			QmttAppConfigVo appConfigVo = this.qmttAppConfigService.getById(id);
			this.request.setAttribute("appConfigVo", appConfigVo);
		}
		return "/jsp/qmtt/admin/appConfig/add";
	}
	
	/**
	 * 后台-编辑保存
	 * @param appConfigVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(QmttAppConfigVo appConfigVo) {
		Result result = new Result();
		try {
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			appConfigVo.setUpdatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
			appConfigVo.setUpdateDate(DateUtil2.getCurrentLongDateTime());
			Boolean flag = this.qmttAppConfigService.edit(appConfigVo);
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
			Boolean flag = this.qmttAppConfigService.deleteById(id);
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
