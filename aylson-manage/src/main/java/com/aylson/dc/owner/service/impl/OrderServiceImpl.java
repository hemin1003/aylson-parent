package com.aylson.dc.owner.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aylson.core.constants.SysConstant.BillCodePrefix;
import com.aylson.core.exception.ServiceException;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.base.PartnerGeneralConstant.CouponState;
import com.aylson.dc.base.PartnerGeneralConstant.WalletBillType;
import com.aylson.dc.owner.dao.OrderDao;
import com.aylson.dc.owner.po.Order;
import com.aylson.dc.owner.search.OrderSearch;
import com.aylson.dc.owner.service.OrderScheduleService;
import com.aylson.dc.owner.service.OrderService;
import com.aylson.dc.owner.service.QuotationService;
import com.aylson.dc.owner.vo.OrderScheduleVo;
import com.aylson.dc.owner.vo.OrderVo;
import com.aylson.dc.owner.vo.QuotationVo;
import com.aylson.dc.partner.po.PartnerWalletBill;
import com.aylson.dc.partner.search.CouponApplySearch;
import com.aylson.dc.partner.search.CouponSearch;
import com.aylson.dc.partner.service.CouponApplyService;
import com.aylson.dc.partner.service.CouponService;
import com.aylson.dc.partner.service.PartnerAccountService;
import com.aylson.dc.partner.vo.CouponApplyVo;
import com.aylson.dc.partner.vo.CouponVo;
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
	private QuotationService quotationService;
	@Autowired
	private PartnerAccountService partnerAccountService;
	@Autowired
	private CouponService couponService;

	@Override
	protected BaseDao<Order,OrderSearch> getBaseDao() {
		return orderDao;
	}

	@Override
	@Transactional
	public Result addOrder(OrderVo orderVo) {
		Result result = new Result();
		//信息校验
		if(!VerificationUtils.isValid(orderVo.getMobilePhone(), VerificationUtils.MOBILE)){
			result.setError(ResultCode.CODE_STATE_4006, "请输入有效的预约人手机号码");
			return result;
		}
		orderVo.setCreateTime(new Date());
		orderVo.setUpdateTime(new Date());
		orderVo.setSourceType(2);     //后台生产的订单
		orderVo.setOrderNo(BillNumUtils.getBillCode(BillCodePrefix.ORDER,orderVo.getMobilePhone()));
		Boolean flag = this.orderDao.insert(orderVo);
		if(flag){
			OrderScheduleVo orderScheduleVo =  new OrderScheduleVo();
			orderScheduleVo.setOrderId(orderVo.getId());
			orderScheduleVo.setCreateTime(new Date());
			orderScheduleVo.setRemark(orderVo.getRemark());
			flag = this.orderScheduleService.add(orderScheduleVo);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "添加订单进度失败");
				throw new ServiceException("添加订单进度失败");
			}
			result.setOK(ResultCode.CODE_STATE_200, "操作成功",orderVo);
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "添加订单失败");
		}
		return result;
	}

	@Override
	@Transactional
	public Result editOrder(OrderVo orderVo) {
		Result result = new Result();
		orderVo.setUpdateTime(new Date());
		Boolean flag = this.orderDao.updateById(orderVo);
		if(flag){
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
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "修改订单信息失败");
		}
		return result;
	}

	@Override
	public Result bonus(Integer orderId) {
		Result result = new Result();
		//校验信息
		if(orderId == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取订单信息失败");
			return result;
		}
		OrderVo orderVo = this.getById(orderId);
		if(orderVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取订单信息失败");
			return result;
		}
		CouponApplySearch couponApplySearch = new CouponApplySearch();
		couponApplySearch.setOwnerPhone(orderVo.getMobilePhone());
		couponApplySearch.setIsBind(true);
		//信息有效
		List<CouponApplyVo> couponApplyList = this.couponApplyService.getList(couponApplySearch);
		if(couponApplyList != null && couponApplyList.size() > 0){
			QuotationVo quotationVo = this.quotationService.getByDesignId(orderVo.getDesignId());
			CouponApplyVo couponApplyVo = couponApplyList.get(0);
			PartnerWalletBill partnerWalletBill = new PartnerWalletBill();
			partnerWalletBill.setAccountId(couponApplyVo.getApplierId());
			partnerWalletBill.setWallet(quotationVo.getSalesAmount()*0.1f);
			partnerWalletBill.setCreateTime(new Date());
			partnerWalletBill.setDescription("订单号："+orderVo.getOrderNo());
			partnerWalletBill.setSourceId(orderVo.getId());
			partnerWalletBill.setSourceType(1);
			partnerWalletBill.setType(WalletBillType.SEND_BONUS);
			result = this.partnerAccountService.updateWallet(partnerWalletBill);
		}else{
			result.setError(ResultCode.CODE_STATE_200, "不需要发放分红");
		}
		return result;
	}

	@Override
	public Result useCoupon(Integer orderId, String orderCode, String[] couponIds) {
		Result result = new Result();
		CouponSearch couponSearch = new CouponSearch();
		couponSearch.setIds(couponIds);
		List<CouponVo> couponList = this.couponService.getList(couponSearch);
		Date curDate= new Date();
		for(CouponVo couponVo:couponList){
			couponVo.setOrderCode(orderCode);
			couponVo.setOrderId(orderId);
			couponVo.setUpdateTime(curDate);
			couponVo.setState(CouponState.HAD_USE);
		}
		Boolean flag = this.couponService.batchUpdate(couponList);
		if(flag){
			result.setOK(ResultCode.CODE_STATE_200, "更新优惠券信息成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "更新优惠券信息失败");
		}
		return result;
	}

	@Override
	@Transactional
	public Result settle(Integer orderId, String orderCode, String[] couponIds) {
		Result result = new Result();
		//信息校验
		if(orderId == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取订单信息失败");
			return result;
		}
		if(StringUtil.isEmpty(orderCode)){
			result.setError(ResultCode.CODE_STATE_4006, "获取订单号失败");
			return result;
		}
		//信息有效
		result = this.bonus(orderId);//结算分红
		if(result.isSuccess()){
			if(couponIds != null && couponIds.length > 0){
				result = this.useCoupon(orderId, orderCode, couponIds);
			}
		}
		if(result.isSuccess()){
			
		}
		if(!result.isSuccess()){
			throw new ServiceException(result.getMessage().toString());
		}
		return result;
	}

	/**
	 * 结算订货单生成的订单
	 */
	@Override
	@Transactional
	public Result settle(Integer orderId, String couponIds) {
		Result result = new Result();
		Boolean flag = false;
		QuotationVo quotationVo = null;         //报价单信息
		Integer couponValueSum = 0;             //使用的优惠券总金额
		Date curDate= new Date();               //当前时间
		List<CouponVo> couponList = null;       //选择的优惠券列表
		//信息校验
		if(orderId == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取订单信息失败");
			return result;
		}
		OrderVo orderVo = this.getById(orderId);
		if(orderVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取订单信息失败");
			return result;
		}
		if(orderVo != null && orderVo.getDesignId() != null){
			quotationVo = this.quotationService.getByDesignId(orderVo.getDesignId());
		}
		if(quotationVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取报价单信息失败");
			return result;
		}
		if(StringUtil.isNotEmpty(couponIds)){//如果有使用团购券抵扣，检查使用规则
			if(quotationVo.getSalesAmount().intValue() < 5000){//必须大于1万才能使用
				result.setError(ResultCode.CODE_STATE_4006, "订单金额至少5000元以上才能使用优惠券");
				return result;
			}
			CouponSearch couponSearch = new CouponSearch();
			couponSearch.setIds(couponIds.split(","));
			couponList = this.couponService.getList(couponSearch);//需要更新的优惠券列表
			for(CouponVo couponVo:couponList){
				couponVo.setOrderCode(orderVo.getOrderNo());
				couponVo.setOrderId(orderId);
				couponVo.setUpdateTime(curDate);
				couponVo.setState(CouponState.HAD_USE);
				couponValueSum += couponVo.getCouponValue();
			}
			//满5000用500，满1万用1000
			Float maxCouponValue = quotationVo.getSalesAmount()*0.1f;
			if(couponValueSum > maxCouponValue.intValue()){
				result.setError(ResultCode.CODE_STATE_4006, "可以使用的优惠券总金额不能超过"+maxCouponValue+"元");
				return result;
			}
		}
		//信息有效
		//计算分红
		CouponApplySearch couponApplySearch = new CouponApplySearch();
		couponApplySearch.setOwnerPhone(orderVo.getMobilePhone());
		couponApplySearch.setIsBind(true);
		List<CouponApplyVo> couponApplyList = this.couponApplyService.getList(couponApplySearch);
		if(couponApplyList != null && couponApplyList.size() > 0){//找到合伙人申请信息，需要计算分红给合伙人
			//QuotationVo quotationVo = this.quotationService.getByDesignId(orderVo.getDesignId());
			CouponApplyVo couponApplyVo = couponApplyList.get(0);
			PartnerWalletBill partnerWalletBill = new PartnerWalletBill();
			partnerWalletBill.setAccountId(couponApplyVo.getApplierId());
			partnerWalletBill.setWallet((quotationVo.getSalesAmount()-quotationVo.getCouponValue()-couponValueSum)*0.1f);
			partnerWalletBill.setCreateTime(curDate);
			partnerWalletBill.setDescription("订单号："+orderVo.getOrderNo());
			partnerWalletBill.setSourceId(orderVo.getId());
			partnerWalletBill.setSourceType(1);
			partnerWalletBill.setType(WalletBillType.SEND_BONUS);
			result = this.partnerAccountService.updateWallet(partnerWalletBill);
			orderVo.setPartnerId(couponApplyVo.getApplierId());//将该订单归属到合伙人
			if(!result.isSuccess()){
				result.setError(ResultCode.CODE_STATE_4006, "计算合伙人分红操作失败");
				return result;
			}
		}
		//更新选择的优惠券状态
		if(couponList != null){
			flag = this.couponService.batchUpdate(couponList);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "更新优惠券信息失败");
				throw new ServiceException("更新优惠券信息失败");
			}
			//保存报价单优惠减免金额
			QuotationVo quotationVoEdit = new QuotationVo();
			quotationVoEdit.setId(quotationVo.getId());
			quotationVoEdit.setPartnerCouponValue(couponValueSum);
			flag = this.quotationService.edit(quotationVoEdit);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "更新报价单信息失败");
				throw new ServiceException("更新报价单信息失败");
			}
		}
		//更新订单状态
		orderVo.setReductionAcount(quotationVo.getCouponValue()+couponValueSum+0.0f);
		orderVo.setIsSettle(true);
		orderVo.setSettleTime(curDate);
		flag = this.edit(orderVo);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "更新订单信息失败");
			throw new ServiceException("更新订单信息失败");
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		return result;
	}
	
	/**
	 * 结算后台生产的订单
	 * @param orderId
	 * @param couponIds
	 * @return
	 */
	@Override
	@Transactional
	public Result settleOrder(Integer orderId, String couponIds){
		Result result = new Result();
		Boolean flag = false;
		Integer couponValueSum = 0;             //使用的优惠券总金额
		Date curDate= new Date();               //当前时间
		List<CouponVo> couponList = null;       //选择的优惠券列表
		//信息校验
		if(orderId == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取订单信息失败");
			return result;
		}
		OrderVo orderVo = this.getById(orderId);
		if(orderVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取订单信息失败");
			return result;
		}
		if(StringUtil.isNotEmpty(couponIds)){//如果有使用团购券抵扣，检查使用规则
			if(orderVo.getSalesAmount().intValue() < 5000){//必须大于1万才能使用
				result.setError(ResultCode.CODE_STATE_4006, "订单金额至少5000元以上才能使用优惠券");
				return result;
			}
			CouponSearch couponSearch = new CouponSearch();
			couponSearch.setIds(couponIds.split(","));
			couponList = this.couponService.getList(couponSearch);//需要更新的优惠券列表
			for(CouponVo couponVo:couponList){
				couponVo.setOrderCode(orderVo.getOrderNo());
				couponVo.setOrderId(orderId);
				couponVo.setUpdateTime(curDate);
				couponVo.setState(CouponState.HAD_USE);
				couponValueSum += couponVo.getCouponValue();
			}
			//满5000用500，满1万用1000
			Float maxCouponValue = orderVo.getSalesAmount()*0.1f;
			if(couponValueSum > maxCouponValue.intValue()){
				result.setError(ResultCode.CODE_STATE_4006, "可以使用的优惠券总金额不能超过"+maxCouponValue+"元");
				return result;
			}
		}
		//信息有效
		//计算分红
		CouponApplySearch couponApplySearch = new CouponApplySearch();
		couponApplySearch.setOwnerPhone(orderVo.getMobilePhone());
		couponApplySearch.setIsBind(true);
		List<CouponApplyVo> couponApplyList = this.couponApplyService.getList(couponApplySearch);
		if(couponApplyList != null && couponApplyList.size() > 0){//找到合伙人申请信息，需要计算分红给合伙人
			//QuotationVo quotationVo = this.quotationService.getByDesignId(orderVo.getDesignId());
			CouponApplyVo couponApplyVo = couponApplyList.get(0);
			PartnerWalletBill partnerWalletBill = new PartnerWalletBill();
			partnerWalletBill.setAccountId(couponApplyVo.getApplierId());
			partnerWalletBill.setWallet((orderVo.getSalesAmount()-couponValueSum)*0.1f);
			partnerWalletBill.setCreateTime(curDate);
			partnerWalletBill.setDescription("订单号："+orderVo.getOrderNo());
			partnerWalletBill.setSourceId(orderVo.getId());
			partnerWalletBill.setSourceType(1);
			partnerWalletBill.setType(WalletBillType.SEND_BONUS);
			result = this.partnerAccountService.updateWallet(partnerWalletBill);
			orderVo.setPartnerId(couponApplyVo.getApplierId());//将该订单归属到合伙人
			if(!result.isSuccess()){
				result.setError(ResultCode.CODE_STATE_4006, "计算合伙人分红操作失败");
				return result;
			}
		}
		//更新选择的优惠券状态
		if(couponList != null){
			flag = this.couponService.batchUpdate(couponList);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "更新优惠券信息失败");
				throw new ServiceException("更新优惠券信息失败");
			}
		}
		//更新订单状态
		orderVo.setReductionAcount(couponValueSum+0.0f);
		orderVo.setIsSettle(true);
		orderVo.setSettleTime(curDate);
		flag = this.edit(orderVo);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "更新订单信息失败");
			throw new ServiceException("更新订单信息失败");
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		return result;
	}
	
	
	public Result settleOld(Integer orderId, String couponIds) {
		Result result = new Result();
		Boolean flag = false;
		//信息校验
		if(orderId == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取订单信息失败");
			return result;
		}
		OrderVo orderVo = this.getById(orderId);
		if(orderVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取订单信息失败");
			return result;
		}
		//信息有效
		Date curTime = new Date();
		//计算分红
		CouponApplySearch couponApplySearch = new CouponApplySearch();
		couponApplySearch.setOwnerPhone(orderVo.getMobilePhone());
		couponApplySearch.setIsBind(true);
		List<CouponApplyVo> couponApplyList = this.couponApplyService.getList(couponApplySearch);
		if(couponApplyList != null && couponApplyList.size() > 0){
			QuotationVo quotationVo = this.quotationService.getByDesignId(orderVo.getDesignId());
			CouponApplyVo couponApplyVo = couponApplyList.get(0);
			PartnerWalletBill partnerWalletBill = new PartnerWalletBill();
			partnerWalletBill.setAccountId(couponApplyVo.getApplierId());
			partnerWalletBill.setWallet(quotationVo.getSalesAmount()*0.1f);
			partnerWalletBill.setCreateTime(curTime);
			partnerWalletBill.setDescription("订单号："+orderVo.getOrderNo());
			partnerWalletBill.setSourceId(orderVo.getId());
			partnerWalletBill.setSourceType(1);
			partnerWalletBill.setType(WalletBillType.SEND_BONUS);
			result = this.partnerAccountService.updateWallet(partnerWalletBill);
			if(result.isSuccess() && StringUtil.isNotEmpty(couponIds)){
				result = this.useCoupon(orderId, orderVo.getOrderNo(), couponIds.split(","));
			}
			orderVo.setPartnerId(couponApplyVo.getApplierId());//将该订单归属到合伙人
		}
		if(result.isSuccess()){//修改为已结算
			orderVo.setIsSettle(true);
			orderVo.setSettleTime(curTime);
			flag = this.edit(orderVo);
		}
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "更新订单信息失败");
			throw new ServiceException("更新订单信息失败");
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		return result;
	}


}
