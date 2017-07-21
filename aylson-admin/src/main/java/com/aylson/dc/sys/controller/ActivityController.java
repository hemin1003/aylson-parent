package com.aylson.dc.sys.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.easyui.EasyuiDataGridJson;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.mem.search.WithdrawalsApplySearch;
import com.aylson.dc.mem.vo.WithdrawalsApplyVo;
import com.aylson.dc.sys.search.ActivityRegisterSearch;
import com.aylson.dc.sys.search.ActivitySearch;
import com.aylson.dc.sys.service.ActivityRegisterService;
import com.aylson.dc.sys.service.ActivityService;
import com.aylson.dc.sys.vo.ActivityRegisterVo;
import com.aylson.dc.sys.vo.ActivityVo;
import com.aylson.utils.DateUtil;
import com.aylson.utils.StringUtil;

/**
 * 反馈管理
 * @author wwx
 * @since  2016-08
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/sys/activity")
public class ActivityController extends BaseController {
	
	@Autowired
	private ActivityService activityService;                     //活动服务
	@Autowired
	private ActivityRegisterService activityRegisterService;     //活动报名服务
	
	
	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/sys/admin/activity/index";
	}	
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(ActivitySearch activitySearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			activitySearch.setIsPage(true);
			List<ActivityVo> list = this.activityService.getList(activitySearch);
			result.setTotal(this.activityService.getRowCount(activitySearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/registerList", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson registerList(ActivityRegisterSearch activityRegisterSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			activityRegisterSearch.setIsPage(true);
			List<ActivityRegisterVo> list = this.activityRegisterService.getList(activityRegisterSearch);
			result.setTotal(this.activityRegisterService.getRowCount(activityRegisterSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据条件获取列表信息
	 * @param activitySearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<ActivityVo> getList(ActivitySearch activitySearch) {
		List<ActivityVo> list = this.activityService.getList(activitySearch);
		return list;
	}
	
	/**
	 * 后台-添加页面
	 * @param activityVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(ActivityVo activityVo) {
		this.request.setAttribute("activityVo",activityVo);
		return "/jsp/sys/admin/activity/add";
	}	
	
	/**
	 * 后台-添加保存
	 * @param activityVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(ActivityVo activityVo) {
		Result result = new Result();
		try{
			if(StringUtil.isNotEmpty(activityVo.getActBeginTimeStr())){
				activityVo.setActBeginTime(DateUtil.format(activityVo.getActBeginTimeStr()));
			}
			if(StringUtil.isNotEmpty(activityVo.getActEndTimeStr())){
				activityVo.setActEndTime(DateUtil.format(activityVo.getActEndTimeStr()));
			}
			Boolean flag = this.activityService.add(activityVo);
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
			ActivityVo activityVo = this.activityService.getById(id);
			if(activityVo.getActBeginTime() != null){
				activityVo.setActBeginTimeStr(DateUtil.format(activityVo.getActBeginTime()));
			}
			if(activityVo.getActEndTime() != null){
				activityVo.setActEndTimeStr(DateUtil.format(activityVo.getActEndTime()));
			}
			this.request.setAttribute("activityVo",activityVo);
		}
		return "/jsp/sys/admin/activity/add";
	}	
	
	/**
	 * 后台-编辑保存
	 * @param activityVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(ActivityVo activityVo) {
		Result result = new Result();
		try {
			Boolean flag = this.activityService.edit(activityVo);
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
			Boolean flag = this.activityService.deleteById(id);
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
	
	@RequestMapping(value = "/admin/changeState", method = RequestMethod.POST)
	@ResponseBody
	public Result changeState(ActivityVo activityVo) {
		Result result = new Result();
		try {
			if(activityVo.getState() == null){
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
				return result;
			}
			Boolean flag = this.activityService.edit(activityVo);
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
	
	@RequestMapping(value = "/admin/exportRegister", method = RequestMethod.GET)
	@ResponseBody
	public void exportRegister(Integer actId, String actTheme, HttpServletRequest request, HttpServletResponse response) {
		try{
			response.setContentType("application/vnd.ms-excel");    
			String excelName = null;
			excelName = URLEncoder.encode("【"+actTheme+"】活动报名列表", "UTF-8");
			response.setHeader("Content-disposition", "attachment;filename="+ excelName+".xls"); 
			response.setCharacterEncoding("utf-8");
			OutputStream out = response.getOutputStream();
			if(actId != null){
				ActivityRegisterSearch activityRegisterSearch = new ActivityRegisterSearch();
				activityRegisterSearch.setActId(actId);
				List<ActivityRegisterVo> list = this.activityRegisterService.getList(activityRegisterSearch);
				if(list != null && list.size() > 0){
					// 创建一个工作簿
					HSSFWorkbook workBook = new HSSFWorkbook();
					// 创建一个工作表
					HSSFSheet sheet = workBook.createSheet();
					// 设置表格默认列宽度为15个字节  
			        sheet.setDefaultColumnWidth((short)30);  
					HSSFRow row = sheet.createRow(0);
					HSSFCell cell[] = new HSSFCell[5];
					for (int i = 0; i < 5; i++) {
						cell[i] = row.createCell(i);
					}
					cell[0].setCellValue("报名人姓名");
					cell[1].setCellValue("报名人电话");
					cell[2].setCellValue("工作单位");
					cell[3].setCellValue("报名时间");
					for(int i=0; i<list.size(); i++){
						ActivityRegisterVo temp = list.get(i);
						HSSFRow row1 = sheet.createRow(i+1);
						HSSFCell cellData[] = new HSSFCell[4];
						for (int j = 0; j < 4; j++) {
							cellData[j] = row1.createCell(j);
						}
						cellData[0].setCellValue(temp.getRegisterName());
						cellData[1].setCellValue(temp.getRegisterPhone());
						cellData[2].setCellValue(temp.getWorkUnit());
						cellData[3].setCellValue(DateUtil.format(temp.getRegisterTime()));
					}
					workBook.write(out);
					out.flush();
					out.close();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
