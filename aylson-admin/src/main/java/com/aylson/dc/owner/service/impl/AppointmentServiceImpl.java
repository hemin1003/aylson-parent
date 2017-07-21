package com.aylson.dc.owner.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aylson.core.constants.SysConstant.BillCodePrefix;
import com.aylson.core.exception.ServiceException;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.base.GeneralConstant.AttachmentType;
import com.aylson.dc.base.GeneralConstant.SourceTable;
import com.aylson.dc.base.OwnerGeneralConstant.AppointState;
import com.aylson.dc.base.OwnerGeneralConstant.CostType;
import com.aylson.dc.base.OwnerGeneralConstant.FlowNodeType;
import com.aylson.dc.base.OwnerGeneralConstant.OrderFlowState;
import com.aylson.dc.base.PartnerGeneralConstant.CouponState;
import com.aylson.dc.base.PartnerGeneralConstant.WalletBillType;
import com.aylson.dc.busi.search.CostSearch;
import com.aylson.dc.busi.search.FlowNodeSearch;
import com.aylson.dc.busi.service.CostService;
import com.aylson.dc.busi.service.FlowNodeService;
import com.aylson.dc.busi.vo.CostVo;
import com.aylson.dc.busi.vo.FlowNodeVo;
import com.aylson.dc.owner.dao.AppointmentDao;
import com.aylson.dc.owner.po.Appointment;
import com.aylson.dc.owner.search.AppointmentSearch;
import com.aylson.dc.owner.search.OrderSearch;
import com.aylson.dc.owner.search.OwnerInfoSearch;
import com.aylson.dc.owner.service.AppointmentService;
import com.aylson.dc.owner.service.OrderService;
import com.aylson.dc.owner.service.OwnerInfoService;
import com.aylson.dc.owner.vo.AppointmentVo;
import com.aylson.dc.owner.vo.OrderVo;
import com.aylson.dc.owner.vo.OwnerInfoVo;
import com.aylson.dc.partner.po.PartnerWalletBill;
import com.aylson.dc.partner.search.CouponApplySearch;
import com.aylson.dc.partner.search.CouponSearch;
import com.aylson.dc.partner.service.CouponApplyService;
import com.aylson.dc.partner.service.CouponService;
import com.aylson.dc.partner.service.PartnerAccountService;
import com.aylson.dc.partner.vo.CouponApplyVo;
import com.aylson.dc.partner.vo.CouponVo;
import com.aylson.dc.sys.search.AttachmentSearch;
import com.aylson.dc.sys.service.AttachmentService;
import com.aylson.dc.sys.vo.AttachmentVo;
import com.aylson.utils.BillNumUtils;
import com.aylson.utils.DateUtil;
import com.aylson.utils.StringUtil;
import com.aylson.utils.VerificationUtils;


@Service
public class AppointmentServiceImpl extends BaseServiceImpl<Appointment,AppointmentSearch> implements AppointmentService {

	@Autowired
	private AppointmentDao appointmentDao;
	@Autowired
	private CostService costService;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private FlowNodeService flowNodeService;
	@Autowired
	private CouponService couponService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OwnerInfoService ownerInfoService;
	@Autowired
	private CouponApplyService couponApplyService;
	@Autowired
	private PartnerAccountService partnerAccountService;

	@Override
	protected BaseDao<Appointment,AppointmentSearch> getBaseDao() {
		return appointmentDao;
	}

	/**
	 * 获取预约单信息
	 */
	@Override
	public Result getAppointInfo(Integer appointId){
		Result result = new Result();
		Map<String, Object> data = new HashMap<String, Object>();
		if(appointId == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取预约信息主键失败！");
			return result;
		}
		AppointmentVo appointmentVo = this.getById(appointId);
		if(appointmentVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取预约信息失败！");
			return result;
		}
		data.put("appointmentVo", appointmentVo);//预约信息
		//预约附件详情
		AttachmentSearch agentAttachSearch = new AttachmentSearch();
		agentAttachSearch.setSourceId(appointId);
		agentAttachSearch.setSourceType(SourceTable.OWNER_APPOINT);
		agentAttachSearch.setType(AttachmentType.AGENT);
		agentAttachSearch.setIsEffect(true);
		List<AttachmentVo> agentAttachList = this.attachmentService.getList(agentAttachSearch);
		//Map<String, List<AttachmentVo>> agentAttachGroup = this.attachmentService.getAttachmentGroup(agentAttachList);
		data.put("agentAttach", agentAttachList);//预约-经销商附件信息
		AttachmentSearch orgAttachSearch = new AttachmentSearch();
		orgAttachSearch.setSourceId(appointId);
		orgAttachSearch.setSourceType(SourceTable.OWNER_APPOINT);
		orgAttachSearch.setType(AttachmentType.ORG);
		orgAttachSearch.setIsEffect(true);
		List<AttachmentVo> orgAttachList = this.attachmentService.getList(orgAttachSearch);
		//Map<String, List<AttachmentVo>> orgAttachGroup = this.attachmentService.getAttachmentGroup(orgAttachList);
		data.put("orgAttach", orgAttachList);//预约-总部附件信息
		//预约费用信息
		CostSearch costSearch = new CostSearch();
		costSearch.setSourceId(appointId);
		costSearch.setSourceType(SourceTable.OWNER_APPOINT);
		List<CostVo> costList = this.costService.getList(costSearch);
		data.put("costList", costList);//预约-费用信息
		//现金券信息
		CouponSearch couponSearch = new CouponSearch();
		couponSearch.setOwnerPhone(appointmentVo.getMobilePhone());
		couponSearch.setState(0);//未使用
		List<CouponVo> couponList = this.couponService.getList(couponSearch);
		CouponSearch couponSearch2 = new CouponSearch();
		couponSearch2.setOwnerPhone(appointmentVo.getMobilePhone());
		couponSearch2.setOrderId(appointmentVo.getId());
		couponSearch2.setOrderCode(appointmentVo.getBillCode());
		List<CouponVo> chosenCouponList = this.couponService.getList(couponSearch2);
		couponList.addAll(chosenCouponList);
		data.put("couponList", couponList);//预约-费用信息
		//意见列表
		FlowNodeSearch flowNodeSearch = new FlowNodeSearch();
		flowNodeSearch.setSourceId(appointId);
		flowNodeSearch.setSourceType(SourceTable.OWNER_APPOINT);
		flowNodeSearch.setState(AppointState.WAIT_OFFER_AGAIN);
		List<FlowNodeVo> optionList = this.flowNodeService.getList(flowNodeSearch);
		data.put("optionList", optionList);//意见列表
		//订单信息
		OrderSearch orderSearch = new OrderSearch();
		orderSearch.setAppointId(appointId);
		List<OrderVo> orderList = this.orderService.getList(orderSearch);
		OrderVo orderVo = null;
		if(orderList != null && orderList.size()>0){
			orderVo = orderList.get(0);
		}
		data.put("orderVo", orderVo);//订单信息
		result.setOK(ResultCode.CODE_STATE_200, "查询成功",data);
		return result;
	}
	
