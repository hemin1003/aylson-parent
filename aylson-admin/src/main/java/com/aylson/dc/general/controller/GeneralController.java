package com.aylson.dc.general.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.frame.base.Page;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.sys.search.ActivityRegisterSearch;
import com.aylson.dc.sys.search.ActivitySearch;
import com.aylson.dc.sys.service.ActivityRegisterService;
import com.aylson.dc.sys.service.ActivityService;
import com.aylson.dc.sys.vo.ActivityRegisterVo;
import com.aylson.dc.sys.vo.ActivityVo;
import com.aylson.utils.DateUtil;
import com.aylson.utils.StringUtil;
import com.aylson.utils.VerificationUtils;

/**
 * 客户端控制器
 * 说明：
 * 	  1、存放设计师联盟客户端的所有控制器接口，便于管理跟踪
 *    2、控制层尽量只负责控制跳转，业务逻辑在相关服务层实现
 * @author wwx
 * @since  2016-05
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/general")
public class GeneralController extends BaseController {
	
	//服务类
	@Autowired
	private ActivityService activityService;                                    
	@Autowired
	private ActivityRegisterService activityRegisterService;                   
	

	/**
	 * 查看活动列表
	 * @param activitySearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getActivityList", method = RequestMethod.GET)
	public Result getActivityList(ActivitySearch activitySearch){
		Result result = new Result();
		try{
			activitySearch.setState(2);//已发布
			Page<ActivityVo> activityList = this.activityService.getPage(activitySearch);
			result.setOK(ResultCode.CODE_STATE_200, "", activityList);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取活动详情
	 * @param actId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getActivityDetail", method = RequestMethod.GET)
	public Result getActivityDetail(Integer actId, int page, int rows){
		Result result = new Result();
		try{
			if(actId == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取活动信息失败");
				return result;
			}
			ActivityVo activityVo = this.activityService.getById(actId);
			ActivityRegisterSearch activityRegisterSearch = new ActivityRegisterSearch();
			activityRegisterSearch.setActId(actId);
			activityRegisterSearch.setPage(page);
			activityRegisterSearch.setRows(rows);
			Page<ActivityRegisterVo> registerList = this.activityRegisterService.getPage(activityRegisterSearch);
			activityVo.setRegisterList(registerList);
			result.setOK(ResultCode.CODE_STATE_200, "", activityVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 
	 * @param activityRegisterVo
	 * @return
	 */
	@RequestMapping(value = "/registerActivity", method = RequestMethod.POST)
	@ResponseBody
	public Result actRegister(ActivityRegisterVo activityRegisterVo) {
		Result result = new Result();
		try{
			if(activityRegisterVo.getActId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取活动信息失败");
				return result;
			}
			ActivityVo activityVo = this.activityService.getById(activityRegisterVo.getActId());
			if( 2 != activityVo.getState().intValue()){
				result.setError(ResultCode.CODE_STATE_4006, "该活动已结束");
				return result;
			}
			if(DateUtil.format(activityVo.getActEndTime()).compareTo(DateUtil.format(new Date())) == -1){
				result.setError(ResultCode.CODE_STATE_4006, "该活动已过期");
				return result;
			}
			if(StringUtil.isEmpty(activityRegisterVo.getRegisterPhone())){
				result.setError(ResultCode.CODE_STATE_4006, "请输入有效手机号码");
				return result;
			}
			if(!VerificationUtils.isValid(activityRegisterVo.getRegisterPhone(), VerificationUtils.MOBILE)){
				result.setError(ResultCode.CODE_STATE_4006, "请输入有效的手机号码");
				return result;
			}
			ActivityRegisterSearch activityRegisterSearch = new ActivityRegisterSearch();
			activityRegisterSearch.setActId(activityRegisterVo.getActId());
			activityRegisterSearch.setRegisterPhone(activityRegisterVo.getRegisterPhone());
			long rows = this.activityRegisterService.getRowCount(activityRegisterSearch);
			if(rows > 0){
				result.setError(ResultCode.CODE_STATE_4006, "该手机号码已经报过名了");
				return result;
			}
			activityRegisterVo.setRegisterTime(new Date());
			Boolean flag = this.activityRegisterService.add(activityRegisterVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "报名成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "报名失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	public static void main(String[] args) {
		String s1 = "2016-10-19 09:39:40";
		String s2 = "2016-10-19 09:39:41";
		String s3 = "2016-10-19 09:39:39";
		String s4 = "2016-10-19 09:39:40";
		Date d1 = DateUtil.format("2016-10-19 09:39:40");
		Date d2 = DateUtil.format("2016-10-19 09:39:41");
		Date d3 = DateUtil.format("2016-10-19 09:39:39");
		Date d4 = DateUtil.format("2016-10-19 09:39:40");
		System.out.println(s1.compareTo(s2));
		System.out.println(s1.compareTo(s3));
		System.out.println(s1.compareTo(s4));
		System.out.println(DateUtil.compareDate(d1, d2));
		System.out.println(DateUtil.compareDate(d1, d3));
		System.out.println(DateUtil.compareDate(d1, d4));
	}
}
