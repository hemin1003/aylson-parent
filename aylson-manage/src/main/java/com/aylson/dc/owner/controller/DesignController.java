package com.aylson.dc.owner.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.constants.SysConstant.BillCodePrefix;
import com.aylson.core.easyui.EasyuiDataGridJson;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.base.OwnerGeneralConstant.DesignState;
import com.aylson.dc.base.OwnerGeneralConstant.DesignType;
import com.aylson.dc.owner.search.DesignSearch;
import com.aylson.dc.owner.service.AppointmentService;
import com.aylson.dc.owner.service.DesignDetailDWService;
import com.aylson.dc.owner.service.DesignDetailSRService;
import com.aylson.dc.owner.service.DesignService;
import com.aylson.dc.owner.vo.AppointmentVo;
import com.aylson.dc.owner.vo.DesignVo;
import com.aylson.utils.BillNumUtils;
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
@RequestMapping("/owner/design")
public class DesignController extends BaseController {
	
	@Autowired
	private DesignService designService;                     //在线预约服务
	@Autowired
	private AppointmentService appointmentService;                     //在线预约服务
	@Autowired
	private DesignDetailDWService designDetailDWService;                     //在线预约服务
	@Autowired
	private DesignDetailSRService designDetailSRService;                     //在线预约服务

	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(DesignSearch designSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
//			designSearch.setIsPage(true);
			List<DesignVo> list = this.designService.getList(designSearch);
			result.setTotal(this.designService.getRowCount(designSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 添加设计信息表
	 * @param appointId
	 * @param designType
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/toAddDesign", method = RequestMethod.GET)
	public String toDesign(Integer appointId,Integer designType, Model model) {
		AppointmentVo appointmentVo = this.appointmentService.getById(appointId);
		DesignVo designVo = new DesignVo();
		designVo.setAppointId(appointId);
		designVo.setDesignType(designType);
		designVo.setClientName(appointmentVo.getName());
		designVo.setClientPhone(appointmentVo.getMobilePhone());
		designVo.setClientAddress(appointmentVo.getProvince()+appointmentVo.getCity()+appointmentVo.getArea()+appointmentVo.getAddress());
		designVo.setOrderTimeStr(DateUtil.format(new Date()));
		designVo.setBillCode( BillNumUtils.getBillCode(BillCodePrefix.DESIGN,appointmentVo.getMobilePhone()));
		model.addAttribute("designVo", designVo);
		if(1 == designType){
			return "/jsp/owner/admin/design/addDoor";
		}else if(2 == designType){
			return "/jsp/owner/admin/design/addWindow";
		}else if(3 == designType){
			return "/jsp/owner/admin/design/addSunRoom";
		}
		return null;
	}	
	
	/**
	 * 保存设计信息表信息
	 * @param designVo
	 * @param designDetailDWVoListJson
	 * @return
	 */
	@RequestMapping(value = "/admin/addDesign", method = RequestMethod.POST)
	@ResponseBody
	public Result addDesign(DesignVo designVo,String designDetailDWVoListJson) {
		Result result = new Result();
		try {
			result = this.designService.addDesign(designVo, designDetailDWVoListJson);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 修改设计信息表
	 * @param designId
	 * @param designType
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/toEditDesign", method = RequestMethod.GET)
	public String toEditDesign(Integer designId,Integer designType, Model model) {
		String pageUrl = null;
		if(designId != null && designType != null){
			DesignVo designVo = this.designService.getDesign(designId, designType);
			model.addAttribute("designVo", designVo);
			if(DesignType.DOOR == designType.intValue()){
				pageUrl =  "/jsp/owner/admin/design/editDoor";
			}else if(DesignType.WINDOW == designType.intValue()){
				pageUrl =  "/jsp/owner/admin/design/editWindow";
			}else if(DesignType.SUMROOM == designType.intValue()){
				pageUrl = "/jsp/owner/admin/design/editSunRoom";
			}
		}
		return pageUrl;
	}	
	
	/**
	 * 修改设计信息内容
	 * @param designVo
	 * @param designDetailDWVoListJson
	 * @return
	 */
	@RequestMapping(value = "/admin/editDesign", method = RequestMethod.POST)
	@ResponseBody
	public Result editDesign(DesignVo designVo,String designDetailDWVoListJson) {
		Result result = new Result();
		try {
			result = this.designService.editDesign(designVo, designDetailDWVoListJson);
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
	@RequestMapping(value = "/admin/delDesign", method = RequestMethod.POST)
	@ResponseBody
	public Result delDesign(Integer designId, Integer designType) {
		Result result = new Result();
		try{
			result = this.designService.delDesign(designId, designType);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 提交设计大样图
	 * @param appointId
	 * @return
	 */
	@RequestMapping(value = "/admin/applyDraw", method = RequestMethod.POST)
	@ResponseBody
	public Result applyDraw(Integer appointId) {
		Result result = new Result();
		try{
			result = this.designService.applyDraw(appointId);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 确认大样图-页面
	 * @param designId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/toConfirmDraw", method = RequestMethod.GET)
	public String toConfirmDraw(Integer designId, Model model) {
		if(designId != null){
			DesignVo designVo = this.designService.getById(designId);
			model.addAttribute("designVo", designVo);
		}
		return "/jsp/owner/admin/design/confirmDraw";
	}	
	
	/**
	 * 确认大样图-保存
	 * @param designVo
	 * @return
	 */
	@RequestMapping(value = "/admin/confirmDraw", method = RequestMethod.POST)
	@ResponseBody
	public Result confirmDraw(DesignVo designVo) {
		Result result = new Result();
		try {
			result = this.designService.confirmDraw(designVo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 上传大样图-页面
	 * @param designId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/toUploadDraw", method = RequestMethod.GET)
	public String toUploadDraw(Integer designId, Model model) {
		if(designId != null){
			DesignVo designVo = this.designService.getById(designId);
			model.addAttribute("designVo", designVo);
		}
		return "/jsp/owner/admin/design/draw";
	}	
	
	/**
	 * 上传大样图保存
	 * @param designVo
	 * @return
	 */
	@RequestMapping(value = "/admin/uploadDraw", method = RequestMethod.POST)
	@ResponseBody
	public Result uploadDraw(DesignVo designVo) {
		Result result = new Result();
		try {
			if(StringUtil.isEmpty(designVo.getDrawUrl())){
				result.setError(ResultCode.CODE_STATE_4006, "请先上传大样图");
				return result;
			}
			if(DesignState.NOTSATISFY_DRAW == designVo.getState()){//如果是不满意，那么是重新上传
				designVo.setState(DesignState.HAD_DRAW_AGAIN);
			}else{
				designVo.setState(DesignState.HAD_DRAW);
			}
			Boolean flag = this.designService.edit(designVo);
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
	
	
	/*
	 * 订货报价单页面
	 
	@RequestMapping(value = "/admin/toQuotation", method = RequestMethod.GET)
	public String toQuotation(Integer appointId,Integer designType, Model model) {
		AppointmentVo appointmentVo = this.appointmentService.getById(appointId);
		QuotationVo quotationVo = new QuotationVo();
		quotationVo.setAppointId(appointId);
		quotationVo.setClientName(appointmentVo.getName());
		quotationVo.setClientPhone(appointmentVo.getMobilePhone());
		quotationVo.setClientAddress(appointmentVo.getProvince()+appointmentVo.getCity()+appointmentVo.getArea()+appointmentVo.getAddress());
		model.addAttribute("quotationVo", quotationVo);
		if(1 == designType){
			return "/jsp/owner/admin/quotation/addDW";
		}else if(2 == designType){
			return "/jsp/owner/admin/quotation/addDW";
		}else if(3 == designType){
			return "/jsp/owner/admin/quotation/addSR";
		}
		return null;
	}	*/
	
	/**
	 * 查看设计信息表详情
	 * @param appointId
	 * @param designType
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/toViewDesign", method = RequestMethod.GET)
	public String toViewDesign(Integer id,Integer designType, Model model) {
		String pageUrl = null;
		if(id != null && designType != null){
			DesignVo designVo = this.designService.getDesign(id, designType);
			model.addAttribute("designVo", designVo);
			if(DesignType.DOOR == designType.intValue()){
				pageUrl =  "/jsp/owner/admin/design/viewDoor";
			}else if(DesignType.WINDOW == designType.intValue()){
				pageUrl =  "/jsp/owner/admin/design/viewWindow";
			}else if(DesignType.SUMROOM == designType.intValue()){
				pageUrl =  "/jsp/owner/admin/design/viewSunRoom";
			}
		}
		return pageUrl;
	}	
	
	
	
	
}
