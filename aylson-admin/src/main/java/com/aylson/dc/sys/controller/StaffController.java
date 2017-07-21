package com.aylson.dc.sys.controller;

import java.util.Date;
import java.util.List;

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
import com.aylson.dc.sys.search.StaffSearch;
import com.aylson.dc.sys.service.StaffService;
import com.aylson.dc.sys.vo.StaffVo;
import com.aylson.dc.sys.vo.UserVo;
import com.aylson.utils.StringUtil;
import com.aylson.utils.VerificationUtils;

/**
 * 门店员工管理
 * @author wwx
 * @since  2016-11
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/sys/staff")
public class StaffController extends BaseController {
	
	@Autowired
	private StaffService staffService;     //门店展示服务
	
	/**
	 * 获取列表（分页）
	 * @param staffSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPage", method = RequestMethod.GET)
	public Result getPage(StaffSearch staffSearch) {
		Result result = new Result();
		SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
		if(sessionInfo == null){
			result.setError(ResultCode.CODE_STATE_4006, "未登录或者登陆过期，请重新登录再试！");
			return result;
		}
		/*if(sessionInfo.getRole() != null && "agent".equals(sessionInfo.getRole().getRoleCode())){
			staffSearch.setSourceId(sessionInfo.getUser().getId());//代理商只能看自己的人员
		}else{
			staffSearch.setSourceType(UserType.ORG_USER);
		}*/
		UserVo userInfo = sessionInfo.getUser();
		if( UserType.ORG_USER == userInfo.getType()){         //总部用户
			staffSearch.setSourceType(UserType.ORG_USER);
		}else if( UserType.AGENT_USER == userInfo.getType()){ //经销商用户
			staffSearch.setSourceId(sessionInfo.getUser().getId());//代理商只能看自己的人员
		}
		Page<StaffVo> page = this.staffService.getPage(staffSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", page);
		return result;
	}
	
	/**
	 * 获取列表（不分页）
	 * @param staffSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public Result getList(StaffSearch staffSearch) {
		Result result = new Result();
		SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
		if(sessionInfo == null){
			result.setError(ResultCode.CODE_STATE_4006, "未登录或者登陆过期，请重新登录再试！");
			return result;
		}
		/*if(sessionInfo.getRole() != null && "agent".equals(sessionInfo.getRole().getRoleCode())){
			staffSearch.setSourceId(sessionInfo.getUser().getId());//代理商只能看自己的人员
		}else{
			staffSearch.setSourceType(UserType.ORG_USER);
		}*/
		UserVo userInfo = sessionInfo.getUser();
		if( UserType.ORG_USER == userInfo.getType()){         //总部用户
			staffSearch.setSourceType(UserType.ORG_USER);
		}else if( UserType.AGENT_USER == userInfo.getType()){ //经销商用户
			staffSearch.setSourceId(sessionInfo.getUser().getId());//代理商只能看自己的人员
		}
		List<StaffVo> list = this.staffService.getList(staffSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", list);
		return result;
	}
	
	/**
	 * 根据id查询
	 * @param id
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
		StaffVo staffVo = this.staffService.getById(id);
		if(staffVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取信息失败，请稍后再试或者联系管理员！");
			return result;
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", staffVo);
		return result;
	}
	
	/**
	 * 新增
	 * @param staffVo
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(StaffVo staffVo) {
		Result result = new Result();
		try{
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			if(sessionInfo == null){
				result.setError(ResultCode.CODE_STATE_4006, "未登录或者登陆过期，请重新登录再试！");
				return result;
			}
			if(StringUtil.isEmpty(staffVo.getStaffName())){
				result.setError(ResultCode.CODE_STATE_4006, "人员姓名不能为空！");
				return result;
			}
			if(StringUtil.isEmpty(staffVo.getStaffPhone())){
				result.setError(ResultCode.CODE_STATE_4006, "人员手机号码不能为空！");
				return result;
			}
			if(!VerificationUtils.isValid(staffVo.getStaffPhone(), VerificationUtils.MOBILE)){
				result.setError(ResultCode.CODE_STATE_4006, "请输入有效的手机号码");
				return result;
			}
			UserVo userInfo = sessionInfo.getUser();
			if(UserType.ORG_USER == userInfo.getType()){    //总部-人员管理
				if(staffVo.getSourceId() == null && StringUtil.isEmpty(staffVo.getSource())){
					result.setError(ResultCode.CODE_STATE_4006, "请选择组织机构");
					return result;
				}
				staffVo.setSourceType(UserType.ORG_USER);
			}else if( UserType.AGENT_USER == userInfo.getType()){   //代理商-人员管理
				staffVo.setSourceId(userInfo.getId());
				staffVo.setSource(userInfo.getUserName());
				staffVo.setSourceType(UserType.AGENT_USER );
			}
			staffVo.setCreateTime(new Date());
			Boolean flag = this.staffService.add(staffVo);
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
	 * 修改
	 * @param staffVo
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(StaffVo staffVo) {
		Result result = new Result();
		try{
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			if(sessionInfo == null){
				result.setError(ResultCode.CODE_STATE_4006, "未登录或者登陆过期，请重新登录再试！");
				return result;
			}
			if(staffVo.getId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "人员管理信息id不能为空！");
				return result;
			}
			if(StringUtil.isEmpty(staffVo.getStaffName())){
				result.setError(ResultCode.CODE_STATE_4006, "人员姓名不能为空！");
				return result;
			}
			if(StringUtil.isEmpty(staffVo.getStaffPhone())){
				result.setError(ResultCode.CODE_STATE_4006, "人员手机号码不能为空！");
				return result;
			}
			if(!VerificationUtils.isValid(staffVo.getStaffPhone(), VerificationUtils.MOBILE)){
				result.setError(ResultCode.CODE_STATE_4006, "请输入有效的手机号码");
				return result;
			}
			UserVo userInfo = sessionInfo.getUser();
			if(UserType.ORG_USER == userInfo.getType()){    //总部-人员管理
				if(staffVo.getSourceId() == null && StringUtil.isEmpty(staffVo.getSource())){
					result.setError(ResultCode.CODE_STATE_4006, "请选择组织机构");
					return result;
				}
			}
			Boolean flag = this.staffService.edit(staffVo);
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
	 * 启用/禁用
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/changeState", method = RequestMethod.POST)
	@ResponseBody
	public Result changeState(Integer id){
		Result result = new Result();
		if(id == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
			return result;
		}
		StaffVo staffVo = this.staffService.getById(id);
		if(staffVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取信息失败，请稍后再试或者联系管理员！");
			return result;
		}
		staffVo.setIsDisable(!staffVo.getIsDisable());
		Boolean flag = this.staffService.edit(staffVo);
		if(flag){
			result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "操作失败");
		}
		return result;
		
	}
	
	
}
