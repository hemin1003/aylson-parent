package com.aylson.dc.cfdb.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.easyui.EasyuiDataGridJson;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.dc.cfdb.search.SysReportInfoSearch;
import com.aylson.dc.cfdb.service.SysReportInfoService;
import com.aylson.dc.cfdb.vo.SysReportInfoVo;
import com.aylson.utils.StringUtil;

/**
 * 统计报表数据查询
 * @author Minbo
 */
@Controller
@RequestMapping("/cfdb/sysReportInfo")
public class SysReportInfoController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(SysReportInfoController.class);

	@Autowired
	private SysReportInfoService sysReportInfoService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/cfdb/admin/sysReportInfo/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(SysReportInfoSearch sysReportInfoSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			sysReportInfoSearch.setIsPage(true);
			List<SysReportInfoVo> list = new ArrayList<>();
			Map<String, Object> params = new HashMap<>();
			params.put("currentTime", sysReportInfoSearch.getCurrentTime());
			SysReportInfoVo sysReportInfoVo = new SysReportInfoVo();
			if(null != sysReportInfoSearch.getType() 
					&& !StringUtil.null2Str(sysReportInfoSearch.getCurrentTime()).equals("")) {
				if(sysReportInfoSearch.getType() == 101) {
					sysReportInfoVo = this.sysReportInfoService.selectNewUserOfDay(params);
				}else if(sysReportInfoSearch.getType() == 102) {
					sysReportInfoVo = this.sysReportInfoService.selectUserCountOfTask(params);
				}else if(sysReportInfoSearch.getType() == 103) {
					sysReportInfoVo = this.sysReportInfoService.selectTaskCountOfFinished(params);
				}else if(sysReportInfoSearch.getType() == 104) {
					sysReportInfoVo = this.sysReportInfoService.selectUserIncomeOfDay(params);
					//格式化，保留三位小数，四舍五入
					DecimalFormat dFormat = new DecimalFormat("#0.000");
					sysReportInfoVo.setValue(dFormat.format(Double.valueOf(sysReportInfoVo.getValue())));
				}else if(sysReportInfoSearch.getType() == 105) {
					sysReportInfoVo = this.sysReportInfoService.selectSysIncomeOfDay(params);
					//格式化，保留三位小数，四舍五入
					DecimalFormat dFormat = new DecimalFormat("#0.000");
					sysReportInfoVo.setValue(dFormat.format(Double.valueOf(sysReportInfoVo.getValue())));
				}
				list.add(sysReportInfoVo);
			}
			result.setTotal(Long.valueOf(list.size()));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
}
