package com.aylson.dc.owner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.easyui.EasyuiDataGridJson;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.base.OwnerGeneralConstant.DesignType;
import com.aylson.dc.owner.search.QuotationSearch;
import com.aylson.dc.owner.service.AppointmentService;
import com.aylson.dc.owner.service.DesignService;
import com.aylson.dc.owner.service.QuotationDetailDWService;
import com.aylson.dc.owner.service.QuotationDetailSRService;
import com.aylson.dc.owner.service.QuotationService;
import com.aylson.dc.owner.vo.DesignVo;
import com.aylson.dc.owner.vo.QuotationDetailDWVo;
import com.aylson.dc.owner.vo.QuotationDetailSRVo;
import com.aylson.dc.owner.vo.QuotationVo;
import com.aylson.utils.DateUtil;


/**
 * 反馈管理
 * @author wwx
 * @since  2016-08
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/owner/quotation")
public class QuotationController extends BaseController {
	
	@Autowired
	private QuotationService quotationService;                      //订货报价单服务
	@Autowired
	private DesignService designService;                            //设计信息表服务
	
	/**
	 * 订货报价单页面
	 */
	@RequestMapping(value = "/admin/toAddQuotation", method = RequestMethod.GET)
	public String toAddQuotation(Integer designId,Integer designType, Model model) {
		DesignVo designVo = this.designService.getById(designId);
		QuotationVo quotationVo = new QuotationVo();
		if(designVo != null){
			quotationVo.setAppointId(designVo.getAppointId());
			quotationVo.setDesignId(designId);
			quotationVo.setClientName(designVo.getClientName());
			quotationVo.setClientPhone(designVo.getClientPhone());
			quotationVo.setClientAddress(designVo.getClientAddress());
		}
		model.addAttribute("quotationVo", quotationVo);
		if(DesignType.DOOR == designType || DesignType.WINDOW == designType){
			return "/jsp/owner/admin/quotation/addDW";
		}else if(DesignType.SUMROOM == designType){
			return "/jsp/owner/admin/quotation/addSR";
		}
		return null;
	}	
	
	/**
	 * 保存订货报价单
	 * @param quotationVo
	 * @param model
	 * @param quotationDetailDWVoListJson
	 * @param quotationDetailSRVo
	 * @return
	 */
	@RequestMapping(value = "/admin/addQuotation", method = RequestMethod.POST)
	@ResponseBody
	public Result addQuotation(QuotationVo quotationVo,String quotationDetailDWVoListJson,QuotationDetailSRVo quotationDetailSRVo) {
		Result result = new Result();
		try {
			result = this.quotationService.addQuotation(quotationVo, quotationDetailDWVoListJson);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 查看订货单
	 * @param id
	 * @param designType
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/toViewQuotation", method = RequestMethod.GET)
	public String toViewQuotation(Integer designId,Integer designType, Model model) {
		String pageUrl = null;
		if(designId != null && designType != null){
			QuotationVo quotationVo  = this.quotationService.getQuotationVo(designId, designType);
			model.addAttribute("quotationVo", quotationVo);
		}
		if(DesignType.SUMROOM == designType.intValue()){
			pageUrl =  "/jsp/owner/admin/quotation/viewSR";
		}else{
			pageUrl =  "/jsp/owner/admin/quotation/viewDW";
		}
		return pageUrl;
	}	
	
	@RequestMapping(value = "/admin/toEditQuotation", method = RequestMethod.GET)
	public String toEditQuotation(Integer designId,Integer designType, Model model) {
		String pageUrl = null;
		if(designId != null && designType != null){
			QuotationVo quotationVo  = this.quotationService.getQuotationVo(designId, designType);
			model.addAttribute("quotationVo", quotationVo);
		}
		if(DesignType.SUMROOM == designType.intValue()){
			pageUrl =  "/jsp/owner/admin/quotation/editSR";
		}else{
			pageUrl =  "/jsp/owner/admin/quotation/editDW";
		}
		return pageUrl;
	}
	
	/**
	 * 保存报价订货单信息
	 * @param quotationVo
	 * @param quotationDetailDWVoListJson
	 * @return
	 */
	@RequestMapping(value = "/admin/editQuotation", method = RequestMethod.POST)
	@ResponseBody
	public Result editQuotation(QuotationVo quotationVo,String quotationDetailDWVoListJson) {
		Result result = new Result();
		try {
			result = this.quotationService.editQuotation(quotationVo, quotationDetailDWVoListJson);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	
	
	
}
