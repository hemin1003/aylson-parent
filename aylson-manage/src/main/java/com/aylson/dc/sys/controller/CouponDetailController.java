package com.aylson.dc.sys.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.dc.sys.po.CouponRule;
import com.aylson.dc.sys.search.CouponDetailSearch;
import com.aylson.dc.sys.search.CouponRuleSearch;
import com.aylson.dc.sys.service.CouponDetailService;
import com.aylson.dc.sys.service.CouponRuleService;
import com.aylson.dc.sys.vo.CouponDetailVo;
import com.aylson.utils.DateUtil;

/**
 * 优惠券明细配置
 * @author minbo
 * @since  2016-11
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/sys/couponDetail")
public class CouponDetailController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(CouponDetailController.class);
	
	@Autowired
	private CouponDetailService couponDetailService;
	
	@Autowired
	private CouponRuleService couponRuleService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/sys/admin/couponDetail/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(CouponDetailSearch couponDetailSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			couponDetailSearch.setIsPage(true);
			List<CouponDetailVo> list = this.couponDetailService.getList(couponDetailSearch);
			result.setTotal(this.couponDetailService.getRowCount(couponDetailSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 根据条件获取供应商列表信息
	 * @param dictionarySearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<CouponDetailVo> getList(CouponDetailSearch couponDetailSearch) {
		List<CouponDetailVo> list = this.couponDetailService.getList(couponDetailSearch);
		return list;
	}
	
	/**
	 * 后台-添加页面
	 * @param helpVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(CouponDetailVo couponDetailVo) {
		this.request.setAttribute("couponDetailVo", couponDetailVo);
		List<CouponRule> list = new ArrayList<CouponRule>();
		for (int i = 0; i < 5; i++) {
			list.add(new CouponRule());
		}
		this.request.setAttribute("list", list);
		return "/jsp/sys/admin/couponDetail/add";
	}
	
	/**
	 * 后台-添加保存
	 * @param couponDetailVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(CouponDetailVo couponDetailVo) {
		String[] startPrice = this.request.getParameterValues("startPrice");
		String[] endPrice = this.request.getParameterValues("endPrice");
		String[] deratePrice = this.request.getParameterValues("deratePrice");
		
		Result result = new Result();
		try{
			couponDetailVo.setStartTime(DateUtil.format(couponDetailVo.getStartTimeStr()));
			couponDetailVo.setEndTime(DateUtil.format(couponDetailVo.getEndTimeStr()));
			couponDetailVo.setCreatedBy(this.getCurrentUserName());
			couponDetailVo.setCreatedTime(new Date());
			result = this.couponDetailService.addDetailAndCouponRule(couponDetailVo, startPrice, endPrice, deratePrice);
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
	public String toEdit(Integer id) {
		if(id != null){
			CouponDetailVo couponDetailVo = this.couponDetailService.getById(id);
			couponDetailVo.setStartTimeStr(DateUtil.format(couponDetailVo.getStartTime()));
			couponDetailVo.setEndTimeStr(DateUtil.format(couponDetailVo.getEndTime()));
			this.request.setAttribute("couponDetailVo", couponDetailVo);
			//显示关联规则Rule
			CouponRuleSearch couponRuleSearch = new CouponRuleSearch();
			couponRuleSearch.setCouponFkid(id);
			List<CouponRule> list = this.couponRuleService.getList(couponRuleSearch);
			this.request.setAttribute("list", list);
		}
		return "/jsp/sys/admin/couponDetail/add";
	}
	
	/**
	 * 后台-编辑保存
	 * @param couponDetailVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(CouponDetailVo couponDetailVo) {
		Result result = new Result();
		String[] ruleId = this.request.getParameterValues("ruleId");
		String[] startPrice = this.request.getParameterValues("startPrice");
		String[] endPrice = this.request.getParameterValues("endPrice");
		String[] deratePrice = this.request.getParameterValues("deratePrice");
		try {
			couponDetailVo.setStartTime(DateUtil.format(couponDetailVo.getStartTimeStr()));
			couponDetailVo.setEndTime(DateUtil.format(couponDetailVo.getEndTimeStr()));
			couponDetailVo.setUpdatedBy(this.getCurrentUserName());
			couponDetailVo.setUpdatedTime(new Date());
			result = this.couponDetailService.updateDetailAndCouponRule(couponDetailVo, startPrice, endPrice, deratePrice, ruleId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = "/admin/changeState", method = RequestMethod.POST)
	@ResponseBody
	public Result changeState(CouponDetailVo couponDetailVo) {
		Result result = new Result();
		try {
			if(couponDetailVo.getIsEnabled() == null){
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
				return result;
			}
			couponDetailVo.setUpdatedBy(this.getCurrentUserName());
			couponDetailVo.setUpdatedTime(new Date());
			Boolean flag = this.couponDetailService.edit(couponDetailVo);
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
	
	private String getCurrentUserName(){
		SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
		if(sessionInfo != null && sessionInfo.getUser() != null){
			return sessionInfo.getUser().getUserName();
		}
		return "";
	}
}