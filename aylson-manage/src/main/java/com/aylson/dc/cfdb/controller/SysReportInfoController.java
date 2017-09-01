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
import com.aylson.utils.DateUtil2;
import com.aylson.utils.StringUtil;

import net.sf.json.JSONObject;

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
	
	/**
	 * 获取图标数据
	 * @return list
	 */
	@RequestMapping(value = "/admin/listMap", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject listMap(){
		try{
			Map<String, Object> map = new HashMap<>();
			//横坐标，统计七天的数据，含今天
			List<Object> list = new ArrayList<>();
			list.add(DateUtil2.getCurrentDateByNum(-6));
			list.add(DateUtil2.getCurrentDateByNum(-5));
			list.add(DateUtil2.getCurrentDateByNum(-4));
			list.add(DateUtil2.getCurrentDateByNum(-3));
			list.add(DateUtil2.getCurrentDateByNum(-2));
			list.add(DateUtil2.getCurrentDateByNum(-1));
			list.add(DateUtil2.getCurrentDateByNum(0));
			map.put("categories", list);
			
			//新增用户数
			list = new ArrayList<>();
			list.add(this.getResult(-6, 101));
			list.add(this.getResult(-5, 101));
			list.add(this.getResult(-4, 101));
			list.add(this.getResult(-3, 101));
			list.add(this.getResult(-2, 101));
			list.add(this.getResult(-1, 101));
			list.add(this.getResult(0, 101));
			map.put("value1", list);
			
			//做任务总人数
			list = new ArrayList<>();
			list.add(this.getResult(-6, 102));
			list.add(this.getResult(-5, 102));
			list.add(this.getResult(-4, 102));
			list.add(this.getResult(-3, 102));
			list.add(this.getResult(-2, 102));
			list.add(this.getResult(-1, 102));
			list.add(this.getResult(0, 102));
			map.put("value2", list);
			
			//完成任务总数
			list = new ArrayList<>();
			list.add(this.getResult(-6, 103));
			list.add(this.getResult(-5, 103));
			list.add(this.getResult(-4, 103));
			list.add(this.getResult(-3, 103));
			list.add(this.getResult(-2, 103));
			list.add(this.getResult(-1, 103));
			list.add(this.getResult(0, 103));
			map.put("value3", list);
			
			//用户总收入
			list = new ArrayList<>();
			list.add(this.getResult(-6, 104));
			list.add(this.getResult(-5, 104));
			list.add(this.getResult(-4, 104));
			list.add(this.getResult(-3, 104));
			list.add(this.getResult(-2, 104));
			list.add(this.getResult(-1, 104));
			list.add(this.getResult(0, 104));
			map.put("value4", list);
			
			//公司总收入
			list = new ArrayList<>();
			list.add(this.getResult(-6, 105));
			list.add(this.getResult(-5, 105));
			list.add(this.getResult(-4, 105));
			list.add(this.getResult(-3, 105));
			list.add(this.getResult(-2, 105));
			list.add(this.getResult(-1, 105));
			list.add(this.getResult(0, 105));
			map.put("value5", list);
			
			JSONObject json = JSONObject.fromObject(map);
			return json;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * @param day 0-6
	 * @param type 101=当日新增用户数, 102=当日做任务总人数, 103=当日完成任务总数, 104=当日用户总收入, 105=当日公司总收入
	 * @return
	 */
	private String getResult(int day, int type) {
		Map<String, Object> params = new HashMap<>();
		params.put("currentTime", DateUtil2.getCurrentDateByNum(day));
		switch (type) {
			case 101:
				return this.sysReportInfoService.selectNewUserOfDay(params).getValue();
			case 102:
				return this.sysReportInfoService.selectUserCountOfTask(params).getValue();	
			case 103:
				return this.sysReportInfoService.selectTaskCountOfFinished(params).getValue();
			case 104:
				SysReportInfoVo sysReportInfoVo = this.sysReportInfoService.selectUserIncomeOfDay(params);
				//格式化，保留三位小数，四舍五入
				DecimalFormat dFormat = new DecimalFormat("#0.000");
				sysReportInfoVo.setValue(dFormat.format(Double.valueOf(sysReportInfoVo.getValue())));
				return sysReportInfoVo.getValue();
			case 105:
				SysReportInfoVo sysReportInfoVo2 = this.sysReportInfoService.selectSysIncomeOfDay(params);
				//格式化，保留三位小数，四舍五入
				DecimalFormat dFormat2 = new DecimalFormat("#0.000");
				sysReportInfoVo2.setValue(dFormat2.format(Double.valueOf(sysReportInfoVo2.getValue())));
				return sysReportInfoVo2.getValue();
			default:
				throw new RuntimeException("错误类型，请检查。day=" + day + ", type=" + type);
		}
	}
}
