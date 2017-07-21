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
import com.aylson.dc.base.OwnerGeneralConstant;
import com.aylson.dc.base.OwnerGeneralConstant.AppointState;
import com.aylson.dc.base.OwnerGeneralConstant.CostType;
import com.aylson.dc.base.OwnerGeneralConstant.FlowNodeType;
import com.aylson.dc.base.OwnerGeneralConstant.OrderFlowState;
import com.aylson.dc.base.PartnerGeneralConstant.CouponState;
import com.aylson.dc.base.PartnerGeneralConstant.WalletBillType;
import com.aylson.dc.busi.search.CostSearch;
import com.aylson.dc.busi.service.CostService;
import com.aylson.dc.busi.service.FlowNodeService;
import com.aylson.dc.busi.vo.CostVo;
import com.aylson.dc.busi.vo.FlowNodeVo;
import com.aylson.dc.owner.dao.OrderDao;
import com.aylson.dc.owner.po.Order;
import com.aylson.dc.owner.search.OrderSearch;
import com.aylson.dc.owner.search.OwnerInfoSearch;
import com.aylson.dc.owner.service.AppointmentService;
import com.aylson.dc.owner.service.OrderScheduleService;
import com.aylson.dc.owner.service.OrderService;
import com.aylson.dc.owner.service.OwnerInfoService;
import com.aylson.dc.owner.vo.AppointmentVo;
import com.aylson.dc.owner.vo.OrderScheduleVo;
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
import com.aylson.utils.StringUtil;
import com.aylson.utils.VerificationUtils;


@Service
public class OrderServiceImpl extends BaseServiceImpl<Order,OrderSearch> implements OrderService {

	@Autowired
	private OrderDao orderDao;
	@Autowired
	private OrderScheduleService orderScheduleService;
	@Autowired
	private CouponApplyService couponApplyService;
	@Autowired
	private PartnerAccountService partnerAccountService;
	@Autowired
	private CouponService couponService;
	@Autowired
	private AttachmentService attachmentService;
//	@Autowired
//	private FlowNodeService flowNodeService;
	@Autowired
	private OwnerInfoService ownerInfoService;
	@Autowired
	private CostService costService;
	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private FlowNodeService flowNodeService;
	
	@Override
	protected BaseDao<Order,OrderSearch> getBaseDao() {
		return orderDao;
	}

