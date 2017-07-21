package com.aylson.dc.owner.controller;

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
import com.aylson.dc.base.GeneralConstant.SourceTable;
import com.aylson.dc.base.GeneralConstant.UserType;
import com.aylson.dc.base.GeneralConstant.AttachmentType;
import com.aylson.dc.owner.search.OrderSearch;
import com.aylson.dc.owner.search.OwnerInfoSearch;
import com.aylson.dc.owner.service.OrderService;
import com.aylson.dc.owner.service.OwnerInfoService;
import com.aylson.dc.owner.vo.OrderVo;
import com.aylson.dc.owner.vo.OwnerInfoVo;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.dc.sys.search.AttachmentSearch;
import com.aylson.dc.sys.service.AttachmentService;
import com.aylson.dc.sys.vo.AttachmentVo;


/**
 * 客户信息管理
 * @author wwx
 * @since  2017-04
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/owner/ownerInfo")
public class OwnerInfoController extends BaseController {
	
	@Autowired
	private OwnerInfoService ownerInfoService;                     //订单服务
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private OrderService orderService;
	
	/**
	 * 获取列表（分页）
	 * @param storeSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPage", method = RequestMethod.GET)
	public Result getPage(OwnerInfoSearch ownerInfoSearch) {
		Result result = new Result();
		SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
		if(sessionInfo == null || sessionInfo.getUser() == null){
			result.setError(ResultCode.CODE_STATE_4006, "登录失效，请重新登录再试");
			return result;
		}
		if(sessionInfo.getUser().getType().intValue() == UserType.AGENT_USER){
			ownerInfoSearch.setCreaterId(sessionInfo.getUser().getId());
		}
		Page<OwnerInfoVo> page = this.ownerInfoService.getPage(ownerInfoSearch);
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
	public Result getList(OwnerInfoSearch ownerInfoSearch) {
		Result result = new Result();
		SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
		if(sessionInfo == null || sessionInfo.getUser() == null){
			result.setError(ResultCode.CODE_STATE_4006, "登录失效，请重新登录再试");
			return result;
		}
		if(sessionInfo.getUser().getType().intValue() == UserType.AGENT_USER){
			ownerInfoSearch.setCreaterId(sessionInfo.getUser().getId());
		}
		List<OwnerInfoVo> list = this.ownerInfoService.getList(ownerInfoSearch);
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
		SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
		if(sessionInfo == null || sessionInfo.getUser() == null){
			result.setError(ResultCode.CODE_STATE_4006, "登录失效，请重新登录再试");
			return result;
		}
		if(id == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
			return result;
		}
		OwnerInfoVo ownerInfoVo = this.ownerInfoService.getById(id);
		if(ownerInfoVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取信息失败，请稍后再试或者联系管理员！");
		}else{
			if(ownerInfoVo.getSourceType().intValue() == 1){
				//订单信息
				OrderSearch orderSearch = new OrderSearch();
				orderSearch.setMobilePhone(ownerInfoVo.getMobilePhone());
				orderSearch.setFlowStateType(1);
				if(sessionInfo.getUser().getType().intValue() == UserType.AGENT_USER){
					orderSearch.setByAgentUserId(sessionInfo.getUser().getId());
				}
				List<OrderVo> orderList = this.orderService.getList(orderSearch);
				ownerInfoVo.setOrderList(orderList);
			}else{
				//附件信息
				AttachmentSearch attachmentSearch = new AttachmentSearch();
				attachmentSearch.setIsEffect(true);//有效附件
				attachmentSearch.setType(AttachmentType.OWNERINFO);
				attachmentSearch.setSourceType(SourceTable.OWNER_INFO);
				attachmentSearch.setSourceId(id);
				List<AttachmentVo> attachList = this.attachmentService.getList(attachmentSearch);
				ownerInfoVo.setAttachList(attachList);
			}
			result.setOK(ResultCode.CODE_STATE_200, "操作成功", ownerInfoVo);
		}
		return result;
	}
	
	/**
	 * 新增
	 * @param storeVo
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(OwnerInfoVo ownerInfoVo) {
		Result result = new Result();
		try{
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			if(sessionInfo == null || sessionInfo.getUser() == null){
				result.setError(ResultCode.CODE_STATE_4006, "登录失效，请重新登录再试");
				return result;
			}
			ownerInfoVo.setCreaterId(sessionInfo.getUser().getId());
			result = this.ownerInfoService.addInfo(ownerInfoVo);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("接口：/owner/ownerInfo/add，错误信息："+e.getMessage());
			result.setError(ResultCode.CODE_STATE_500, "操作失败，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	/**
	 * 修改
	 * @param ownerInfoVo
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(OwnerInfoVo ownerInfoVo) {
		Result result = new Result();
		try{
			result = this.ownerInfoService.updateInfo(ownerInfoVo);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("接口：/owner/ownerInfo/add，错误信息："+e.getMessage());
			result.setError(ResultCode.CODE_STATE_500, "操作失败，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteById", method = RequestMethod.POST)
	@ResponseBody
	public Result deleteById(Integer id) {
		Result result = null;
		try{
			result = this.ownerInfoService.delInfo(id);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("接口：/owner/ownerInfo/deleteById，错误信息："+e.getMessage());
			result.setError(ResultCode.CODE_STATE_500, "操作失败，请稍后再试或者联系管理员！");
		}
		return result;
	}

	
}
