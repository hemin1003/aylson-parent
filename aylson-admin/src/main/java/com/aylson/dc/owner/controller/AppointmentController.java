package com.aylson.dc.owner.controller;

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
import com.aylson.dc.base.GeneralConstant.UserType;
import com.aylson.dc.base.OwnerGeneralConstant.AppointState;
import com.aylson.dc.owner.search.AppointmentSearch;
import com.aylson.dc.owner.service.AppointmentService;
import com.aylson.dc.owner.vo.AppointmentVo;
import com.aylson.dc.sys.common.SessionInfo;

/**
 * 反馈管理
 * @author wwx
 * @since  2016-08
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/owner/appointment")
public class AppointmentController extends BaseController {
	
	@Autowired
	private AppointmentService appointmentService;                     //在线预约服务
	
	
	/**
	 * 获取列表（分页）
	 * stateType：0 报废 1已办 2代办
	 * userType: 1经销商 2总部
	 * @param appointmentSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPage", method = RequestMethod.GET)
	public Result getPage(AppointmentSearch appointmentSearch) {
		Result result = new Result();
		//如果是门店，只能看到自己的预约
		SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
		if(sessionInfo == null){
			result.setError(ResultCode.CODE_STATE_4006, "未登录或者登陆过期，请重新登录再试！");
			return result;
		}
		appointmentSearch.setUserType(sessionInfo.getUser().getType().intValue());
		if(sessionInfo.getUser().getType().intValue() == UserType.AGENT_USER){
			appointmentSearch.setByAgentUserId(sessionInfo.getUser().getId());
		}
		Page<AppointmentVo> page = this.appointmentService.getPage(appointmentSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", page);
		return result;
	}	
	
	/**
	 * 获取预约详情
	 * @param appointId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAppointInfo", method = RequestMethod.GET)
	public Result getAppointInfo(Integer appointId){
		Result result = new Result();
		try{
			result = this.appointmentService.getAppointInfo(appointId);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍后再试或者联系管理员！");
		}
		return result;
	}
		
	/**
	 * 保存
	 * @param appointmentVo
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Result save(AppointmentVo appointmentVo){
		Result result = new Result();
		try{
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			if(sessionInfo == null){
				result.setError(ResultCode.CODE_STATE_4006, "未登录或者登陆过期，请重新登录再试！");
				return result;
			}
			/*if(sessionInfo.getUser().getType().intValue() == UserType.ORG_USER){
				result.setError(ResultCode.CODE_STATE_4006, "您没有该操作权限！");
				return result;
			}*/
			if(!appointmentVo.getIsOnlySave()){
				appointmentVo.setDealerId(sessionInfo.getUser().getId());
				appointmentVo.setDealer(sessionInfo.getUser().getUserName());
			}
			appointmentVo.setUpdateTime(new Date());
			//appointmentVo.setIsOnlySave(true);
			result = this.appointmentService.save(appointmentVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	/**
	 * 提交下一步
	 * @param appointmentVo
	 * @return
	 */
	@RequestMapping(value = "/next", method = RequestMethod.POST)
	@ResponseBody
	public Result next(Integer id){
		Result result = new Result();
		try{
			//业务处理
			if(id == null){
				result.setError(ResultCode.CODE_STATE_4006, "预约信息主键不能为空！");
				return result;
			}
			AppointmentVo appointmentVo = this.appointmentService.getById(id);
			if(appointmentVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取预约信息失败，请稍后再试或者联系管理员");
				return result;
			}
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			if(sessionInfo == null){
				result.setError(ResultCode.CODE_STATE_4006, "未登录或者登陆过期，请重新登录再试！");
				return result;
			}
			appointmentVo.setIsOnlySave(false);
			result = this.appointmentService.next(appointmentVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	/**
	 * 添加预约
	 * @param appointmentVo
	 * @return
	 */
	@RequestMapping(value = "/addAppoint", method = RequestMethod.POST)
	@ResponseBody
	public Result addAppoint(AppointmentVo appointmentVo){
		Result result = new Result();
		try{
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			if(sessionInfo == null){
				result.setError(ResultCode.CODE_STATE_4006, "未登录或者登陆过期，请重新登录再试！");
				return result;
			}
			if(sessionInfo.getUser().getType().intValue() == UserType.AGENT_USER){
				result.setError(ResultCode.CODE_STATE_4006, "您没有该操作权限！");
				return result;
			}
			appointmentVo.setDealerId(sessionInfo.getUser().getId());
			appointmentVo.setDealer(sessionInfo.getUser().getUserName());
			appointmentVo.setUpdateTime(new Date());
			result = this.appointmentService.addAppoint(appointmentVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	/**
	 * 取消预约
	 * @param appointmentVo
	 * @return
	 */
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	@ResponseBody
	public Result cancel(Integer id){
		Result result = new Result();
		try{
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			if(sessionInfo == null){
				result.setError(ResultCode.CODE_STATE_4006, "未登录或者登陆过期，请重新登录再试！");
				return result;
			}
			if(sessionInfo.getUser().getType().intValue() == UserType.AGENT_USER){
				result.setError(ResultCode.CODE_STATE_4006, "您没有该操作权限！");
				return result;
			}
			if(id == null){
				result.setError(ResultCode.CODE_STATE_4006, "预约信息主键不能为空！");
				return result;
			}
			AppointmentVo appointmentVo = this.appointmentService.getById(id);
			if(appointmentVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取预约信息失败，请稍后再试或者联系管理员");
				return result;
			}
			if(AppointState.APPOINT != appointmentVo.getState().intValue()){
				result.setError(ResultCode.CODE_STATE_4006, "当前状态不允许该操作，如有疑问，请联系相关工作人员！");
				return result;
			}
			appointmentVo.setState(AppointState.CANCEL);
			appointmentVo.setDealerId(sessionInfo.getUser().getId());
			appointmentVo.setDealer(sessionInfo.getUser().getUserName());
			appointmentVo.setUpdateTime(new Date());
			Boolean flag = this.appointmentService.edit(appointmentVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	/**
	 * 恢复预约
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/restore", method = RequestMethod.POST)
	@ResponseBody
	public Result restore(Integer id){
		Result result = new Result();
		try{
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			if(sessionInfo == null){
				result.setError(ResultCode.CODE_STATE_4006, "未登录或者登陆过期，请重新登录再试！");
				return result;
			}
			if(sessionInfo.getUser().getType().intValue() == UserType.AGENT_USER){
				result.setError(ResultCode.CODE_STATE_4006, "您没有该操作权限！");
				return result;
			}
			if(id == null){
				result.setError(ResultCode.CODE_STATE_4006, "预约信息主键不能为空！");
				return result;
			}
			AppointmentVo appointmentVo = this.appointmentService.getById(id);
			if(appointmentVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取预约信息失败，请稍后再试或者联系管理员");
				return result;
			}
			if(AppointState.CANCEL != appointmentVo.getState().intValue()){
				result.setError(ResultCode.CODE_STATE_4006, "当前状态不允许该操作，如有疑问，请联系相关工作人员！");
				return result;
			}
			appointmentVo.setState(AppointState.APPOINT);
			appointmentVo.setDealerId(sessionInfo.getUser().getId());
			appointmentVo.setDealer(sessionInfo.getUser().getUserName());
			appointmentVo.setUpdateTime(new Date());
			Boolean flag = this.appointmentService.edit(appointmentVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	
}