	/**
	 * 经销商添加订单
	 */
	@Override
	@Transactional
	public Result addOrder(OrderVo orderVo) {
		Result result = new Result();
		Boolean flag = false;
		List<CouponVo> couponList = null;
		Map<Integer,CostVo> costMap = this.getCostMap(orderVo.getCostVoList());
		//信息校验
		if(StringUtil.isNotEmpty(orderVo.getCouponIds())){
			CouponSearch couponSearch = new CouponSearch();
			couponSearch.setIds(orderVo.getCouponIds().split(","));
			couponList = this.couponService.getList(couponSearch);//需要更新的优惠券列表
		}
		orderVo.setCouponList(couponList);
		if(!orderVo.getIsOnlySave()){//如果确认订单
			result = this.check(orderVo);
			if(!result.isSuccess()) return result;
		}
		//保存订单信息
		CouponApplyVo couponApplyVo = null;    //优惠券申请信息，如果不为null，表示需要计算分红
		Date curDate = new Date();
		if(!orderVo.getIsOnlySave()){//如果确认订单
			CouponApplySearch couponApplySearch = new CouponApplySearch();
			couponApplySearch.setOwnerPhone(orderVo.getMobilePhone());
			couponApplySearch.setIsBind(true);
			List<CouponApplyVo> couponApplyList = this.couponApplyService.getList(couponApplySearch);
			if(couponApplyList != null && couponApplyList.size() > 0){
				couponApplyVo = couponApplyList.get(0);
				orderVo.setPartnerId(couponApplyVo.getApplierId());//将该订单归属到合伙人
			}
			orderVo.setFlowState(OrderFlowState.AGENT_CONFIRM);
			orderVo.setOrderTime(curDate);//下单时间
		}else{
			orderVo.setFlowState(OrderFlowState.WAIT_AGENT_CONFRIM);
		}
		orderVo.setSourceType(2);//后台新增
		orderVo.setCreateTime(curDate);
		orderVo.setUpdateTime(curDate);
		orderVo.setOrderNo(BillNumUtils.getBillCode(BillCodePrefix.ORDER,orderVo.getMobilePhone()));
		flag = this.add(orderVo);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "添加订单失败");
			throw new ServiceException("添加订单失败");
		}
		//保存优惠券信息
		Float couponValueSum = 0.0f;  //现金券减免总金额
		if(couponList != null && couponList.size() >0){
			for(CouponVo couponVo:couponList){
				couponVo.setOrderCode(orderVo.getOrderNo());
				couponVo.setOrderId(orderVo.getId());
				couponVo.setUpdateTime(curDate);
				couponVo.setState(CouponState.HAD_USE);
				couponValueSum += couponVo.getCouponValue();
			}
			flag = this.couponService.batchUpdate(couponList);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "更新重新选中的优惠券信息失败");
				throw new ServiceException( "更新重新选中的优惠券信息失败");
			}
		}
		//结算
		if(couponApplyVo != null){
			PartnerWalletBill partnerWalletBill = new PartnerWalletBill();
			partnerWalletBill.setAccountId(couponApplyVo.getApplierId());
			partnerWalletBill.setWallet(orderVo.getTurnoverAmount()*0.1f);
			partnerWalletBill.setCreateTime(orderVo.getUpdateTime());
			partnerWalletBill.setDescription("订单号："+orderVo.getOrderNo());
			partnerWalletBill.setSourceId(orderVo.getId());
			partnerWalletBill.setSourceType(SourceTable.OWNER_ORDER);
			partnerWalletBill.setType(WalletBillType.SEND_BONUS);
			partnerWalletBill.setAgentUserId(orderVo.getByAgentUserId());
			result = this.partnerAccountService.updateWallet(partnerWalletBill);
			if(!result.isSuccess()){
				result.setError(ResultCode.CODE_STATE_4006, "计算合伙人分红操作失败");
				throw new ServiceException("计算合伙人分红操作失败");
			}
		}
		//费用信息处理,初始化处理
		List<CostVo> costAddList = new ArrayList<CostVo>();
		Map<Integer, String> costTypeMap = OwnerGeneralConstant.CostType.CostTypeMap;
		for (Map.Entry<Integer, String> entry : costTypeMap.entrySet()) {  
		   // System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
			if(costMap.containsKey(entry.getKey())){
				CostVo cost = costMap.get(entry.getKey());
				cost.setSourceId(orderVo.getId());
				cost.setSourceType(SourceTable.OWNER_ORDER);
				if(StringUtil.isEmpty(cost.getCostName())){cost.setCostName(entry.getValue());}
				costAddList.add(costMap.get(entry.getKey()));
				continue;
			}
			CostVo cost = new CostVo();
			cost.setCostType(entry.getKey());
			cost.setCostName(entry.getValue());
			if( entry.getKey() == CostType.DISCOUNT){
				cost.setCostValue(1.0f);
			}else{
				cost.setCostValue(0.0f);
			}
			if(entry.getKey() == CostType.CASH_COUPON){
				cost.setCostValue(couponValueSum);
			}
			cost.setDesc("");
			cost.setRemark("");
			costAddList.add(cost);
		}  
		flag = this.costService.batchAdd(costAddList);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "保存费用失败");
			throw new ServiceException("保存费用失败");
		}
		//保存附件信息
		List<AttachmentVo> attachmentVoList = orderVo.getAttachmentVoList();  //本次附件列表
		if(attachmentVoList != null && attachmentVoList.size() > 0){
			for(AttachmentVo attach : attachmentVoList){
				attach.setSourceId(orderVo.getId());
				attach.setSourceType(SourceTable.OWNER_ORDER);
				attach.setType(AttachmentType.AGENT);
				attach.setCreateTime(curDate);
				if(!orderVo.getIsOnlySave()){
					attach.setIsLock(true);
				}
			}
			flag = this.attachmentService.batchAdd(attachmentVoList);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "更新附件信息失败，请稍后再试");
				throw new ServiceException("更新附件信息失败");
			}
		}
		//保存客户信息
		if(!orderVo.getIsOnlySave()){
			result = this.saveOwnerInfo(orderVo);
			if(!result.isSuccess()){
				throw new ServiceException("保存客户信息失败");
			}
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功",orderVo);
		return result;
	}

	/**
	 * 经销商修改订单
	 */
	@Override
	@Transactional
	public Result editOrder(OrderVo orderVo) {
		Result result = new Result();
		Boolean flag = false;
		List<CouponVo> couponList = null;
		//信息校验
		if(orderVo.getId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "订单id不能为空");
			return result;
		}
		OrderVo old = this.getById(orderVo.getId());
		if(old == null){
			result.setError(ResultCode.CODE_STATE_4006, "根据订单id获取详情失败");
			return result;
		}
		if(OrderFlowState.WAIT_AGENT_CONFRIM != old.getFlowState().intValue()){
			result.setError(ResultCode.CODE_STATE_4006, "当前订单状态不允许该操作");
			return result;
		}
		if(StringUtil.isNotEmpty(orderVo.getCouponIds())){
			CouponSearch couponSearch = new CouponSearch();
			couponSearch.setIds(orderVo.getCouponIds().split(","));
			couponList = this.couponService.getList(couponSearch);//需要更新的优惠券列表
		}
		orderVo.setCouponList(couponList);
		if(!orderVo.getIsOnlySave()){//如果确认订单
			if(!orderVo.getIsOnlySave()){//如果确认订单
				result = this.check(orderVo);
				if(!result.isSuccess()) return result;
			}
		}
		//保存订单信息
		CouponApplyVo couponApplyVo = null;    //优惠券申请信息，如果不为null，表示需要计算分红
		Date curDate = new Date();
		
		if(!orderVo.getIsOnlySave()){//如果确认订单
			CouponApplySearch couponApplySearch = new CouponApplySearch();
			couponApplySearch.setOwnerPhone(orderVo.getMobilePhone());
			couponApplySearch.setIsBind(true);
			List<CouponApplyVo> couponApplyList = this.couponApplyService.getList(couponApplySearch);
			if(couponApplyList != null && couponApplyList.size() > 0){
				couponApplyVo = couponApplyList.get(0);
				orderVo.setPartnerId(couponApplyVo.getApplierId());//将该订单归属到合伙人
			}
			orderVo.setFlowState(OrderFlowState.ORG_CONFIRM);
			orderVo.setOrderTime(curDate);
		}
		orderVo.setUpdateTime(curDate);
		flag = this.edit(orderVo);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "修改订单信息失败");
			throw new ServiceException("修改订单信息失败");
		}
		//保存优惠券信息
		//如果已经有选中的现金券，先更新为未使用
		CouponSearch search = new CouponSearch();
		search.setOrderId(orderVo.getId());
		search.setOrderCode(orderVo.getOrderNo());
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
		if(couponList != null && couponList.size() >0){
			for(CouponVo couponVo:couponList){
				couponVo.setOrderCode(old.getOrderNo());
				couponVo.setOrderId(old.getId());
				couponVo.setUpdateTime(curDate);
				couponVo.setState(CouponState.HAD_USE);
				couponValueSum += couponVo.getCouponValue();
			}
			flag = this.couponService.batchUpdate(couponList);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "更新重新选中的优惠券信息失败");
				throw new ServiceException( "更新重新选中的优惠券信息失败");
			}
		}
		//结算
		if(couponApplyVo != null){
			PartnerWalletBill partnerWalletBill = new PartnerWalletBill();
			partnerWalletBill.setAccountId(couponApplyVo.getApplierId());
			partnerWalletBill.setWallet(orderVo.getTurnoverAmount()*0.1f);
			partnerWalletBill.setCreateTime(orderVo.getUpdateTime());
			partnerWalletBill.setDescription("订单号："+old.getOrderNo());
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
		//保存费用信息
		if(orderVo.getCostVoList() != null && orderVo.getCostVoList().size() > 0){
			for(CostVo costVo:orderVo.getCostVoList()){
				if(costVo.getCostType() != null && CostType.CASH_COUPON == costVo.getCostType().intValue()){
					costVo.setCostValue(couponValueSum);
				}
			}
			flag = this.costService.batchUpdate(orderVo.getCostVoList());
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "保存费用信息失败");
				throw new ServiceException("保存费用信息失败");
			}
		}
		//保存附件信息
		if(1 == old.getSourceType().intValue() && old.getAppointId() != null && 0 != old.getAppointId().intValue()){
			result = this.saveAttach(orderVo.getAttachmentVoList(), orderVo.getIsOnlySave(), curDate, AttachmentType.AGENT, SourceTable.OWNER_APPOINT, old.getAppointId());
		}else{
			result = this.saveAttach(orderVo.getAttachmentVoList(), orderVo.getIsOnlySave(), curDate, AttachmentType.AGENT, SourceTable.OWNER_ORDER, old.getId());
		}
		if(!result.isSuccess()){
			result.setError(ResultCode.CODE_STATE_4006, "保存附件信息失败");
			throw new ServiceException("保存附件信息失败");
		}
		//保存客户信息
		if(!orderVo.getIsOnlySave()){
			result = this.saveOwnerInfo(orderVo);
			if(!result.isSuccess()){
				throw new ServiceException("保存客户信息失败");
			}
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功",orderVo);
		return result;
	}

	/**
	 * 校验
	 * @param orderVo
	 * @return
	 */
	public Result check(OrderVo orderVo){
		Result result = new Result();
		List<CouponVo> couponList = orderVo.getCouponList();
		Map<Integer,CostVo> costMap = this.getCostMap(orderVo.getCostVoList());
		if(StringUtil.isEmpty(orderVo.getName())){
			result.setError(ResultCode.CODE_STATE_4006, "联系人姓名不能为空");
			return result;
		}
		if(StringUtil.isEmpty(orderVo.getMobilePhone())){
			result.setError(ResultCode.CODE_STATE_4006, "联系人手机不能为空");
			return result;
		}
		if(!VerificationUtils.isValid(orderVo.getMobilePhone(), VerificationUtils.MOBILE)){
			result.setError(ResultCode.CODE_STATE_4006, "请输入有效的预约人手机号码");
			return result;
		}
		if(StringUtil.isEmpty(orderVo.getDecorateProject()) || StringUtil.isEmpty(orderVo.getDecorateProjectTypes())){
			result.setError(ResultCode.CODE_STATE_4006, "装修项目不能为空");
			return result;
		}
		if(StringUtil.isEmpty(orderVo.getProvince()) || orderVo.getProvinceId() == null
				|| StringUtil.isEmpty(orderVo.getCity()) || orderVo.getCityId() == null
					|| StringUtil.isEmpty(orderVo.getArea()) || orderVo.getAreaId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "所在地区不能为空");
			return result;
		}
		if(StringUtil.isEmpty(orderVo.getAddress())){
			result.setError(ResultCode.CODE_STATE_4006, "详细地址不能为空");
			return result;
		}
		//附件信息校验
		if(orderVo.getAttachmentVoList() == null || orderVo.getAttachmentVoList().isEmpty()){
			result.setError(ResultCode.CODE_STATE_4006, "请上传相关附件");
			return result;
		}
		//费用校验
		if(orderVo.getCostVoList() == null || orderVo.getCostVoList().isEmpty()){
			result.setError(ResultCode.CODE_STATE_4006, "请填写相关费用信息");
			return result;
		}
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
		//现金券校验
		Float productCostValue = productCost.getCostValue();
		Float pfreightCostValue = freightCost.getCostValue();
		Float installationFeeCostValue = installationFeeCost.getCostValue();
		Float agentDiscount = costMap.get(CostType.DISCOUNT).getCostValue();//折扣
		Float activityCouponAmount = costMap.get(CostType.ACTIVITY_COUPON).getCostValue();//优惠券金额
		Float couponValueSum = 0.0f;  //现金券减免总金额
		Float finalSalesAmount = (productCostValue+pfreightCostValue+installationFeeCostValue)*agentDiscount/100-activityCouponAmount;
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
				couponVo.setOrderCode(orderVo.getOrderNo());
				couponVo.setOrderId(orderVo.getId());
				couponVo.setUpdateTime(new Date());
				couponVo.setState(CouponState.HAD_USE);
				couponValueSum += couponVo.getCouponValue();
			}
			//满5000用500，满1万用1000
			Float maxCouponValue = finalSalesAmount*0.1f;
			if(couponValueSum > maxCouponValue.intValue()){
				result.setError(ResultCode.CODE_STATE_4006, "可以使用的优惠券总金额不能超过"+maxCouponValue+"元，请重新选择优惠券");
				return result;
			}
			orderVo.setFlowState(OrderFlowState.AGENT_CONFIRM);
		}
		orderVo.setTurnoverAmount(finalSalesAmount-couponValueSum);
		orderVo.setSalesAmount(productCostValue+pfreightCostValue+installationFeeCostValue);
		result.setOK(ResultCode.CODE_STATE_200, "信息有效");
		return result;
	}
	
	/**
	 * 保存客户信息
	 * @param appointmentVo
	 * @return
	 */
	@Transactional
	public Result saveOwnerInfo(OrderVo orderVo){
		Result result = new Result();
		OwnerInfoSearch ownerInfoSearch = new OwnerInfoSearch();
		ownerInfoSearch.setCreaterId(orderVo.getByAgentUserId());
		ownerInfoSearch.setMobilePhoneLike(orderVo.getMobilePhone());
		List<OwnerInfoVo> ownerInfoList = this.ownerInfoService.getList(ownerInfoSearch);
		if(ownerInfoList == null || ownerInfoList.isEmpty()){
			OwnerInfoVo ownerInfoVo = new OwnerInfoVo();
			ownerInfoVo.setName(orderVo.getName());
			ownerInfoVo.setMobilePhone(orderVo.getMobilePhone());
			ownerInfoVo.setProvince(orderVo.getProvince());
			ownerInfoVo.setProvinceId(orderVo.getProvinceId());
			ownerInfoVo.setCity(orderVo.getCity());
			ownerInfoVo.setCityId(orderVo.getCityId());
			ownerInfoVo.setArea(orderVo.getArea());
			ownerInfoVo.setAreaId(orderVo.getAreaId());
			ownerInfoVo.setAddress(orderVo.getAddress());
			ownerInfoVo.setSourceType(1);//预约下单
			ownerInfoVo.setCreaterId(orderVo.getByAgentUserId());
			Boolean flag = this.ownerInfoService.add(ownerInfoVo);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "保存客户信息失败");
				throw new ServiceException("保存客户信息失败");
			}
		}
		result.setOK(ResultCode.CODE_STATE_200, "保存客户信息成功");
		return result;
	}
	
	/**
	 * 保存附件信息
	 * @param appointmentVo
	 * @return
	 */
	@Transactional
	public Result saveAttach(List<AttachmentVo> attachmentVoList,boolean isOnlySave, Date curDate, Integer attachType, Integer sourceType, Integer sourceId){
		Result result = new Result();
		Boolean flag = false;
//		Date curDate = orderVo.getUpdateTime();
//		List<AttachmentVo> attachmentVoList = orderVo.getAttachmentVoList();  //本次附件列表
		AttachmentSearch attachmentSearch = new AttachmentSearch();
		attachmentSearch.setSourceId(sourceId);
		attachmentSearch.setSourceType(sourceType);
		attachmentSearch.setIsEffect(true);
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
						attach.setSourceId(sourceId);
						attach.setType(attachType);
						attach.setSourceType(SourceTable.OWNER_ORDER);
						attach.setCreateTime(curDate);
						if(!isOnlySave){
							attach.setIsLock(true);
						}
						addList.add(attach);
					}
				}
				if(updateList != null && updateList.size() > 0){
					for(AttachmentVo update:existAttachmentVoList){
						if(!isOnlySave){
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
					attach.setSourceId(sourceId);
					attach.setSourceType(sourceType);
					attach.setType(attachType);
					attach.setCreateTime(curDate);
					if(!isOnlySave){
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
	 * 总部确认订单
	 * @param orderVo
	 * @return
	 */
	@Override
	@Transactional
	public Result confirmOrder(OrderVo orderVo){
		Result result = new Result();
		if(orderVo.getId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "订单id不能为空");
			return result;
		}
		OrderVo old = this.getById(orderVo.getId());
		if(old == null){
			result.setError(ResultCode.CODE_STATE_4006, "根据订单id获取详情失败");
			return result;
		}
		if(OrderFlowState.AGENT_CONFIRM != old.getState().intValue()){
			result.setError(ResultCode.CODE_STATE_4006, "当前订单状态不允许该操作");
			return result;
		}
		if(!orderVo.getIsOnlySave()){
			//附件信息校验
			if(orderVo.getAttachmentVoList() == null || orderVo.getAttachmentVoList().isEmpty()){
				result.setError(ResultCode.CODE_STATE_4006, "请上传相关附件");
				return result;
			}
			//费用校验
			if(orderVo.getCostVoList() == null || orderVo.getCostVoList().isEmpty()){
				result.setError(ResultCode.CODE_STATE_4006, "请填写相关费用信息");
				return result;
			}
			orderVo.setFlowState(OrderFlowState.ORG_CONFIRM);
			
		}
		Date curDate = new Date();
		orderVo.setUpdateTime(curDate);
		Boolean flag = this.edit(orderVo);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "修改订单信息失败");
			throw new ServiceException("修改订单信息失败");
		}
		//更新预约状态
		if(!orderVo.getIsOnlySave() && old.getAppointId() != null){
			AppointmentVo appointmentVo = new AppointmentVo();
			appointmentVo.setId(old.getAppointId());
			appointmentVo.setState(AppointState.AYLSON_CONFIRM);
			flag = this.appointmentService.edit(appointmentVo);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "更新预约信息失败");
				throw new ServiceException("更新预约信息失败");
			}
			//保存节点信息
			FlowNodeVo flowNodeVo = new FlowNodeVo();
			flowNodeVo.setCreater(orderVo.getDealer());
			flowNodeVo.setCreateId(orderVo.getDealerId());
			flowNodeVo.setCreateTime(curDate);
			flowNodeVo.setState(AppointState.AYLSON_CONFIRM);
			flowNodeVo.setNodeName(AppointState.AppointStateMap.get(AppointState.AYLSON_CONFIRM));
			flowNodeVo.setRemark("总部确认订单，流程完结");
			flowNodeVo.setSourceId(old.getAppointId());
			flowNodeVo.setSourceType(SourceTable.OWNER_APPOINT);
			flowNodeVo.setType(FlowNodeType.APPOINT);
			flag = this.flowNodeService.add(flowNodeVo);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "保存预约流程节点失败，请稍后再试");
				throw new ServiceException("保存预约流程节点失败");
			}
		}
		//保存费用信息
		if(orderVo.getCostVoList() != null && orderVo.getCostVoList().size() > 0){
			flag = this.costService.batchUpdate(orderVo.getCostVoList());
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "保存费用信息失败");
				throw new ServiceException("保存费用信息失败");
			}
		}
		//保存附件信息
		//result = this.saveAttach(orderVo, AttachmentType.ORG);
		if(1 == old.getSourceType().intValue() && old.getAppointId() != null && 0 != old.getAppointId().intValue()){
			result = this.saveAttach(orderVo.getAttachmentVoList(), orderVo.getIsOnlySave(), curDate, AttachmentType.ORG, SourceTable.OWNER_APPOINT, old.getAppointId());
		}else{
			result = this.saveAttach(orderVo.getAttachmentVoList(), orderVo.getIsOnlySave(), curDate, AttachmentType.ORG, SourceTable.OWNER_ORDER, old.getId());
		}
		if(!result.isSuccess()){
			result.setError(ResultCode.CODE_STATE_4006, "保存附件信息失败");
			throw new ServiceException("保存附件信息失败");
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功",orderVo);
		return result;
	}
	
	/**
	 * 经销商提交下一步：待总部确认
	 */
	@Override
	@Transactional
	public Result next(OrderVo orderVo) {
		Result result = new Result();
		//查询优惠券信息
		CouponSearch couponSearch= new CouponSearch();
		couponSearch.setOrderId(orderVo.getId());
		couponSearch.setOrderCode(orderVo.getOrderNo());
		List<CouponVo> couponList = this.couponService.getList(couponSearch);
		orderVo.setCouponList(couponList);
		//查询费用信息
		CostSearch costSearch = new CostSearch();
		costSearch.setSourceId(orderVo.getId());
		costSearch.setSourceType(SourceTable.OWNER_ORDER);
		List<CostVo> costList = this.costService.getList(costSearch);
		orderVo.setCostVoList(costList);
		//查询附件信息
		AttachmentSearch attachmentSearch = new AttachmentSearch();
		attachmentSearch.setSourceId(orderVo.getId());
		attachmentSearch.setSourceType(SourceTable.OWNER_ORDER);
		attachmentSearch.setType(AttachmentType.AGENT);//附件类型
		List<AttachmentVo> attachmentVoList = this.attachmentService.getList(attachmentSearch);
		orderVo.setAttachmentVoList(attachmentVoList);
		result = this.check(orderVo);
		if(!result.isSuccess())  return result;
		//保存订单信息
		Boolean flag = false;
		CouponApplyVo couponApplyVo = null;    //优惠券申请信息，如果不为null，表示需要计算分红
		Date curDate = new Date();
		if(!orderVo.getIsOnlySave()){//如果确认订单
			CouponApplySearch couponApplySearch = new CouponApplySearch();
			couponApplySearch.setOwnerPhone(orderVo.getMobilePhone());
			couponApplySearch.setIsBind(true);
			List<CouponApplyVo> couponApplyList = this.couponApplyService.getList(couponApplySearch);
			if(couponApplyList != null && couponApplyList.size() > 0){
				couponApplyVo = couponApplyList.get(0);
				orderVo.setPartnerId(couponApplyVo.getApplierId());//将该订单归属到合伙人
			}
			orderVo.setFlowState(OrderFlowState.AGENT_CONFIRM);
			orderVo.setOrderTime(curDate);
		}
		orderVo.setUpdateTime(curDate);
		flag = this.edit(orderVo);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "修改订单信息失败");
			throw new ServiceException("修改订单信息失败");
		}
		//结算
		if(couponApplyVo != null){
			PartnerWalletBill partnerWalletBill = new PartnerWalletBill();
			partnerWalletBill.setAccountId(couponApplyVo.getApplierId());
			partnerWalletBill.setWallet(orderVo.getTurnoverAmount()*0.1f);
			partnerWalletBill.setCreateTime(orderVo.getUpdateTime());
			partnerWalletBill.setDescription("订单号："+orderVo.getOrderNo());
			partnerWalletBill.setSourceId(orderVo.getId());
			partnerWalletBill.setSourceType(1);
			partnerWalletBill.setType(WalletBillType.SEND_BONUS);
			partnerWalletBill.setAgentUserId(orderVo.getByAgentUserId());
			result = this.partnerAccountService.updateWallet(partnerWalletBill);
			if(!result.isSuccess()){
				result.setError(ResultCode.CODE_STATE_4006, "计算合伙人分红操作失败");
				throw new ServiceException("计算合伙人分红操作失败");
			}
		}
		//保存客户信息
		if(!orderVo.getIsOnlySave()){
			result = this.saveOwnerInfo(orderVo);
			if(!result.isSuccess()){
				throw new ServiceException("保存客户信息失败");
			}
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功");
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
	 * 修改订单状态
	 */
	@Override
	@Transactional
	public Result changeState(OrderVo orderVo) {
		Result result = new Result();
		if(orderVo.getId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
			return result;
		}
		if(orderVo.getState() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取状态失败！");
			return result;
		}
		OrderVo old = this.getById(orderVo.getId());
		if(old == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取信息失败，请稍后再试或者联系管理员！");
			return result;
		}
		if(OrderFlowState.ORG_CONFIRM != old.getFlowState().intValue()){
			result.setError(ResultCode.CODE_STATE_4006, "当前状态不允许操作！");
			return result;
		}
		if(old.getState().intValue() > orderVo.getState().intValue()){
			result.setError(ResultCode.CODE_STATE_4006, "当前状态不允许操作！");
			return result;
		}
		orderVo.setUpdateTime(new Date());
		Boolean flag = this.edit(orderVo);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "修改订单信息失败");
			return result;
		}
		//添加订单进度记录
		OrderScheduleVo orderScheduleVo =  new OrderScheduleVo();
		orderScheduleVo.setOrderId(orderVo.getId());
		orderScheduleVo.setCreateTime(new Date());
		orderScheduleVo.setRemark(orderVo.getScheduleRemark());
		orderScheduleVo.setState(orderVo.getState());
		flag = this.orderScheduleService.add(orderScheduleVo);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "添加订单进度失败");
			throw new ServiceException("添加订单进度失败");
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		return result;
	}

	@Override
	public Result getOrderInfo(Integer orderId) {
		Result result = new Result();
		Map<String, Object> data = new HashMap<String, Object>();
		if(orderId == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取订单信息主键失败！");
			return result;
		}
		OrderVo orderVo = this.getById(orderId);
		if(orderVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取订单信息失败！");
			return result;
		}
		//订单信息
		if(orderVo.getSourceType() == null){
			result.setError(ResultCode.CODE_STATE_4006, "订单来源未知，无法获取更多详情！");
			return result;
		}
		AppointmentVo appointmentVo = null;        //预约信息
		if(1 == orderVo.getSourceType().intValue() && orderVo.getAppointId() != null && 0 != orderVo.getAppointId().intValue()){
			result = this.appointmentService.getAppointInfo(orderVo.getAppointId());
			return result;
		}
		data.put("orderVo", orderVo);//订单信息
		data.put("appointmentVo", appointmentVo);//预约信息
		//预约附件详情
		AttachmentSearch agentAttachSearch = new AttachmentSearch();
		agentAttachSearch.setSourceId(orderId);
		agentAttachSearch.setSourceType(SourceTable.OWNER_ORDER);
		agentAttachSearch.setType(AttachmentType.AGENT);
		agentAttachSearch.setIsEffect(true);
		List<AttachmentVo> agentAttachList = this.attachmentService.getList(agentAttachSearch);
		//Map<String, List<AttachmentVo>> agentAttachGroup = this.attachmentService.getAttachmentGroup(agentAttachList);
		data.put("agentAttach", agentAttachList);//经销商附件信息
		AttachmentSearch orgAttachSearch = new AttachmentSearch();
		orgAttachSearch.setSourceId(orderId);
		orgAttachSearch.setSourceType(SourceTable.OWNER_ORDER);
		orgAttachSearch.setType(AttachmentType.ORG);
		orgAttachSearch.setIsEffect(true);
		List<AttachmentVo> orgAttachList = this.attachmentService.getList(orgAttachSearch);
		//Map<String, List<AttachmentVo>> orgAttachGroup = this.attachmentService.getAttachmentGroup(orgAttachList);
		data.put("orgAttach", orgAttachList);//预约-总部附件信息
		//预约费用信息
		CostSearch costSearch = new CostSearch();
		costSearch.setSourceId(orderId);
		costSearch.setSourceType(SourceTable.OWNER_ORDER);
		List<CostVo> costList = this.costService.getList(costSearch);
		data.put("costList", costList);//费用信息
		//现金券信息
		CouponSearch couponSearch = new CouponSearch();
		couponSearch.setOwnerPhone(orderVo.getMobilePhone());
		couponSearch.setState(0);//未使用
		List<CouponVo> couponList = this.couponService.getList(couponSearch);
		CouponSearch couponSearch2 = new CouponSearch();
		couponSearch2.setOwnerPhone(orderVo.getMobilePhone());
		couponSearch2.setOrderId(orderVo.getId());
		couponSearch2.setOrderCode(orderVo.getOrderNo());
		List<CouponVo> chosenCouponList = this.couponService.getList(couponSearch2);
		couponList.addAll(chosenCouponList);
		data.put("couponList", couponList);//预约-费用信息
		//意见列表
		data.put("optionList", null);//意见列表
		result.setOK(ResultCode.CODE_STATE_200, "查询成功",data);
		return result;
	}

	
	@Override
	@Transactional
	public Result orgNext(Integer id) {
		Result result = new Result();
		if(id == null){
			result.setError(ResultCode.CODE_STATE_4006, "订单id不能为空");
			return result;
		}
		OrderVo orderVo = this.getById(id);
		if(orderVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "根据订单id获取详情失败");
			return result;
		}
		if(OrderFlowState.AGENT_CONFIRM != orderVo.getState().intValue()){
			result.setError(ResultCode.CODE_STATE_4006, "当前订单状态不允许该操作");
			return result;
		}
		//信息校验
		List<AttachmentVo> attachmentVoList = null;  //附件列表
		List<CostVo> costVoList = null;              //预约消费列表
		//总部附件检验
		//总部报价检验
		AttachmentSearch attachmentSearch = new AttachmentSearch();
		CostSearch costSearch = new CostSearch();
		if(orderVo.getAppointId() != null){//预约
			attachmentSearch.setSourceId(orderVo.getAppointId());
			attachmentSearch.setSourceType(SourceTable.OWNER_APPOINT);
			
			costSearch.setSourceId(orderVo.getAppointId());
			costSearch.setSourceType(SourceTable.OWNER_APPOINT);
		}else{//新增
			attachmentSearch.setSourceId(orderVo.getId());
			attachmentSearch.setSourceType(SourceTable.OWNER_ORDER);
			
			costSearch.setSourceId(orderVo.getId());
			costSearch.setSourceType(SourceTable.OWNER_ORDER);
		}
		attachmentVoList = this.attachmentService.getList(attachmentSearch);
		costVoList = this.costService.getList(costSearch);
		if(attachmentVoList == null || attachmentVoList.size() <= 0 ){
			result.setError(ResultCode.CODE_STATE_4006, "请先上传总部相关附件");
			return result;
		}
		if(costVoList == null || costVoList.size() <= 0 ){
			result.setError(ResultCode.CODE_STATE_4006, "请填写总部费用信息");
			return result;
		}
		Map<Integer,CostVo> costMap = this.getCostMap(costVoList);
		CostVo factory_amount = costMap.get(CostType.FACTORY_AMOUNT);//出厂金额
		if(factory_amount == null || factory_amount.getCostValue() == null){
			result.setError(ResultCode.CODE_STATE_4006, "出厂金额不能为空");
			return result;
		}
		if(factory_amount.getCostValue() < 0.0f){
			result.setError(ResultCode.CODE_STATE_4006, "运输费不能小于0");
			return result;
		}
		CostVo org_discount = costMap.get(CostType.ORG_DISCOUNT);//折扣
		if(org_discount == null || org_discount.getCostValue() == null){
			result.setError(ResultCode.CODE_STATE_4006, "折扣不能为空");
			return result;
		}
		//订单校验
		if(orderVo.getLimitDays() == null){
			result.setError(ResultCode.CODE_STATE_4006, "订单完成天数时长不能为空");
			return result;
		}
		if(orderVo.getLimitDays().intValue() <= 0 ){
			result.setError(ResultCode.CODE_STATE_4006, "订单完成天数时长不能少于等于0天");
			return result;
		}
		if(orderVo.getLevel() == null){
			orderVo.setLevel(1);
		}
		//信息有效，更新订单
		orderVo.setFlowState(OrderFlowState.ORG_CONFIRM);
		Date curDate = new Date();
		orderVo.setUpdateTime(curDate);
		Boolean flag = this.edit(orderVo);
		if(flag){
			//更新预约状态
			if(orderVo.getAppointId() != null){
				AppointmentVo appointmentVo = new AppointmentVo();
				appointmentVo.setId(orderVo.getAppointId());
				appointmentVo.setState(AppointState.AYLSON_CONFIRM);
				flag = this.appointmentService.edit(appointmentVo);
				if(!flag){
					result.setError(ResultCode.CODE_STATE_4006, "更新预约信息失败");
					throw new ServiceException("更新预约信息失败");
				}
				//保存节点信息
				FlowNodeVo flowNodeVo = new FlowNodeVo();
				flowNodeVo.setCreater(orderVo.getDealer());
				flowNodeVo.setCreateId(orderVo.getDealerId());
				flowNodeVo.setCreateTime(curDate);
				flowNodeVo.setState(AppointState.AYLSON_CONFIRM);
				flowNodeVo.setNodeName(AppointState.AppointStateMap.get(AppointState.AYLSON_CONFIRM));
				flowNodeVo.setRemark("总部确认订单，流程完结");
				flowNodeVo.setSourceId(orderVo.getAppointId());
				flowNodeVo.setSourceType(SourceTable.OWNER_APPOINT);
				flowNodeVo.setType(FlowNodeType.APPOINT);
				flag = this.flowNodeService.add(flowNodeVo);
				if(!flag){
					result.setError(ResultCode.CODE_STATE_4006, "保存预约流程节点失败，请稍后再试");
					throw new ServiceException("保存预约流程节点失败");
				}
			}
			result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "操作失败");
		}
		
		return result;
	}

}
