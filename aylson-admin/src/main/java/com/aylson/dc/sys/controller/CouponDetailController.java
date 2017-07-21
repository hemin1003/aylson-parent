package com.aylson.dc.sys.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.frame.base.Page;
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
import com.aylson.utils.StringUtil;

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
	 * 获取列表（分页）
	 * @param storeSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPage", method = RequestMethod.GET)
	public Result getPage(CouponDetailSearch couponDetailSearch) {
		Result result = new Result();
		Page<CouponDetailVo> page = this.couponDetailService.getPage(couponDetailSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", page);
		return result;
	}
	
	/**
	 * 获取列表（不分页）
	 * @param storeSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public Result getList(CouponDetailSearch couponDetailSearch) {
		Result result = new Result();
		List<CouponDetailVo> list = this.couponDetailService.getList(couponDetailSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", list);
		return result;
	}
	
	/**
	 * 获取列表（不分页）
	 * @param storeSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getById", method = RequestMethod.GET)
	public Result getById(Integer id) {
		Result result = new Result();
		if(id == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
			return result;
		}
		CouponDetailVo couponDetailVo = this.couponDetailService.getById(id);
		if(couponDetailVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取信息失败，请稍后再试或者联系管理员！");
		}
		Map<String, Object> data = new HashMap<String, Object>();
		couponDetailVo.setStartTimeStr(DateUtil.format(couponDetailVo.getStartTime()));
		couponDetailVo.setEndTimeStr(DateUtil.format(couponDetailVo.getEndTime()));
		data.put("couponDetailVo", couponDetailVo);
		//显示关联规则Rule
		CouponRuleSearch couponRuleSearch = new CouponRuleSearch();
		couponRuleSearch.setCouponFkid(id);
		List<CouponRule> list = this.couponRuleService.getList(couponRuleSearch);
		data.put("couponRuleList", list);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", data);
		return result;
	}
	
	/**
	 * 新增
	 * @param couponDetailVo
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(CouponDetailVo couponDetailVo) {
		Result result = new Result();
		try{
			if(StringUtil.isEmpty(couponDetailVo.getCouponName())){
				result.setError(ResultCode.CODE_STATE_4006, "优惠券名称不能为空！");
				return result;
			}
			if(couponDetailVo.getCouponType() == null){
				result.setError(ResultCode.CODE_STATE_4006, "优惠券类型不能为空！");
				return result;
			}
			if(couponDetailVo.getCouponValue() == null){
				result.setError(ResultCode.CODE_STATE_4006, "券面值不能为空！");
				return result;
			}
			if(StringUtil.isEmpty(couponDetailVo.getStartTimeStr())){
				result.setError(ResultCode.CODE_STATE_4006, "有效开始时间不能为空！");
				return result;
			}
			if(StringUtil.isEmpty(couponDetailVo.getEndTimeStr())){
				result.setError(ResultCode.CODE_STATE_4006, "有效终止时间不能为空！");
				return result;
			}
			couponDetailVo.setCreatedBy(this.getCurrentUserName());
			couponDetailVo.setCreatedTime(new Date());
			couponDetailVo.setStartTime(DateUtil.format(couponDetailVo.getStartTimeStr()));
			couponDetailVo.setEndTime(DateUtil.format(couponDetailVo.getEndTimeStr()));
			result = this.couponDetailService.addDetailAndCouponRule(couponDetailVo);
//			String[] startPrice = this.request.getParameterValues("startPrice");
//			String[] endPrice = this.request.getParameterValues("endPrice");
//			String[] deratePrice = this.request.getParameterValues("deratePrice");
//			couponDetailVo.setStartTime(DateUtil.format(couponDetailVo.getStartTimeStr()));
//			couponDetailVo.setEndTime(DateUtil.format(couponDetailVo.getEndTimeStr()));
//			couponDetailVo.setCreatedBy(this.getCurrentUserName());
//			couponDetailVo.setCreatedTime(new Date());
//			result = this.couponDetailService.addDetailAndCouponRule(couponDetailVo, startPrice, endPrice, deratePrice);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500,"系统繁忙，请稍后再试或者联系管理员");
		}
		return result;
	}
	
	/**
	 * 修改
	 * @param couponDetailVo
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(CouponDetailVo couponDetailVo) {
		Result result = new Result();
		try {
			if(couponDetailVo.getId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "id不能为空");
				return result;
			}
			if(StringUtil.isEmpty(couponDetailVo.getCouponName())){
				result.setError(ResultCode.CODE_STATE_4006, "优惠券名称不能为空！");
				return result;
			}
			if(couponDetailVo.getCouponType() == null){
				result.setError(ResultCode.CODE_STATE_4006, "优惠券类型不能为空！");
				return result;
			}
			if(couponDetailVo.getCouponValue() == null){
				result.setError(ResultCode.CODE_STATE_4006, "券面值不能为空！");
				return result;
			}
			if(StringUtil.isEmpty(couponDetailVo.getStartTimeStr())){
				result.setError(ResultCode.CODE_STATE_4006, "有效开始时间不能为空！");
				return result;
			}
			if(StringUtil.isEmpty(couponDetailVo.getEndTimeStr())){
				result.setError(ResultCode.CODE_STATE_4006, "有效终止时间不能为空！");
				return result;
			}
			couponDetailVo.setStartTime(DateUtil.format(couponDetailVo.getStartTimeStr()));
			couponDetailVo.setEndTime(DateUtil.format(couponDetailVo.getEndTimeStr()));
			couponDetailVo.setUpdatedBy(this.getCurrentUserName());
			couponDetailVo.setUpdatedTime(new Date());
			result = this.couponDetailService.updateDetailAndCouponRule(couponDetailVo);
//			String[] ruleId = this.request.getParameterValues("ruleId");
//			String[] startPrice = this.request.getParameterValues("startPrice");
//			String[] endPrice = this.request.getParameterValues("endPrice");
//			String[] deratePrice = this.request.getParameterValues("deratePrice");
//		
//			couponDetailVo.setStartTime(DateUtil.format(couponDetailVo.getStartTimeStr()));
//			couponDetailVo.setEndTime(DateUtil.format(couponDetailVo.getEndTimeStr()));
//			couponDetailVo.setUpdatedBy(this.getCurrentUserName());
//			couponDetailVo.setUpdatedTime(new Date());
//			result = this.couponDetailService.updateDetailAndCouponRule(couponDetailVo, startPrice, endPrice, deratePrice, ruleId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = "/changeState", method = RequestMethod.POST)
	@ResponseBody
	public Result changeState(CouponDetailVo couponDetailVo) {
		Result result = new Result();
		try {
			if(couponDetailVo.getId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取id失败");
				return result;
			}
			if(couponDetailVo.getIsEnabled() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取操作状态失败");
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