	/**
	 * 保存/提交
	 */
	@Override
	@Transactional
	public Result save(AppointmentVo appointmentVo) {
		Result result = new Result();
		Boolean flag = false;
		//1、信息校验
		//查询优惠券信息
		if(StringUtil.isNotEmpty(appointmentVo.getCouponIds())){
			CouponSearch couponSearch = new CouponSearch();
			couponSearch.setIds(appointmentVo.getCouponIds().split(","));
			List<CouponVo> couponList = this.couponService.getList(couponSearch);//需要更新的优惠券列表
			appointmentVo.setCouponList(couponList);
		}
		result = this.check(appointmentVo);
		if(!result.isSuccess()) return result;
		//2、信息保存
		//保存节点信息
		result = this.saveFlowNode(appointmentVo);
		if(!result.isSuccess()) return result;
		//保存附件信息
		result = this.saveAttach(appointmentVo);
		if(!result.isSuccess()) return result;
		//保存优惠券信息
		result = this.saveCoupon(appointmentVo);
		if(!result.isSuccess()) return result;
		//保存费用信息
		result = this.saveCostInfo(appointmentVo);
		if(!result.isSuccess()) return result;
		//保存订单信息
		result = this.saveOrder(appointmentVo);
		if(!result.isSuccess()) return result;
		//保存客户信息
		result = this.saveOwnerInfo(appointmentVo);
		if(!result.isSuccess()) return result;
		//保存预约信息
		if(!appointmentVo.getIsOnlySave() && appointmentVo.getFlowNodeVo() != null){
			appointmentVo.setState(appointmentVo.getFlowNodeVo().getState());
		}
		if(StringUtil.isNotEmpty(appointmentVo.getHomeTimeStr())){
			appointmentVo.setHomeTime(DateUtil.format(appointmentVo.getHomeTimeStr()));
		}
		if(StringUtil.isNotEmpty(appointmentVo.getDecoratingTimeStr())){
			appointmentVo.setDecoratingTime(DateUtil.format(appointmentVo.getDecoratingTimeStr(),"yyyy-MM-dd"));
		}
		flag = this.edit(appointmentVo);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "更新预约信息失败，请稍后再试");
			throw new ServiceException("更新预约信息失败");
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		return result;
	}
	
	/**
	 * 提交下一步
	 */
	@Override
	@Transactional
	public Result next(AppointmentVo appointmentVo) {
		Result result = new Result();
		Boolean flag = false;
		//1、信息校验
		//封装现有的数据进行校验
		//查询优惠券信息
		if(!appointmentVo.getIsOnlySave() && AppointState.APPOINT != appointmentVo.getState().intValue()){
			CouponSearch couponSearch= new CouponSearch();
			couponSearch.setOrderId(appointmentVo.getId());
			couponSearch.setOrderCode(appointmentVo.getBillCode());
			List<CouponVo> couponList = this.couponService.getList(couponSearch);
			appointmentVo.setCouponList(couponList);
		}
		//查询费用细腻
		CostSearch costSearch = new CostSearch();
		costSearch.setSourceId(appointmentVo.getId());
		costSearch.setSourceType(SourceTable.OWNER_APPOINT);
		List<CostVo> costList = this.costService.getList(costSearch);
		appointmentVo.setCostVoList(costList);
		//查询附件信息
		AttachmentSearch attachmentSearch = new AttachmentSearch();
		attachmentSearch.setSourceId(appointmentVo.getId());
		attachmentSearch.setSourceType(SourceTable.OWNER_APPOINT);
		attachmentSearch.setType(AttachmentType.AGENT);//附件类型
		List<AttachmentVo> attachmentVoList = this.attachmentService.getList(attachmentSearch);
		appointmentVo.setAttachmentVoList(attachmentVoList);
		result = this.check(appointmentVo);
		if(!result.isSuccess()) return result;
		//2、信息保存
		//保存节点信息
		result = this.saveFlowNode(appointmentVo);
		result = this.saveOrder(appointmentVo);
		//result = this.saveCoupon(appointmentVo);
		result = this.saveOwnerInfo(appointmentVo);
		//如果是预约处理，那么初始化费用信息
		if(!appointmentVo.getIsOnlySave() && AppointState.APPOINT == appointmentVo.getState().intValue()){
			if(!appointmentVo.getIsOnlySave()){
				result = this.costService.initAppointCost(SourceTable.OWNER_APPOINT, appointmentVo.getId());
				if(!result.isSuccess()){
					throw new ServiceException(result.getMessage().toString());
				}
			}
		}
		//保存预约信息
		if(!appointmentVo.getIsOnlySave() && appointmentVo.getFlowNodeVo() != null){
			appointmentVo.setState(appointmentVo.getFlowNodeVo().getState());
		}
		flag = this.edit(appointmentVo);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "更新预约信息失败，请稍后再试");
			throw new ServiceException("更新预约信息失败");
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		return result;
	}
	
	/**
	 * 校验信息
	 * @param appointmentVo
	 * @return
	 */
	public Result check(AppointmentVo appointmentVo){
		Result result = new Result();
		List<CouponVo> couponList = appointmentVo.getCouponList();
		Date curDate = appointmentVo.getUpdateTime() == null?new Date():appointmentVo.getUpdateTime();
		if(appointmentVo.getId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "预约信息主键不能为空！");
			return result;
		}
		if(appointmentVo.getState() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取预约信息状态失败，请稍后再试或者联系管理员");
			return result;
		}
		AppointmentVo old = this.getById(appointmentVo.getId());
		if(old == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取预约信息失败，请稍后再试或者联系管理员");
			return result;
		}
		if(old.getState() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取预约信息状态失败，请稍后再试或者联系管理员");
			return result;
		}
		if(old.getState().intValue() != appointmentVo.getState().intValue()){
			result.setError(ResultCode.CODE_STATE_4006, "状态信息有误，请稍后再试或者联系管理员");
			return result;
		}
		//获取存在的一些预约信息
		if(StringUtil.isEmpty(appointmentVo.getMobilePhone())){
			appointmentVo.setMobilePhone(old.getMobilePhone());
		}
		if(appointmentVo.getByAgentUserId() == null){
			appointmentVo.setByAgent(old.getByAgent());
			appointmentVo.setByAgentUserId(old.getByAgentUserId());
		}
		
		if(!appointmentVo.getIsOnlySave()){
			//待预约处理：发送下一步校验
			if(AppointState.APPOINT == old.getState().intValue()){//预约处理
				if("web_appoint".equals(old.getSource())){
					if(StringUtil.isEmpty(appointmentVo.getName())){
						result.setError(ResultCode.CODE_STATE_4006, "姓名不能为空");
						return result;
					}
					if(StringUtil.isEmpty(appointmentVo.getMobilePhone())){
						result.setError(ResultCode.CODE_STATE_4006, "电话不能为空");
						return result;
					}
					if(!VerificationUtils.isValid(appointmentVo.getMobilePhone(), VerificationUtils.MOBILE)){
						result.setError(ResultCode.CODE_STATE_4006, "手机号码格式有误");
						return result;
					}
					if(StringUtil.isEmpty(appointmentVo.getProvince()) || appointmentVo.getProvinceId() == null){
						result.setError(ResultCode.CODE_STATE_4006, "所在省份不能为空");
						return result;
					}
					if(StringUtil.isEmpty(appointmentVo.getCity()) || appointmentVo.getCityId() == null){
						result.setError(ResultCode.CODE_STATE_4006, "所在城市不能为空");
						return result;
					}
					if(StringUtil.isEmpty(appointmentVo.getArea()) || appointmentVo.getAreaId() == null){
						result.setError(ResultCode.CODE_STATE_4006, "所在区域不能为空");
						return result;
					}
				}
				if(StringUtil.isEmpty(appointmentVo.getAddress())){
					result.setError(ResultCode.CODE_STATE_4006, "装修地址不能为空");
					return result;
				}
				if(StringUtil.isEmpty(appointmentVo.getByAgent()) || appointmentVo.getByAgentUserId() == null){
					result.setError(ResultCode.CODE_STATE_4006, "请选择所属经销商");
					return result;
				}
			}else{
				//附件信息校验
				if(appointmentVo.getAttachmentVoList() == null || appointmentVo.getAttachmentVoList().isEmpty()){
					result.setError(ResultCode.CODE_STATE_4006, "请上传相关附件");
					return result;
				}
				//费用校验
				if(appointmentVo.getCostVoList() == null || appointmentVo.getCostVoList().isEmpty()){
					result.setError(ResultCode.CODE_STATE_4006, "请填写相关费用信息");
					return result;
				}
				//【待设计报价】【待重新设计报价】【待下单生产】 发送下一步校验
				Map<Integer,CostVo> costMap = this.getCostMap(appointmentVo.getCostVoList());
				if(AppointState.WAIT_OFFER == appointmentVo.getState().intValue()
						|| AppointState.WAIT_OFFER_AGAIN == appointmentVo.getState().intValue()
						|| AppointState.WAIT_ORDER == appointmentVo.getState().intValue()){
					CostVo productCost = costMap.get(CostType.PRODUCTCOST);//产品费
					if(productCost == null || productCost.getCostValue() == null){
						result.setError(ResultCode.CODE_STATE_4006, "产品费信息不能为空");
						return result;
					}
					if(productCost.getCostValue() < 0.0f){
						result.setError(ResultCode.CODE_STATE_4006, "产品费不能小于0");
						return result;
					}
					CostVo freightCost = costMap.get(CostType.FREIGHT);//运输费
					if(freightCost == null || freightCost.getCostValue() == null){
						result.setError(ResultCode.CODE_STATE_4006, "运输费信息不能为空");
						return result;
					}
					if(freightCost.getCostValue() < 0.0f){
						result.setError(ResultCode.CODE_STATE_4006, "运输费不能小于0");
						return result;
					}
					CostVo installationFeeCost = costMap.get(CostType.INSTALLATION_FEE);//安装费
					if(installationFeeCost == null || installationFeeCost.getCostValue() == null){
						result.setError(ResultCode.CODE_STATE_4006, "安装费信息不能为空");
						return result;
					}
					if(freightCost.getCostValue() < 0.0f){
						result.setError(ResultCode.CODE_STATE_4006, "安装费不能小于0");
						return result;
					}
					Float productCostValue = productCost.getCostValue();
					Float pfreightCostValue = freightCost.getCostValue();
					Float installationFeeCostValue = installationFeeCost.getCostValue();
					Float agentDiscount = costMap.get(CostType.DISCOUNT).getCostValue();//折扣
					Float activityCouponAmount = costMap.get(CostType.ACTIVITY_COUPON).getCostValue();//优惠券金额
					Float couponValueSum = 0.0f;  //现金券减免总金额
					Float finalSalesAmount = (productCostValue+pfreightCostValue+installationFeeCostValue)*agentDiscount/100-activityCouponAmount;
					//现金券校验
					if(couponList != null && couponList.size() > 0){//如果用了券
						//未使用现金券销售金额
						if(finalSalesAmount.intValue() < 5000){
							result.setError(ResultCode.CODE_STATE_4006, "订单金额至少5000元以上才能使用优惠券");
							return result;
						}
						//CouponSearch couponSearch = new CouponSearch();
						//couponSearch.setIds(appointmentVo.getCouponIds().split(","));
						//couponList = this.couponService.getList(couponSearch);//需要更新的优惠券列表
						for(CouponVo couponVo:couponList){
							/*if(finalSalesAmount.intValue() < couponVo.getAchieveMoney()){
								result.setError(ResultCode.CODE_STATE_4006, couponVo.getCouponCode()+"的优惠券必须满足"+couponVo.getAchieveMoney()+"元才能使用");
								return result;
							}*/
							couponVo.setOrderCode(appointmentVo.getBillCode());
							couponVo.setOrderId(appointmentVo.getId());
							couponVo.setUpdateTime(curDate);
							couponVo.setState(CouponState.HAD_USE);
							couponValueSum += couponVo.getCouponValue();
						}
						//满5000用500，满1万用1000
						Float maxCouponValue = finalSalesAmount*0.1f;
						if(couponValueSum > maxCouponValue.intValue()){
							result.setError(ResultCode.CODE_STATE_4006, "可以使用的优惠券总金额不能超过"+maxCouponValue+"元，请重新选择优惠券");
							return result;
						}
						//appointmentVo.setCouponValueSum(couponValueSum); //现金券减免金额
					}
					appointmentVo.setTurnoverAmount(finalSalesAmount-couponValueSum);//成交金额
					appointmentVo.setSalesAmount(productCostValue+pfreightCostValue+installationFeeCostValue);
				}
			}
		}
		result.setOK(ResultCode.CODE_STATE_200, "信息有效");
		return result;
	}
	
	/**
	 * 保存流程节点
	 * @param appointmentVo
	 * @return
	 */
	@Transactional
	public Result saveFlowNode(AppointmentVo appointmentVo){
		Result result = new Result();
		Boolean flag = false;
		if(!appointmentVo.getIsOnlySave()){//如果提交下一步
			FlowNodeVo flowNodeVo = new FlowNodeVo();
			flowNodeVo.setCreater(appointmentVo.getDealer());
			flowNodeVo.setCreateId(appointmentVo.getDealerId());
			flowNodeVo.setCreateTime(appointmentVo.getUpdateTime());
			if(AppointState.APPOINT == appointmentVo.getState().intValue()){                              //预约处理
				flowNodeVo.setState(AppointState.WAIT_OFFER);
				flowNodeVo.setNodeName(AppointState.AppointStateMap.get(AppointState.WAIT_OFFER));
				flowNodeVo.setRemark("预约单已经处理，提交下一步：待设计报价");
			}else if(AppointState.WAIT_OFFER == appointmentVo.getState().intValue()){                     //待设计报价
				flowNodeVo.setState(AppointState.WAIT_CONFIRM);
				flowNodeVo.setNodeName(AppointState.AppointStateMap.get(AppointState.WAIT_CONFIRM));
				flowNodeVo.setRemark("经销商已经设计报价，提交下一步：待客户确认");
			}else if(AppointState.WAIT_CONFIRM == appointmentVo.getState().intValue()){                   //待客户确认
				//flowNodeVo.setState(AppointState.WAIT_CONFIRM);
				//flowNodeVo.setRemark("经销商已经设计报价，提交下一步：待客户确认");
			}else if(AppointState.WAIT_OFFER_AGAIN == appointmentVo.getState().intValue()){               //待重新报价
				flowNodeVo.setState(AppointState.WAIT_CONFIRM);
				flowNodeVo.setNodeName(AppointState.AppointStateMap.get(AppointState.WAIT_CONFIRM));
				flowNodeVo.setRemark("经销商已经重新设计报价，提交下一步：待客户确认");
			}else if(AppointState.WAIT_ORDER == appointmentVo.getState().intValue()){                     //待下单生产
				flowNodeVo.setState(AppointState.WAIT_AYLSON_CONFIRM);
				flowNodeVo.setNodeName(AppointState.AppointStateMap.get(AppointState.WAIT_AYLSON_CONFIRM));
				flowNodeVo.setRemark("经销商已经确定下单生产，提交下一步：待总部确认订单");
			}else if(AppointState.WAIT_AYLSON_CONFIRM == appointmentVo.getState().intValue()){            //待总部确认
				flowNodeVo.setState(AppointState.AYLSON_CONFIRM);
				flowNodeVo.setNodeName(AppointState.AppointStateMap.get(AppointState.AYLSON_CONFIRM));
				flowNodeVo.setRemark("总部已经确认订单");
			}
			flowNodeVo.setSourceId(appointmentVo.getId());
			flowNodeVo.setSourceType(SourceTable.OWNER_APPOINT);
			flowNodeVo.setType(FlowNodeType.APPOINT);
			flag = this.flowNodeService.add(flowNodeVo);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "保存预约流程节点失败，请稍后再试");
				throw new ServiceException("保存预约流程节点失败");
			}
			appointmentVo.setFlowNodeVo(flowNodeVo);
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		return result;
	}
	
	/**
	 * 保存附件信息
	 * @param appointmentVo
	 * @return
	 */
	@Transactional
	public Result saveAttach(AppointmentVo appointmentVo){
		Result result = new Result();
		Integer attachType = this.getAttachType(appointmentVo.getState().intValue());
		if(attachType == null){
			result.setOK(ResultCode.CODE_STATE_200, "当前不需要上传附件");return result;
		}
		Boolean flag = false;
		Date curDate = appointmentVo.getUpdateTime();
		List<AttachmentVo> attachmentVoList = appointmentVo.getAttachmentVoList();  //本次附件列表
		AttachmentSearch attachmentSearch = new AttachmentSearch();
		attachmentSearch.setIsEffect(true);
		attachmentSearch.setSourceId(appointmentVo.getId());
		attachmentSearch.setSourceType(SourceTable.OWNER_APPOINT);
		attachmentSearch.setType(attachType);//附件类型
		List<AttachmentVo> existAttachmentVoList = this.attachmentService.getList(attachmentSearch);//存在的经销商附件列表
		if(existAttachmentVoList != null && existAttachmentVoList.size() >0){//如果存在
			if(attachmentVoList != null && attachmentVoList.size() > 0){
				List<AttachmentVo> updateList = new ArrayList<AttachmentVo>(); //修改的附件
				List<AttachmentVo> addList = new ArrayList<AttachmentVo>();    //新增的附件
				for(AttachmentVo attach:attachmentVoList){
					if(attach.getId() != null){
						updateList.add(attach);
					}else{
						attach.setSourceId(appointmentVo.getId());
						attach.setType(attachType);
						attach.setSourceType(SourceTable.OWNER_APPOINT);
						attach.setCreateTime(curDate);
						if(!appointmentVo.getIsOnlySave()){
							attach.setIsLock(true);
						}
						addList.add(attach);
					}
				}
				if(updateList != null && updateList.size() > 0){
					for(AttachmentVo update:existAttachmentVoList){
						if(!appointmentVo.getIsOnlySave()){
							update.setIsLock(true);
						}
						for(AttachmentVo temp:updateList){
							if(update.getId().intValue() == temp.getId().intValue()){
								update.setIsEffect(true);
								break;
							}
							update.setIsEffect(false);
						}
					}
					flag = this.attachmentService.batchUpdate(existAttachmentVoList);
					if(!flag){
						result.setError(ResultCode.CODE_STATE_4006, "更新附件信息失败，请稍后再试");
						throw new ServiceException("更新附件信息失败");
					}
				}
				if(addList != null && addList.size()>0){
					flag = this.attachmentService.batchAdd(addList);
					if(!flag){
						result.setError(ResultCode.CODE_STATE_4006, "更新附件信息失败，请稍后再试");
						throw new ServiceException("更新附件信息失败");
					}
				}
			}else{
				for(AttachmentVo attachmentVo:existAttachmentVoList){
					attachmentVo.setIsEffect(false);
				}
				flag = this.attachmentService.batchUpdate(existAttachmentVoList);
			}
			
		}else{//如果不存在
			if(attachmentVoList != null && attachmentVoList.size() > 0){
				for(AttachmentVo attach : attachmentVoList){
					attach.setSourceId(appointmentVo.getId());
					attach.setSourceType(SourceTable.OWNER_APPOINT);
					attach.setType(attachType);
					attach.setCreateTime(curDate);
					if(!appointmentVo.getIsOnlySave()){
						attach.setIsLock(true);
					}
				}
				flag = this.attachmentService.batchAdd(attachmentVoList);
				if(!flag){
					result.setError(ResultCode.CODE_STATE_4006, "保存附件信息失败，请稍后再试");
					throw new ServiceException("保存附件信息失败");
				}
			}
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		return result;
	}
	
	/**
	 * 更新费用信息
	 * @param appointmentVo
	 * @return
	 */
	@Transactional
	public Result saveCostInfo(AppointmentVo appointmentVo){
		Result result = new Result();
		Boolean flag = false;
		//如果是预约处理，那么初始化费用信息
		if(!appointmentVo.getIsOnlySave() && AppointState.APPOINT == appointmentVo.getState().intValue()){
			if(!appointmentVo.getIsOnlySave()){
				result = this.costService.initAppointCost(SourceTable.OWNER_APPOINT, appointmentVo.getId());
				if(!result.isSuccess()){
					throw new ServiceException(result.getMessage().toString());
				}
			}
		}else{
			List<CostVo> agentCostList = appointmentVo.getCostVoList();  //代理商报价单信息
			if(agentCostList != null && agentCostList.size() > 0){
				for(CostVo costVo:agentCostList){
					if(costVo.getCostType() != null && CostType.CASH_COUPON == costVo.getCostType().intValue()){
						costVo.setCostValue(appointmentVo.getCouponValueSum());
					}
				}
				flag = this.costService.batchUpdate(agentCostList);
				if(!flag){
					result.setError(ResultCode.CODE_STATE_4006, "保存经销商报价信息失败，请稍后再试");
					throw new ServiceException("保存经销商报价信息失败");
				}
			}
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		return result;
	}
	
	/**
	 * 处理优惠券信息
	 * @param appointmentVo
	 * @return
	 */
	@Transactional
	public Result saveCoupon(AppointmentVo appointmentVo){
		Result result = new Result();
		Boolean flag = false;
		//预约状态为：待设计报价、待重新设计报价、待下单生产  才能选择现金券
		if(AppointState.WAIT_OFFER == appointmentVo.getState().intValue()
				|| AppointState.WAIT_OFFER_AGAIN == appointmentVo.getState().intValue()
				|| AppointState.WAIT_ORDER == appointmentVo.getState().intValue()){
			Date curDate= appointmentVo.getUpdateTime();               //当前时间
			//如果已经有选中的现金券，先更新为未使用
			CouponSearch search = new CouponSearch();
			search.setOrderId(appointmentVo.getId());
			search.setOrderCode(appointmentVo.getBillCode());
			List<CouponVo> chosenList = this.couponService.getList(search);
			if(chosenList != null && chosenList.size() > 0){//之前已经有选中的现金券了
				for(CouponVo chosen: chosenList){
					chosen.setOrderCode("");
					chosen.setOrderId(-1);
					chosen.setUpdateTime(curDate);
					chosen.setState(CouponState.WAIT_USE);
				}
				flag = this.couponService.batchUpdate(chosenList);
				if(!flag){
					result.setError(ResultCode.CODE_STATE_4006, "更新已经选中的优惠券信息失败");
					throw new ServiceException( "更新已经选中的优惠券信息失败");
				}
			}
			Float couponValueSum = 0.0f;  //现金券减免总金额
			if(StringUtil.isNotEmpty(appointmentVo.getCouponIds())){
				CouponSearch couponSearch = new CouponSearch();
				couponSearch.setIds(appointmentVo.getCouponIds().split(","));
				List<CouponVo> couponList = this.couponService.getList(couponSearch);//需要更新的优惠券列表
				for(CouponVo couponVo:couponList){
					couponVo.setOrderCode(appointmentVo.getBillCode());
					couponVo.setOrderId(appointmentVo.getId());
					couponVo.setUpdateTime(curDate);
					couponVo.setState(CouponState.HAD_USE);
					couponValueSum += couponVo.getCouponValue();
				}
//			List<CouponVo> couponList = appointmentVo.getCouponList();
				if(couponList != null && couponList.size() >0){
					appointmentVo.setCouponValueSum(couponValueSum);
					flag = this.couponService.batchUpdate(couponList);
					if(!flag){
						result.setError(ResultCode.CODE_STATE_4006, "更新重新选中的优惠券信息失败");
						throw new ServiceException( "更新重新选中的优惠券信息失败");
					}
				}
			}
		}
		
		result.setOK(ResultCode.CODE_STATE_200, "更新优惠券信息成功");
		return result;
	}
	
	/**
	 * 保存订单信息
	 * @param appointmentVo
	 * @return
	 */
	@Transactional
	public Result saveOrder(AppointmentVo appointmentVo){
		Result result = new Result();
		AppointmentVo old = this.getById(appointmentVo.getId());
		CouponApplyVo couponApplyVo = null;    //优惠券申请信息，如果不为null，表示需要计算分红
		//提交下一步并且状态为代下单生产时添加订单
		if(!appointmentVo.getIsOnlySave() && AppointState.WAIT_ORDER == appointmentVo.getState().intValue()){
			Date curDate = new Date();
			CouponApplySearch couponApplySearch = new CouponApplySearch();
			couponApplySearch.setOwnerPhone(appointmentVo.getMobilePhone());
			couponApplySearch.setIsBind(true);
			List<CouponApplyVo> couponApplyList = this.couponApplyService.getList(couponApplySearch);
			if(couponApplyList != null && couponApplyList.size() > 0){
				couponApplyVo = couponApplyList.get(0);
			}
			OrderVo orderVo = new OrderVo();
			orderVo.setByAgent(old.getByAgent());
			orderVo.setByAgentUserId(old.getByAgentUserId());
			orderVo.setAppointId(old.getId());
			orderVo.setAppointNo(old.getBillCode());
			orderVo.setMobilePhone(old.getMobilePhone());
			orderVo.setName(old.getName());
			orderVo.setProvince(old.getProvince());
			orderVo.setProvinceId(old.getProvinceId());
			orderVo.setCity(old.getCity());
			orderVo.setCityId(old.getCityId());
			orderVo.setArea(old.getArea());
			orderVo.setAreaId(old.getAreaId());
			orderVo.setAddress(old.getAddress());
			orderVo.setDecorateProject(old.getDecorateProject());
			orderVo.setDecorateProjectTypes(old.getDecorateProjectTypes());
			orderVo.setAgentRemark(appointmentVo.getAgentRemark());
			orderVo.setCreateTime(curDate);
			orderVo.setUpdateTime(curDate);
			orderVo.setSourceType(1);     //后台生产的订单
			orderVo.setFlowState(OrderFlowState.AGENT_CONFIRM);//
			orderVo.setOrderNo(BillNumUtils.getBillCode(BillCodePrefix.ORDER,orderVo.getMobilePhone()));
			orderVo.setTurnoverAmount(appointmentVo.getTurnoverAmount());
			orderVo.setSalesAmount(appointmentVo.getSalesAmount());
			orderVo.setOrderTime(curDate);
			if(couponApplyVo != null){
				orderVo.setPartnerId(couponApplyVo.getApplierId());//将该订单归属到合伙人
			}
			Boolean flag = this.orderService.add(orderVo);
			if(!flag){
				throw new ServiceException("添加订单失败");
			}
			result.setData(orderVo);
			//结算
			if(couponApplyVo != null){
				PartnerWalletBill partnerWalletBill = new PartnerWalletBill();
				partnerWalletBill.setAccountId(couponApplyVo.getApplierId());
				partnerWalletBill.setWallet(appointmentVo.getTurnoverAmount()*0.1f);
				partnerWalletBill.setCreateTime(appointmentVo.getUpdateTime());
				partnerWalletBill.setDescription("订单号："+orderVo.getOrderNo());
				partnerWalletBill.setSourceId(orderVo.getId());
				partnerWalletBill.setSourceType(SourceTable.OWNER_ORDER);
				partnerWalletBill.setType(WalletBillType.SEND_BONUS);
				partnerWalletBill.setAgentUserId(old.getByAgentUserId());
				result = this.partnerAccountService.updateWallet(partnerWalletBill);
				if(!result.isSuccess()){
					result.setError(ResultCode.CODE_STATE_4006, "计算合伙人分红操作失败");
					throw new ServiceException("计算合伙人分红操作失败");
				}
			}
		}
		if(AppointState.WAIT_AYLSON_CONFIRM == appointmentVo.getState().intValue()){
			OrderVo orderVo = appointmentVo.getOrderVo();
			Boolean flag = this.orderService.edit(orderVo);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "更新订单信息失败");
				throw new ServiceException("更新订单信息失败");
			}
		}
		result.setOK(ResultCode.CODE_STATE_200, "当前状态不能添加订单");
		return result;
	}
	
	/**
	 * 保存客户信息
	 * @param appointmentVo
	 * @return
	 */
	@Transactional
	public Result saveOwnerInfo(AppointmentVo appointmentVo){
		Result result = new Result();
		Boolean flag = false;
		if(!appointmentVo.getIsOnlySave() && AppointState.WAIT_ORDER == appointmentVo.getState().intValue()){
			OwnerInfoSearch ownerInfoSearch = new OwnerInfoSearch();
			ownerInfoSearch.setCreaterId(appointmentVo.getByAgentUserId());
			ownerInfoSearch.setMobilePhoneLike(appointmentVo.getMobilePhone());
			List<OwnerInfoVo> ownerInfoList = this.ownerInfoService.getList(ownerInfoSearch);
			if(ownerInfoList == null || ownerInfoList.isEmpty()){
				AppointmentVo old = this.getById(appointmentVo.getId());
				OwnerInfoVo ownerInfoVo = new OwnerInfoVo();
				ownerInfoVo.setName(old.getName());
				ownerInfoVo.setMobilePhone(old.getMobilePhone());
				ownerInfoVo.setProvince(old.getProvince());
				ownerInfoVo.setProvinceId(old.getProvinceId());
				ownerInfoVo.setCity(old.getCity());
				ownerInfoVo.setCityId(old.getCityId());
				ownerInfoVo.setArea(old.getArea());
				ownerInfoVo.setAreaId(old.getAreaId());
				ownerInfoVo.setAddress(old.getAddress());
				ownerInfoVo.setSourceType(1);//预约下单
				ownerInfoVo.setCreaterId(old.getByAgentUserId());
				flag = this.ownerInfoService.add(ownerInfoVo);
				if(!flag){
					result.setError(ResultCode.CODE_STATE_4006, "保存客户信息失败");
					throw new ServiceException("保存客户信息失败");
				}
			}
		}
		result.setOK(ResultCode.CODE_STATE_200, "保存客户信息成功");
		return result;
	}
	
	/**
	 * 转换报价信息
	 * @param costVoList
	 * @return
	 */
	private Map<Integer,CostVo> getCostMap(List<CostVo> costVoList){
		if(costVoList == null || costVoList.isEmpty()) return null;
		Map<Integer,CostVo> costMap = new HashMap<Integer,CostVo>();
		for(CostVo cost:costVoList){
			if(CostType.CostTypeMap.containsKey(cost.getCostType())){
				costMap.put(cost.getCostType(), cost);
			}
		}
		return costMap;
	}
	
	/**
	 * 根据状态获取附件类型
	 * @param appointState
	 * @return
	 */
	public Integer getAttachType(int appointState){
		Integer attachType = null;
		if(AppointState.WAIT_OFFER == appointState
				|| AppointState.WAIT_OFFER_AGAIN == appointState
					|| AppointState.WAIT_ORDER == appointState){
			attachType = AttachmentType.AGENT;
		}else if(AppointState.WAIT_AYLSON_CONFIRM == appointState){
			attachType = AttachmentType.ORG;
		}
		return attachType;
	}
		
	/**
	 * 保存附件信息
	 * @param attachType 5经销商附件   6总部附件
	 * @param appointmentVo
	 * @return
	 */
	@Transactional
	public Result saveAttach(AppointmentVo appointmentVo, Integer attachType){
		Result result = new Result();
		Boolean flag = false;
		Date curDate = new Date();
		if(appointmentVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取预约信息失败，请稍后再试");
			return result;
		}
		if(attachType == null){
			result.setError(ResultCode.CODE_STATE_4006, "附件类型不能为空");
			return result;
		}
		List<AttachmentVo> attachmentVoList = appointmentVo.getAttachmentVoList();  //本次附件列表
		AttachmentSearch attachmentSearch = new AttachmentSearch();
		attachmentSearch.setSourceId(appointmentVo.getId());
		attachmentSearch.setSourceType(SourceTable.OWNER_APPOINT);
		attachmentSearch.setType(attachType);//附件类型
		List<AttachmentVo> existAttachmentVoList = this.attachmentService.getList(attachmentSearch);//存在的经销商附件列表
		if(existAttachmentVoList != null && existAttachmentVoList.size() >0){//如果存在
			if(attachmentVoList != null && attachmentVoList.size() > 0){
				List<AttachmentVo> updateList = new ArrayList<AttachmentVo>(); //修改的附件
				List<AttachmentVo> addList = new ArrayList<AttachmentVo>();    //新增的附件
				for(AttachmentVo attach:attachmentVoList){
					if(attach.getId() != null){
						updateList.add(attach);
					}else{
						attach.setSourceId(appointmentVo.getId());
						attach.setSourceType(SourceTable.OWNER_APPOINT);
						attach.setType(attachType);
						attach.setCreateTime(curDate);
						if(!appointmentVo.getIsOnlySave()){
							attach.setIsLock(true);
						}
						addList.add(attach);
					}
				}
				if(updateList != null && updateList.size() > 0){
					for(AttachmentVo update:existAttachmentVoList){
						if(!appointmentVo.getIsOnlySave()){
							update.setIsLock(true);
						}
						for(AttachmentVo temp:updateList){
							if(update.getId().intValue() == temp.getId().intValue()){
								update.setIsEffect(true);
								break;
							}
							update.setIsEffect(false);
						}
					}
					flag = this.attachmentService.batchUpdate(existAttachmentVoList);
					if(!flag){
						result.setError(ResultCode.CODE_STATE_4006, "更新附件信息失败，请稍后再试");
						throw new ServiceException("更新附件信息失败");
					}
				}
				if(addList != null && addList.size()>0){
					flag = this.attachmentService.batchAdd(addList);
					if(!flag){
						result.setError(ResultCode.CODE_STATE_4006, "更新附件信息失败，请稍后再试");
						throw new ServiceException("更新附件信息失败");
					}
				}
			}else{
				for(AttachmentVo attachmentVo:existAttachmentVoList){
					attachmentVo.setIsEffect(false);
				}
				flag = this.attachmentService.batchUpdate(existAttachmentVoList);
			}
			
		}else{//如果不存在
			if(attachmentVoList != null && attachmentVoList.size() > 0){
				for(AttachmentVo attach : attachmentVoList){
					attach.setSourceId(appointmentVo.getId());
					attach.setSourceType(SourceTable.OWNER_APPOINT);
					attach.setType(attachType);
					attach.setCreateTime(curDate);
					if(!appointmentVo.getIsOnlySave()){
						attach.setIsLock(true);
					}
				}
				flag = this.attachmentService.batchAdd(attachmentVoList);
				if(!flag){
					result.setError(ResultCode.CODE_STATE_4006, "保存附件信息失败，请稍后再试");
					throw new ServiceException("保存附件信息失败");
				}
			}
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		return result;
	}

	/**
	 * 添加预约
	 */
	@Override
	public Result addAppoint(AppointmentVo appointmentVo) {
		Result result = new Result();
		Boolean flag = false;
		if(StringUtil.isEmpty(appointmentVo.getName())){
			result.setError(ResultCode.CODE_STATE_4006, "姓名不能为空");
			return result;
		}
		if(StringUtil.isEmpty(appointmentVo.getMobilePhone())){
			result.setError(ResultCode.CODE_STATE_4006, "电话不能为空");
			return result;
		}
		if(!VerificationUtils.isValid(appointmentVo.getMobilePhone(), VerificationUtils.MOBILE)){
			result.setError(ResultCode.CODE_STATE_4006, "手机号码格式有误");
			return result;
		}
		if(!appointmentVo.getIsOnlySave()){
			if(StringUtil.isEmpty(appointmentVo.getProvince()) || appointmentVo.getProvinceId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "所在省份不能为空");
				return result;
			}
			if(StringUtil.isEmpty(appointmentVo.getCity()) || appointmentVo.getCityId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "所在城市不能为空");
				return result;
			}
			if(StringUtil.isEmpty(appointmentVo.getArea()) || appointmentVo.getAreaId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "所在区域不能为空");
				return result;
			}
			if(StringUtil.isEmpty(appointmentVo.getAddress())){
				result.setError(ResultCode.CODE_STATE_4006, "装修地址不能为空");
				return result;
			}
			if(StringUtil.isEmpty(appointmentVo.getByAgent()) || appointmentVo.getByAgentUserId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "请选择所属经销商");
				return result;
			}
		}
		//保存预约信息
		Date curDate = appointmentVo.getUpdateTime();
		appointmentVo.setState(AppointState.APPOINT);
		if(!appointmentVo.getIsOnlySave()){
			appointmentVo.setState(AppointState.WAIT_OFFER);//如果提交下一步，更新为：待设计报价状态
		}
		appointmentVo.setAppointDate(curDate);
		appointmentVo.setSource("web_appoint"); //添加来源：后台添加预约
		appointmentVo.setBillCode(BillNumUtils.getBillCode(BillCodePrefix.APPOINTMENT,appointmentVo.getMobilePhone()));
		if(StringUtil.isEmpty(appointmentVo.getHomeTimeStr())){
			appointmentVo.setHomeTime(DateUtil.format(appointmentVo.getHomeTimeStr()));
		}
		if(StringUtil.isEmpty(appointmentVo.getDecoratingTimeStr())){
			appointmentVo.setDecoratingTime(DateUtil.format(appointmentVo.getDecoratingTimeStr()));
		}
		flag = this.add(appointmentVo);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "保存预约信息失败");
			return result;
		}
		//2保存业务流程节点
		if(!appointmentVo.getIsOnlySave()){
			FlowNodeVo flowNodeVo = new FlowNodeVo();
			flowNodeVo.setCreater(appointmentVo.getDealer());
			flowNodeVo.setCreateId(appointmentVo.getDealerId());
			flowNodeVo.setCreateTime(curDate);
			flowNodeVo.setNodeName(AppointState.AppointStateMap.get(AppointState.WAIT_OFFER));
			flowNodeVo.setRemark("通过后台添加预约，提交下一步待预约处理");
			flowNodeVo.setSourceId(appointmentVo.getId());
			flowNodeVo.setSourceType(SourceTable.OWNER_APPOINT);
			flowNodeVo.setState(AppointState.APPOINT);
			flowNodeVo.setType(FlowNodeType.APPOINT);
			flag = this.flowNodeService.add(flowNodeVo);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "保存预约流程节点失败，请稍后再试");
				throw new ServiceException("保存预约流程节点失败");
			}
			//初始化费用信息
			result = this.costService.initAppointCost(SourceTable.OWNER_APPOINT, appointmentVo.getId());
			if(!result.isSuccess()){
				throw new ServiceException(result.getMessage().toString());
			}
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		return result;
	}
	
	
}
