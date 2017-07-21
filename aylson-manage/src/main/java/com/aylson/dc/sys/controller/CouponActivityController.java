package com.aylson.dc.sys.controller;

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
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/sys/admin/couponConfig/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(CouponActivitySearch couponActivitySearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			//如果是门店，只能看到自己的活动
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			if(sessionInfo.getUser().getType().intValue() == UserType.AGENT_USER){
				couponActivitySearch.setCreatedById(sessionInfo.getUser().getId());
			}
			couponActivitySearch.setIsPage(true);
			List<CouponActivityVo> list = this.couponActivityService.getList(couponActivitySearch);
			result.setTotal(this.couponActivityService.getRowCount(couponActivitySearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 后台-添加页面
	 * @param helpVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(CouponActivityVo couponActivityVo) {
		this.request.setAttribute("couponActivityVo", couponActivityVo);
		return "/jsp/sys/admin/couponConfig/add";
	}
	
	/**
	 * 后台-添加保存
	 * @param couponActivityVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(CouponActivityVo couponActivityVo) {
		Result result = new Result();
		try{
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
			CouponActivityVo couponActivityVo = this.couponActivityService.getById(id);
			couponActivityVo.setStartTimeStr(DateUtil.format(couponActivityVo.getStartTime()));
			couponActivityVo.setEndTimeStr(DateUtil.format(couponActivityVo.getEndTime()));
			this.request.setAttribute("couponActivityVo", couponActivityVo);
		}
		return "/jsp/sys/admin/couponConfig/add";
	}
	
	/**
	 * 后台-编辑保存
	 * @param couponActivityVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(CouponActivityVo couponActivityVo) {
		Result result = new Result();
		try {
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
	
	@RequestMapping(value = "/admin/changeState", method = RequestMethod.POST)
	@ResponseBody
	public Result changeState(CouponActivityVo couponActivityVo) {
		Result result = new Result();
		try {
			if(couponActivityVo.getState() == null){
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
				return result;
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
	
	private String getCurrentUserName(){
		SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
		if(sessionInfo != null && sessionInfo.getUser() != null){
			return sessionInfo.getUser().getUserName();
		}
		return "";
	}
	
	/**
	 * 审核页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/toVerify", method = RequestMethod.GET)
	public String toVerify(Integer id) {
		if(id != null){
			CouponActivityVo couponActivityVo = this.couponActivityService.getById(id);
			if(couponActivityVo.getStartTime() != null){
				couponActivityVo.setStartTimeStr(DateUtil.format(couponActivityVo.getStartTime()));
			}
			if(couponActivityVo.getEndTime() != null){
				couponActivityVo.setEndTimeStr(DateUtil.format(couponActivityVo.getEndTime()));
			}
			this.request.setAttribute("couponActivityVo", couponActivityVo);
		}
		return "/jsp/sys/admin/couponConfig/verify";
	}
	
	/**
	 * 保存审核结果
	 * @param couponActivityVo
	 * @return
	 */
	@RequestMapping(value = "/admin/verify", method = RequestMethod.POST)
	@ResponseBody
	public Result verify(CouponActivityVo couponActivityVo) {
		Result result = new Result();
		try {
			CouponActivityVo ca = this.couponActivityService.getById(couponActivityVo.getId());
			if(ca == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取活动信息失败");
				return result;
			}
			Date curDate = new Date();
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			couponActivityVo.setUpdatedBy(sessionInfo.getUser().getUserName());
			couponActivityVo.setUpdatedById(sessionInfo.getUser().getId());
			couponActivityVo.setUpdatedTime(curDate);
			if(StringUtil.isNotEmpty(couponActivityVo.getCurAuditOpinion())){
				String curOpinion = DateUtil.format(curDate)+ " " + sessionInfo.getUser().getUserName() + " " + couponActivityVo.getCurAuditOpinion() + ";<br/>";
				couponActivityVo.setAuditOpinion((ca.getAuditOpinion()==null?"":ca.getAuditOpinion()) + curOpinion);
			}
			System.out.println(couponActivityVo.getAuditOpinion());
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