package com.aylson.dc.owner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.easyui.EasyuiDataGridJson;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.base.OwnerGeneralConstant.OrderScheduleState;
import com.aylson.dc.owner.search.OrderScheduleSearch;
import com.aylson.dc.owner.search.OrderSearch;
import com.aylson.dc.owner.service.AppointmentService;
import com.aylson.dc.owner.service.OrderScheduleService;
import com.aylson.dc.owner.service.OrderService;
import com.aylson.dc.owner.service.QuotationService;
import com.aylson.dc.owner.vo.AppointmentVo;
import com.aylson.dc.owner.vo.OrderScheduleVo;
import com.aylson.dc.owner.vo.OrderVo;
import com.aylson.dc.owner.vo.QuotationVo;
import com.aylson.dc.partner.search.CouponSearch;
import com.aylson.dc.partner.service.CouponService;
import com.aylson.dc.partner.vo.CouponVo;
import com.aylson.utils.DateUtil;


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
	private AppointmentService appointmentService;         //在线预约服务
	@Autowired
	private OrderScheduleService orderScheduleService;     //订单进度服务
	@Autowired
	private QuotationService quotationService;             //报价单信息服务
	@Autowired
	private CouponService couponService;                   //优惠券信息服务

	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/owner/admin/order/index";
	}	
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(OrderSearch orderSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			orderSearch.setIsPage(true);
			List<OrderVo> list = this.orderService.getList(orderSearch);
			result.setTotal(this.orderService.getRowCount(orderSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据条件获取列表信息
	 * @param orderSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<OrderVo> getList(OrderSearch orderSearch) {
		List<OrderVo> list = this.orderService.getList(orderSearch);
		return list;
	}
	
	/**
	 * 后台-添加页面
	 * @param orderVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(OrderVo orderVo) {
		this.request.setAttribute("orderVo",orderVo);
		this.request.setAttribute("orderScheduleStateMap", OrderScheduleState.OrderScheduleStateMap);
		return "/jsp/owner/admin/order/add";
	}	
	
	/**
	 * 后台-添加保存
	 * @param orderVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(OrderVo orderVo) {
		Result result = null;
		try{
			result = this.orderService.addOrder(orderVo);
		}catch(Exception e){
			e.printStackTrace();
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
			OrderVo orderVo = this.orderService.getById(id);
			OrderScheduleSearch orderScheduleSearch = new OrderScheduleSearch();
			orderScheduleSearch.setOrderId(id);
			List<OrderScheduleVo> scheduleVoList = this.orderScheduleService.getList(orderScheduleSearch);
			orderVo.setScheduleVoList(scheduleVoList);
			this.request.setAttribute("orderVo",orderVo);
			this.request.setAttribute("orderScheduleStateMap", OrderScheduleState.OrderScheduleStateMap);
		}
		return "/jsp/owner/admin/order/edit";
	}	
	
	/**
	 * 后台-编辑保存
	 * @param orderVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(OrderVo orderVo) {
		Result result = null;
		try {
			result = this.orderService.editOrder(orderVo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 后台-编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/toView", method = RequestMethod.GET)
	public String toView(Integer id) {
		if(id != null){
			OrderVo orderVo = this.orderService.getById(id);
			OrderScheduleSearch orderScheduleSearch = new OrderScheduleSearch();
			orderScheduleSearch.setOrderId(id);
			List<OrderScheduleVo> scheduleVoList = this.orderScheduleService.getList(orderScheduleSearch);
			orderVo.setScheduleVoList(scheduleVoList);
			this.request.setAttribute("orderVo",orderVo);
		}
		this.request.setAttribute("orderScheduleStateMap", OrderScheduleState.OrderScheduleStateMap);
		return "/jsp/owner/admin/order/view";
	}	
	
	/**
	 * 后台-编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/appointInfo", method = RequestMethod.GET)
	public String appointInfo(Integer orderId) {
		if(orderId != null){
			OrderVo orderVo = this.orderService.getById(orderId);
			this.request.setAttribute("orderVo",orderVo);
			if(orderVo.getAppointId() != null){
				AppointmentVo appointmentVo = this.appointmentService.getById(orderVo.getAppointId());
				if(appointmentVo != null){
					if(appointmentVo.getHomeTime() != null){
						appointmentVo.setHomeTimeStr(DateUtil.format(appointmentVo.getHomeTime()));
					}
					if(appointmentVo.getDecoratingTime() != null){
						appointmentVo.setDecoratingTimeStr(DateUtil.format(appointmentVo.getDecoratingTime(),true));
					}
					if(appointmentVo.getAppointDate() != null){
						appointmentVo.setAppointDateStr(DateUtil.format(appointmentVo.getAppointDate(),true));
					}
				}
				this.request.setAttribute("appointmentVo",appointmentVo);
			}
		}
		return "/jsp/owner/admin/order/appointInfo";
	}	
	
	/**
	 * 后台-计算页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/toSettle", method = RequestMethod.GET)
	public String toSettle(Integer id) {
		OrderVo orderVo = null;                 //订单信息
		QuotationVo quotationVo = null;         //优惠券信息
		List<CouponVo> couponVoList = null;     //报价单信息
		if(id != null){
			orderVo = this.orderService.getById(id);
			if(orderVo.getCreateTime() != null){
				orderVo.setCreateTimeStr(DateUtil.format(orderVo.getCreateTime(), false));
			}
			if(orderVo != null && orderVo.getDesignId() != null){
				quotationVo = this.quotationService.getByDesignId(orderVo.getDesignId());
			}
			CouponSearch couponSearch = new CouponSearch();
			couponSearch.setSort("partner_coupon.state");
			couponSearch.setOwnerPhone(orderVo.getMobilePhone());
			couponVoList = this.couponService.getList(couponSearch);
		}
		this.request.setAttribute("orderVo",orderVo);          
		this.request.setAttribute("quotationVo",quotationVo);  
		this.request.setAttribute("couponVoList",couponVoList);
		if(orderVo.getSourceType() == 1){
			return "/jsp/owner/admin/order/settle1";
		}else if(orderVo.getSourceType() == 2){
			return "/jsp/owner/admin/order/settle2";
		}
		return null;
	}	
	
	/**
	 * 后台-结算保存
	 * @param orderVo
	 * @return
	 */
	@RequestMapping(value = "/admin/settle", method = RequestMethod.POST)
	@ResponseBody
	public Result settle(Integer id,String couponIds, Integer sourceType) {
		Result result = null;
		try {
			if(sourceType == null){
				result = new Result();
				result.setError(ResultCode.CODE_STATE_4006, "添加订单类型失败");
				return result;
			}
			if(sourceType.intValue() == 1){//结算预约单生产的订单
				result = this.orderService.settle(id, couponIds);
			}else if(sourceType.intValue() == 2){//结算后台添加的订单
				result = this.orderService.settleOrder(id, couponIds);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	
}
