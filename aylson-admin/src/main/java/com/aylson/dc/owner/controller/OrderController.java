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
import com.aylson.dc.base.GeneralConstant.UserType;
import com.aylson.dc.base.OwnerGeneralConstant.OrderFlowState;
import com.aylson.dc.owner.search.OrderSearch;
import com.aylson.dc.owner.service.OrderService;
import com.aylson.dc.owner.vo.OrderVo;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.dc.sys.service.AgentUserService;
import com.aylson.dc.sys.vo.AgentUserVo;


/**
 * 业主订单管理
 * @author wwx
 * @since  2016-12
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/owner/order")
public class OrderController extends BaseController {
	
	@Autowired
	private OrderService orderService;                     //订单服务
	@Autowired
	private AgentUserService agentUserService;     //组织机构用户服务
	
	/**
	 * 获取列表（分页）
	 * @param storeSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPage", method = RequestMethod.GET)
	public Result getPage(OrderSearch orderSearch) {
		Result result = new Result();
		//如果是门店，只能看到自己的预约
		SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
		if(sessionInfo == null){
			result.setError(ResultCode.CODE_STATE_4006, "未登录或者登陆过期，请重新登录再试！");
			return result;
		}
		if(sessionInfo.getUser().getType().intValue() == UserType.AGENT_USER){
			orderSearch.setByAgentUserId(sessionInfo.getUser().getId());
		}else{
			orderSearch.setFlowStateType(1);
		}
		Page<OrderVo> page = this.orderService.getPage(orderSearch);
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
	public Result getList(OrderSearch orderSearch) {
		Result result = new Result();
		//如果是门店，只能看到自己的预约
		SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
		if(sessionInfo == null){
			result.setError(ResultCode.CODE_STATE_4006, "未登录或者登陆过期，请重新登录再试！");
			return result;
		}
		if(sessionInfo.getUser().getType().intValue() == UserType.AGENT_USER){
			orderSearch.setByAgentUserId(sessionInfo.getUser().getId());
		}else{
			orderSearch.setFlowStateType(1);
		}
		List<OrderVo> list = this.orderService.getList(orderSearch);
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
		OrderVo orderVo = this.orderService.getById(id);
		if(orderVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取信息失败，请稍后再试或者联系管理员！");
			return result;
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", orderVo);
		return result;
	}
	
	/**
	 * 获取预约详情
	 * @param appointId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getOrderInfo", method = RequestMethod.GET)
	public Result getOrderInfo(Integer orderId){
		Result result = new Result();
		try{
			result = this.orderService.getOrderInfo(orderId);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	/**
	 * 更新订单状态
	 * @param id
	 * @param state
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/changeState", method = RequestMethod.POST)
	public Result changeState(OrderVo orderVo) {
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
			result = this.orderService.changeState(orderVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	/**
	 * 经销商-新增订单
	 * @param orderVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addOrder", method = RequestMethod.POST)
	public Result addOrder(OrderVo orderVo) {
		Result result = new Result();
		try{
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			if(sessionInfo == null){
				result.setError(ResultCode.CODE_STATE_4006, "未登录或者登陆过期，请重新登录再试！");
				return result;
			}
			if(sessionInfo.getUser().getType().intValue() == UserType.ORG_USER){
				result.setError(ResultCode.CODE_STATE_4006, "您没有该操作权限！");
				return result;
			}
			AgentUserVo agentUserVo = this.agentUserService.getByUserId(sessionInfo.getUser().getId());
			if(agentUserVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前登录人详细信息失败！");
				return result;
			}
			orderVo.setByAgent(agentUserVo.getAgentName());
			orderVo.setByAgentUserId(sessionInfo.getUser().getId());
			result = this.orderService.addOrder(orderVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	/**
	 * 修改订单
	 * @param orderVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/editOrder", method = RequestMethod.POST)
	public Result editOrder(OrderVo orderVo) {
		Result result = new Result();
		try{
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			if(sessionInfo == null){
				result.setError(ResultCode.CODE_STATE_4006, "未登录或者登陆过期，请重新登录再试！");
				return result;
			}
			if(sessionInfo.getUser().getType().intValue() == UserType.ORG_USER){
				result.setError(ResultCode.CODE_STATE_4006, "您没有该操作权限！");
				return result;
			}
			result = this.orderService.editOrder(orderVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	/**
	 * 确认订单
	 * @param orderVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/confirmOrder", method = RequestMethod.POST)
	public Result confirmOrder(OrderVo orderVo){
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
			if(!orderVo.getIsOnlySave()){
				orderVo.setDealerId(sessionInfo.getUser().getId());
				orderVo.setDealer(sessionInfo.getUser().getUserName());
			}
			result = this.orderService.confirmOrder(orderVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	/**
	 * 提交下一步：待总部确认订单
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/next", method = RequestMethod.POST)
	public Result next(Integer id){
		Result result = new Result();
		try{
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			if(sessionInfo == null){
				result.setError(ResultCode.CODE_STATE_4006, "未登录或者登陆过期，请重新登录再试！");
				return result;
			}
			if(sessionInfo.getUser().getType().intValue() == UserType.ORG_USER){
				result.setError(ResultCode.CODE_STATE_4006, "您没有该操作权限！");
				return result;
			}
			if(id == null){
				result.setError(ResultCode.CODE_STATE_4006, "订单id不能为空");
				return result;
			}
			OrderVo orderVo = this.orderService.getById(id);
			if(orderVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "根据订单id获取详情失败");
				return result;
			}
			if(OrderFlowState.WAIT_AGENT_CONFRIM != orderVo.getFlowState().intValue()){
				result.setError(ResultCode.CODE_STATE_4006, "当前订单状态不允许该操作");
				return result;
			}
			orderVo.setIsOnlySave(false);
			result = this.orderService.next(orderVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	/**
	 * 提交下一步 总部确认订单
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/orgNext", method = RequestMethod.POST)
	public Result orgNext(Integer id){
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
			
			result = this.orderService.orgNext(id);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	
}
