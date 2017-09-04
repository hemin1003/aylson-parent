package com.aylson.dc.cfdb.controller;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
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
	 * 获取详细列表数据
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(SysReportInfoSearch sysReportInfoSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			sysReportInfoSearch.setIsPage(true);
			List<SysReportInfoList> list = new ArrayList<>();
			Map<String, Object> params = new HashMap<>();
			params.put("currentTime", sysReportInfoSearch.getCurrentTime());
			for (int i = 0; i < sysReportInfoSearch.getNum(); i++) {
				int j = 0;
				if(i > 0) {
					j = 0 - i;
				}
				SysReportInfoList sysReportInfo = new SysReportInfoList();
				sysReportInfo.setDateStr(DateUtil2.getCurrentDateByNum(j));;
				sysReportInfo.setNewUserOfDay(this.getResult(j, 101));
				sysReportInfo.setUserCountOfTask(this.getResult(j, 102));
				sysReportInfo.setTaskCountOfFinished(this.getResult(j, 103));
				sysReportInfo.setUserIncomeOfDay(this.getResult(j, 104));
				sysReportInfo.setSysIncomeOfDay(this.getResult(j, 105));
				list.add(sysReportInfo);
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
	 * 获取顶部三栏数据
	 * @return list
	 */
	@RequestMapping(value = "/admin/listTop", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject listTop(){
		try{
			Map<String, Object> map = new HashMap<>();
			//公司今日收入
			map.put("todayIncome", this.getResult(0, 105));
			//公司昨日收入
			map.put("yesterdayIncome", this.getResult(-1, 105));
			//公司总收入
			map.put("allIncome", this.getResult(0, 107));
			JSONObject json = JSONObject.fromObject(map);
			return json;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 获取折线图数据
	 * @return list
	 */
	@RequestMapping(value = "/admin/listMap", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject listMap(int num){
		try{
			Map<String, Object> map = new HashMap<>();
			//横坐标，统计七天的数据，含今天
			List<Object> list = new ArrayList<>();
			for (int i = 0; i < num; i++) {
				int j = 0;
				if(i > 0) {
					j = 0 - i;
				}
				list.add(DateUtil2.getCurrentDateByNum(j));
			}
			Collections.reverse(list);
			map.put("categories", list);
			
			//新增用户数
			list = new ArrayList<>();
			for (int i = 0; i < num; i++) {
				int j = 0;
				if(i > 0) {
					j = 0 - i;
				}
				list.add(this.getResult(j, 101));
			}
			Collections.reverse(list);
			map.put("value1", list);
			
			//做任务总人数
			list = new ArrayList<>();
			for (int i = 0; i < num; i++) {
				int j = 0;
				if(i > 0) {
					j = 0 - i;
				}
				list.add(this.getResult(j, 102));
			}
			Collections.reverse(list);
			map.put("value2", list);
			
			//完成任务总数
			list = new ArrayList<>();
			for (int i = 0; i < num; i++) {
				int j = 0;
				if(i > 0) {
					j = 0 - i;
				}
				list.add(this.getResult(j, 103));
			}
			Collections.reverse(list);
			map.put("value3", list);
			
			//用户总收入
			list = new ArrayList<>();
			for (int i = 0; i < num; i++) {
				int j = 0;
				if(i > 0) {
					j = 0 - i;
				}
				list.add(this.getResult(j, 104));
			}
			Collections.reverse(list);
			map.put("value4", list);
			
			//公司总收入
			list = new ArrayList<>();
			for (int i = 0; i < num; i++) {
				int j = 0;
				if(i > 0) {
					j = 0 - i;
				}
				list.add(this.getResult(j, 105));
			}
			Collections.reverse(list);
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
	 * @param type 101=当日新增用户数, 102=当日做任务总人数, 103=当日完成任务总数, 104=当日用户总收入, 105=当日公司总收入, 106=公司总收入, 107=公司总收入
	 * @return
	 */
	private String getResult(int day, int type) {
		Map<String, Object> params = new HashMap<>();
		params.put("currentTime", DateUtil2.getCurrentDateByNum(day));
		//格式化，保留三位小数，四舍五入
		DecimalFormat dFormat = new DecimalFormat("#0.000");
		SysReportInfoVo sysReportInfoVo = new SysReportInfoVo();
		switch (type) {
			case 101:
				return this.sysReportInfoService.selectNewUserOfDay(params).getValue();
			case 102:
				return this.sysReportInfoService.selectUserCountOfTask(params).getValue();	
			case 103:
				return this.sysReportInfoService.selectTaskCountOfFinished(params).getValue();
			case 104:
				sysReportInfoVo = this.sysReportInfoService.selectUserIncomeOfDay(params);
				sysReportInfoVo.setValue(dFormat.format(Double.valueOf(sysReportInfoVo.getValue())));
				return sysReportInfoVo.getValue();
			case 105:
				sysReportInfoVo = this.sysReportInfoService.selectSysIncomeOfDay(params);
				sysReportInfoVo.setValue(dFormat.format(Double.valueOf(sysReportInfoVo.getValue())));
				return sysReportInfoVo.getValue();
			case 106:
				sysReportInfoVo = this.sysReportInfoService.selectAllUserIncomeOfDay();
				sysReportInfoVo.setValue(dFormat.format(Double.valueOf(sysReportInfoVo.getValue())));
				return sysReportInfoVo.getValue();
			case 107:
				sysReportInfoVo = this.sysReportInfoService.selectAllSysIncomeOfDay();
				sysReportInfoVo.setValue(dFormat.format(Double.valueOf(sysReportInfoVo.getValue())));
				return sysReportInfoVo.getValue();
			default:
				throw new RuntimeException("错误类型，请检查。day=" + day + ", type=" + type);
		}
	}
	
	public class SysReportInfoList implements Serializable{
		private static final long serialVersionUID = -79583709832163829L;
		
		private String dateStr;
		private String newUserOfDay;
		private String userCountOfTask;
		private String taskCountOfFinished;
		private String userIncomeOfDay;
		private String sysIncomeOfDay;
		
		public String getDateStr() {
			return dateStr;
		}
		public void setDateStr(String dateStr) {
			this.dateStr = dateStr;
		}
		public String getNewUserOfDay() {
			return newUserOfDay;
		}
		public void setNewUserOfDay(String newUserOfDay) {
			this.newUserOfDay = newUserOfDay;
		}
		public String getUserCountOfTask() {
			return userCountOfTask;
		}
		public void setUserCountOfTask(String userCountOfTask) {
			this.userCountOfTask = userCountOfTask;
		}
		public String getTaskCountOfFinished() {
			return taskCountOfFinished;
		}
		public void setTaskCountOfFinished(String taskCountOfFinished) {
			this.taskCountOfFinished = taskCountOfFinished;
		}
		public String getUserIncomeOfDay() {
			return userIncomeOfDay;
		}
		public void setUserIncomeOfDay(String userIncomeOfDay) {
			this.userIncomeOfDay = userIncomeOfDay;
		}
		public String getSysIncomeOfDay() {
			return sysIncomeOfDay;
		}
		public void setSysIncomeOfDay(String sysIncomeOfDay) {
			this.sysIncomeOfDay = sysIncomeOfDay;
		}
	}
}
