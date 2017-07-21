package com.aylson.dc.sys.controller;

import java.util.Date;
import java.util.List;

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
import com.aylson.dc.base.GeneralConstant.UserType;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.dc.sys.search.CouponActivitySearch;
import com.aylson.dc.sys.service.CouponActivityService;
import com.aylson.dc.sys.vo.CouponActivityVo;
import com.aylson.utils.DateUtil;
import com.aylson.utils.StringUtil;

/**
 * 优惠券礼品配置
 * @author minbo
 * @since  2016-11
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/sys/coupon")
public class CouponActivityController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(CouponActivityController.class);
	
	@Autowired
	private CouponActivityService couponActivityService;
	
	/**
	 * 获取列表（分页）
	 * @param storeSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPage", method = RequestMethod.GET)
	public Result getPage(CouponActivitySearch couponActivitySearch) {
		Result result = new Result();
		//如果是门店，只能看到自己的活动
		SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
		if(sessionInfo != null && sessionInfo.getUser().getType().intValue() == UserType.AGENT_USER){
			couponActivitySearch.setCreatedById(sessionInfo.getUser().getId());
		}
		Page<CouponActivityVo> page = this.couponActivityService.getPage(couponActivitySearch);
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
	public Result getList(CouponActivitySearch couponActivitySearch) {
		Result result = new Result();
		//如果是门店，只能看到自己的活动
		SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
		if(sessionInfo != null && sessionInfo.getUser().getType().intValue() == UserType.AGENT_USER){
			couponActivitySearch.setCreatedById(sessionInfo.getUser().getId());
		}
		List<CouponActivityVo> list = this.couponActivityService.getList(couponActivitySearch);
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
		CouponActivityVo couponActivityVo = this.couponActivityService.getById(id);
		if(couponActivityVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取信息失败，请稍后再试或者联系管理员！");
			return result;
		}
		if(couponActivityVo.getStartTime() != null){
			couponActivityVo.setStartTimeStr(DateUtil.format(couponActivityVo.getStartTime()));
		}
		if(couponActivityVo.getEndTime() != null){
			couponActivityVo.setEndTimeStr(DateUtil.format(couponActivityVo.getEndTime()));
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", couponActivityVo);
		return result;
	}
	
	/**
	 * 新增
	 * @param couponActivityVo
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(CouponActivityVo couponActivityVo) {
		Result result = new Result();
		try{
			if(couponActivityVo.getActivityType() == null){
				result.setError(ResultCode.CODE_STATE_4006, "活动类型不能为空！");
				return result;
			}
			if(couponActivityVo.getCouponFkid() == null){
				result.setError(ResultCode.CODE_STATE_4006, "使用优惠券不能为空！");
				return result;
			}
			if(StringUtil.isEmpty(couponActivityVo.getTitle())){
				result.setError(ResultCode.CODE_STATE_4006, "活动标题不能为空！");
				return result;
			}
			if(StringUtil.isEmpty(couponActivityVo.getSummary())){
				result.setError(ResultCode.CODE_STATE_4006, "活动摘要不能为空！");
				return result;
			}
			if(StringUtil.isEmpty(couponActivityVo.getStartTimeStr())){
				result.setError(ResultCode.CODE_STATE_4006, "活动开始时间不能为空！");
				return result;
			}
			if(StringUtil.isEmpty(couponActivityVo.getEndTimeStr())){
				result.setError(ResultCode.CODE_STATE_4006, "活动结束时间不能为空！");
				return result;
			}
			if(StringUtil.isEmpty(couponActivityVo.getLocation())){
				result.setError(ResultCode.CODE_STATE_4006, "活动地点不能为空！");
				return result;
			}
			if(couponActivityVo.getLimitNum() == null){
				result.setError(ResultCode.CODE_STATE_4006, "活动人数限制不能为空！");
				return result;
			}
			if(couponActivityVo.getProvinceId() == null || StringUtil.isEmpty(couponActivityVo.getProvince())){
				result.setError(ResultCode.CODE_STATE_4006, "请选择省份！");
				return result;
			}
			if(couponActivityVo.getCityId() == null || StringUtil.isEmpty(couponActivityVo.getCity())){
				result.setError(ResultCode.CODE_STATE_4006, "请选择城市！");
				return result;
			}
			if(StringUtil.isEmpty(couponActivityVo.getCityCode())){
				result.setError(ResultCode.CODE_STATE_4006, "城市编码不能为空！");
				return result;
			}
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			couponActivityVo.setStartTime(DateUtil.format(couponActivityVo.getStartTimeStr()));
			couponActivityVo.setEndTime(DateUtil.format(couponActivityVo.getEndTimeStr()));
			couponActivityVo.setCreatedBy(sessionInfo.getUser().getUserName());
			couponActivityVo.setCreatedById(sessionInfo.getUser().getId());
			couponActivityVo.setUserType(sessionInfo.getUser().getType());
			couponActivityVo.setCreatedTime(new Date());
			Boolean flag = this.couponActivityService.add(couponActivityVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍后再试或者联系管理员");
		}
		return result;
	}
	
	/**
	 * 修改
	 * @param couponActivityVo
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(CouponActivityVo couponActivityVo) {
		Result result = new Result();
		try {
			if(couponActivityVo.getId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "活动id不能为空！");
				return result;
			}
			if(couponActivityVo.getActivityType() == null){
				result.setError(ResultCode.CODE_STATE_4006, "活动类型不能为空！");
				return result;
			}
			if(couponActivityVo.getCouponFkid() == null){
				result.setError(ResultCode.CODE_STATE_4006, "使用优惠券不能为空！");
				return result;
			}
			if(StringUtil.isEmpty(couponActivityVo.getTitle())){
				result.setError(ResultCode.CODE_STATE_4006, "活动标题不能为空！");
				return result;
			}
			if(StringUtil.isEmpty(couponActivityVo.getSummary())){
				result.setError(ResultCode.CODE_STATE_4006, "活动摘要不能为空！");
				return result;
			}
			if(StringUtil.isEmpty(couponActivityVo.getStartTimeStr())){
				result.setError(ResultCode.CODE_STATE_4006, "活动开始时间不能为空！");
				return result;
			}
			if(StringUtil.isEmpty(couponActivityVo.getEndTimeStr())){
				result.setError(ResultCode.CODE_STATE_4006, "活动结束时间不能为空！");
				return result;
			}
			if(StringUtil.isEmpty(couponActivityVo.getLocation())){
				result.setError(ResultCode.CODE_STATE_4006, "活动地点不能为空！");
				return result;
			}
			if(couponActivityVo.getLimitNum() == null){
				result.setError(ResultCode.CODE_STATE_4006, "活动人数限制不能为空！");
				return result;
			}
			if(couponActivityVo.getProvinceId() == null || StringUtil.isEmpty(couponActivityVo.getProvince())){
				result.setError(ResultCode.CODE_STATE_4006, "请选择省份！");
				return result;
			}
			if(couponActivityVo.getCityId() == null || StringUtil.isEmpty(couponActivityVo.getCity())){
				result.setError(ResultCode.CODE_STATE_4006, "请选择城市！");
				return result;
			}
			if(StringUtil.isEmpty(couponActivityVo.getCityCode())){
				result.setError(ResultCode.CODE_STATE_4006, "城市编码不能为空！");
				return result;
			}
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			couponActivityVo.setStartTime(DateUtil.format(couponActivityVo.getStartTimeStr()));
			couponActivityVo.setEndTime(DateUtil.format(couponActivityVo.getEndTimeStr()));
			couponActivityVo.setUpdatedBy(sessionInfo.getUser().getUserName());
			couponActivityVo.setUpdatedById(sessionInfo.getUser().getId());
			couponActivityVo.setUpdatedTime(new Date());
			Boolean flag = this.couponActivityService.edit(couponActivityVo);
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
	 * 上线/下线
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/changeState", method = RequestMethod.POST)
	@ResponseBody
	public Result changeState(Integer id) {
		Result result = new Result();
		try {
			if(id == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
				return result;
			}
			CouponActivityVo couponActivityVo = this.couponActivityService.getById(id);
			if(couponActivityVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取信息失败，请稍后再试或者联系管理员！");
				return result;
			}
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			int curUserType = sessionInfo.getUser().getType().intValue();
			int state = couponActivityVo.getState().intValue();
			if(curUserType == UserType.AGENT_USER){//门店操作
				if(state == 0 && couponActivityVo.getAuditState() != 2){
					result.setError(ResultCode.CODE_STATE_4006, "还未通过审核，不能上线！");
					return result;
				}
			}
			if(state == 0){
				couponActivityVo.setState(1);
			}else{
				couponActivityVo.setState(0);
			}
			couponActivityVo.setUpdatedBy(this.getCurrentUserName());
			couponActivityVo.setUpdatedTime(new Date());
			Boolean flag = this.couponActivityService.edit(couponActivityVo);
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
	 * 申请活动
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/apply", method = RequestMethod.POST)
	@ResponseBody
	public Result apply(Integer id) {
		Result result = new Result();
		try {
			if(id == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
				return result;
			}
			CouponActivityVo couponActivityVo = this.couponActivityService.getById(id);
			if(couponActivityVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取信息失败，请稍后再试或者联系管理员！");
				return result;
			}
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			int curUserType = sessionInfo.getUser().getType().intValue();
			int state = couponActivityVo.getState().intValue();
			if(curUserType == UserType.ORG_USER){//总部操作
				result.setError(ResultCode.CODE_STATE_4006, "总部用户不能申请！");
				return result;
			}
			if(state == 1){
				result.setError(ResultCode.CODE_STATE_4006, "已经上线，不能申请！");
				return result;
			}
			if(couponActivityVo.getAuditState().intValue() == 1){
				result.setError(ResultCode.CODE_STATE_4006, "正在审核中，不能重复申请！");
				return result;
			}
			if(couponActivityVo.getAuditState().intValue() == 2){
				result.setError(ResultCode.CODE_STATE_4006, "已经审核通过，不能重复申请！");
				return result;
			}
			couponActivityVo.setAuditState(1);//审核中
			couponActivityVo.setUpdatedBy(this.getCurrentUserName());
			couponActivityVo.setUpdatedTime(new Date());
			Boolean flag = this.couponActivityService.edit(couponActivityVo);
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
	 * 获取当前登录人信息
	 * @return
	 */
	private String getCurrentUserName(){
		SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
		if(sessionInfo != null && sessionInfo.getUser() != null){
			return sessionInfo.getUser().getUserName();
		}
		return "";
	}
	
	/**
	 * 审核通过/审核不通过
	 * @param id
	 * @param isPass
	 * @param curAuditOpinion
	 * @return
	 */
	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	@ResponseBody
	public Result verify(Integer id, Boolean isPass, String curAuditOpinion) {
		Result result = new Result();
		try {
			//信息校验
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			int curUserType = sessionInfo.getUser().getType().intValue();
			if(curUserType == UserType.AGENT_USER){
				result.setError(ResultCode.CODE_STATE_4006, "只有总部账号才有审核权限");
				return result;
			}
			if(id == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
				return result;
			}
			CouponActivityVo couponActivityVo = this.couponActivityService.getById(id);
			if(couponActivityVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "根据获取信息失败，请稍后再试或者联系管理员！");
				return result;
			}
			if(couponActivityVo.getState() == null){
				result.setError(ResultCode.CODE_STATE_4006, "根据当前状态失败，请稍后再试或者联系管理员！");
				return result;
			}
			if(couponActivityVo.getState().intValue() == 1){
				result.setError(ResultCode.CODE_STATE_4006, "该活动已经上线，不能审核，如有疑问请联系管理员！");
				return result;
			}
			//审核状态：0未提交审核 1 审核中 2 审核通过 3审核不通过 
			if(couponActivityVo.getAuditState() == null){
				result.setError(ResultCode.CODE_STATE_4006, "根据当前审核状态失败，请稍后再试或者联系管理员！");
				return result;
			}
			if(couponActivityVo.getAuditState().intValue() != 1){
				result.setError(ResultCode.CODE_STATE_4006, "申请人未提交审核！");
				return result;
			}
			if(isPass == null){
				result.setError(ResultCode.CODE_STATE_4006, "审核结果未知，请选择通过或者不通过");
				return result;
			}
			if(!isPass && StringUtil.isEmpty(curAuditOpinion)){
				result.setError(ResultCode.CODE_STATE_4006, "不通过请输入审核意见");
				return result;
			}
			//信息有效
			if(isPass){
				couponActivityVo.setAuditState(2);
			}else{
				couponActivityVo.setAuditState(3);
			}
			Date curDate = new Date();
			couponActivityVo.setUpdatedBy(sessionInfo.getUser().getUserName());
			couponActivityVo.setUpdatedById(sessionInfo.getUser().getId());
			couponActivityVo.setUpdatedTime(curDate);
			couponActivityVo.setCurAuditOpinion(curAuditOpinion);
			if(StringUtil.isNotEmpty(couponActivityVo.getCurAuditOpinion())){
				String curOpinion = DateUtil.format(curDate)+ " " + sessionInfo.getUser().getUserName() + " " + couponActivityVo.getCurAuditOpinion() + ";<br/>";
				couponActivityVo.setAuditOpinion((couponActivityVo.getAuditOpinion()==null?"":couponActivityVo.getAuditOpinion()) + curOpinion);
			}
			//System.out.println(couponActivityVo.getAuditOpinion());
			Boolean flag = this.couponActivityService.edit(couponActivityVo);
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