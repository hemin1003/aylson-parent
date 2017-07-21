package com.aylson.dc.owner.service.impl;


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
import com.aylson.dc.base.OwnerGeneralConstant.CostSourceType;
import com.aylson.dc.base.OwnerGeneralConstant.CostType;
import com.aylson.dc.base.OwnerGeneralConstant.DesignType;
import com.aylson.dc.base.OwnerGeneralConstant.FlowNodeType;
import com.aylson.dc.busi.search.CostSearch;
import com.aylson.dc.busi.search.FlowNodeSearch;
import com.aylson.dc.busi.service.CostService;
import com.aylson.dc.busi.service.FlowNodeService;
import com.aylson.dc.busi.vo.CostVo;
import com.aylson.dc.busi.vo.FlowNodeVo;
import com.aylson.dc.owner.dao.AppointmentDao;
import com.aylson.dc.owner.po.Appointment;
import com.aylson.dc.owner.search.AppointmentSearch;
import com.aylson.dc.owner.search.DesignSearch;
import com.aylson.dc.owner.search.OrderSearch;
import com.aylson.dc.owner.service.AppointmentService;
import com.aylson.dc.owner.service.DesignDetailDWService;
import com.aylson.dc.owner.service.DesignDetailSRService;
import com.aylson.dc.owner.service.DesignService;
import com.aylson.dc.owner.service.OrderService;
import com.aylson.dc.owner.service.QuotationDetailDWService;
import com.aylson.dc.owner.service.QuotationDetailSRService;
import com.aylson.dc.owner.service.QuotationService;
import com.aylson.dc.owner.vo.AppointmentVo;
import com.aylson.dc.owner.vo.DesignDetailDWVo;
import com.aylson.dc.owner.vo.DesignDetailSRVo;
import com.aylson.dc.owner.vo.DesignVo;
import com.aylson.dc.owner.vo.OrderVo;
import com.aylson.dc.owner.vo.QuotationDetailDWVo;
import com.aylson.dc.owner.vo.QuotationDetailSRVo;
import com.aylson.dc.owner.vo.QuotationVo;
import com.aylson.dc.partner.search.CouponSearch;
import com.aylson.dc.partner.service.CouponService;
import com.aylson.dc.partner.vo.CouponVo;
import com.aylson.dc.sys.po.CouponUserRelations;
import com.aylson.dc.sys.search.AttachmentSearch;
import com.aylson.dc.sys.service.AttachmentService;
import com.aylson.dc.sys.service.CouponDetailService;
import com.aylson.dc.sys.service.CouponUserRelationsService;
import com.aylson.dc.sys.vo.AttachmentVo;
import com.aylson.dc.sys.vo.CouponDetailVo;
import com.aylson.utils.BillNumUtils;
import com.aylson.utils.StringUtil;
import com.aylson.utils.VerificationUtils;


@Service
public class AppointmentServiceImpl extends BaseServiceImpl<Appointment,AppointmentSearch> implements AppointmentService {

	@Autowired
	private AppointmentDao appointmentDao;
	@Autowired
	private DesignService designService;
	@Autowired
	private DesignDetailDWService designDetailDWService;
	@Autowired
	private DesignDetailSRService designDetailSRService;
	@Autowired
	private QuotationService quotationService;                      //订货报价单服务
	@Autowired
	private QuotationDetailDWService quotationDetailDWService;
	@Autowired
	private QuotationDetailSRService quotationDetailSRService;
	@Autowired
	private FlowNodeService flowNodeService;
	@Autowired
	private CouponUserRelationsService couponUserRelationsService;   //优惠券活动
	@Autowired
	private CouponDetailService couponDetailService;	             //优惠券活动明细配置
	@Autowired
	private CostService costService;	             //优惠券活动明细配置
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private CouponService couponService;
	@Autowired
	private OrderService orderService;
	
	@Override
	protected BaseDao<Appointment,AppointmentSearch> getBaseDao() {
		return appointmentDao;
	}

	@Override
	public Result getAppointDetail(Integer appointId) {
		Result result = new Result();
		if(appointId == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取不到预约信息");
			return result;
		}
		AppointmentVo appointmentVo = this.appointmentDao.selectById(appointId);//获取预约信息
		if(appointmentVo != null){
			DesignSearch designSearch = new DesignSearch();
			designSearch.setAppointId(appointId);
			List<DesignVo> designVoList = this.designService.getList(designSearch);
			if(designVoList != null && designVoList.size() > 0){
				for(DesignVo designVo : designVoList){//获取设计信息表信息
					QuotationVo quotationVo = this.quotationService.getByDesignId(designVo.getId());//设计信息对应的报价单
					if(DesignType.SUMROOM == designVo.getDesignType().intValue()){//如果是阳光房
						DesignDetailSRVo designDetailSRVo = this.designDetailSRService.getByDesignId(designVo.getId());
						designVo.setDesignDetailSRVo(designDetailSRVo);//阳光房设计信息
						if(quotationVo != null){//阳光房-报价单详情
							List<QuotationDetailSRVo> detailSRVoList = this.quotationDetailSRService.getByQuotationId(quotationVo.getId());
							quotationVo.setDetailSRVoList(detailSRVoList);
						}
					}else{//如果是门、窗
						List<DesignDetailDWVo> designDetailDWList = this.designDetailDWService.getByDesignId(designVo.getId());
						designVo.setDesignDetailDWList(designDetailDWList);//门窗设计信息详情
						if(quotationVo != null){//门窗-报检单详情
							List<QuotationDetailDWVo> detailDWVoList = this.quotationDetailDWService.getByQuotationId(quotationVo.getId());
							quotationVo.setDetailDWVoList(detailDWVoList);
						}
					}
					designVo.setQuotationVo(quotationVo);
				}
			}
			appointmentVo.setDesignVoList(designVoList);
			result.setOK(ResultCode.CODE_STATE_200, "",appointmentVo);
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "根据主键获取不到预约信息");
			return result;
		}
		return result;
	}

	@Override
	@Transactional
	public Result addAppointMent(AppointmentVo appointmentVo) {
		Result result = new Result();
		//信息校验
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
		//信息有效,保存预约单信息，同时添加业务流程节点
		//1 保存预约信息
		Date curDate = new Date();
		appointmentVo.setAppointDate(curDate);
		appointmentVo.setSource("wx_appoint"); //添加来源：wx公众号
		appointmentVo.setBillCode(BillNumUtils.getBillCode(BillCodePrefix.APPOINTMENT,appointmentVo.getMobilePhone()));
		appointmentVo.setDealer(appointmentVo.getName());
		appointmentVo.setDealerId(-1);
		Boolean flag = this.add(appointmentVo);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "保存预约信息失败，请稍后再试");
			return result;
		}
		//2保存业务流程节点
		FlowNodeVo flowNodeVo = new FlowNodeVo();
		flowNodeVo.setCreater(appointmentVo.getName());
		flowNodeVo.setCreateTime(curDate);
		flowNodeVo.setNodeName(AppointState.AppointStateMap.get(AppointState.APPOINT));
		flowNodeVo.setRemark("客户通过微信预约，提交下一步待预约处理");
		flowNodeVo.setSourceId(appointmentVo.getId());
		flowNodeVo.setSourceType(SourceTable.OWNER_APPOINT);
		flowNodeVo.setState(AppointState.APPOINT);
		flowNodeVo.setType(FlowNodeType.APPOINT);
		flag = this.flowNodeService.add(flowNodeVo);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "保存预约流程节点失败，请稍后再试");
			throw new ServiceException("保存预约流程节点失败");
		}
		result.setOK(ResultCode.CODE_STATE_200, "预约成功，请您静候工作人员的电话回访");
		return result;
	}

	@Override
	@Transactional
	public Result appointConfirm(Integer appointId, Boolean isComfirm, String opinion, Integer couponId, Integer couponUserId) {
		Result result = new Result();
		Boolean flag = false;
		CouponDetailVo couponDetailVo = null;            //优惠券明细
		CouponUserRelations couponUserRelations = null;  //优惠券用户关系
		//信息校验
		if(appointId == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取预约主键信息失败，请稍后再试或者联系工作人员");
			return result;
		}
		AppointmentVo appointmentVo = this.getById(appointId);
		if(appointmentVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取预约信息失败，请稍后再试或者联系工作人员");
			return result;
		}
		if(AppointState.WAIT_CONFIRM != appointmentVo.getState().intValue()){
			result.setError(ResultCode.CODE_STATE_4006, "当前状态不允许该操作，请稍后再试或者联系工作人员");
			return result;
		}
		if(isComfirm == null){
			result.setError(ResultCode.CODE_STATE_4006, "请选择一个操作");
			return result;
		}
		if(!isComfirm && StringUtil.isEmpty(opinion)){
			result.setError(ResultCode.CODE_STATE_4006, "不满意请输入原因来帮助我们更好的设计报价");
			return result;
		}
		//信息有效
		Date curDate = new Date();
		FlowNodeVo flowNodeVo = new FlowNodeVo();
		flowNodeVo.setCreater(appointmentVo.getName());
		flowNodeVo.setCreateTime(curDate);
		flowNodeVo.setSourceId(appointmentVo.getId());
		flowNodeVo.setSourceType(SourceTable.OWNER_APPOINT);
		flowNodeVo.setType(FlowNodeType.APPOINT);
		if(isComfirm){//客户满意，待下单
			flowNodeVo.setState(AppointState.WAIT_ORDER);
			flowNodeVo.setNodeName(AppointState.AppointStateMap.get(AppointState.WAIT_ORDER));
			flowNodeVo.setRemark("客户满意，提交下一步：待下单生产");
			
			appointmentVo.setState(AppointState.WAIT_ORDER);
			//如果使用了优惠券，判断该券是否存在
			if(couponId != null && couponUserId != null){
				couponDetailVo = this.couponDetailService.getById(couponId);
				if(couponDetailVo == null){
					result.setError(ResultCode.CODE_STATE_4006, "找不到该优惠券的信息");
					return result;
				}
				couponUserRelations = this.couponUserRelationsService.getById(couponUserId);
				if(couponUserRelations == null){
					result.setError(ResultCode.CODE_STATE_4006, "找不到该优惠券的用户信息");
					return result;
				}
				if(couponUserRelations.getIsUsed().intValue() == 1){
					result.setError(ResultCode.CODE_STATE_4006, "该优惠券已经使用过了");
					return result;
				}
			}
			
		}else{//客户不满意，待重新设计报价
			flowNodeVo.setState(AppointState.WAIT_OFFER_AGAIN);
			flowNodeVo.setNodeName(AppointState.AppointStateMap.get(AppointState.WAIT_OFFER_AGAIN));
			flowNodeVo.setRemark("客户不满意，提交下一步：待重新设计报价；客户意见如下："+opinion);
			appointmentVo.setState(AppointState.WAIT_OFFER_AGAIN);
		}
		flag = this.flowNodeService.add(flowNodeVo);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "保存预约流程节点失败，请稍后再试或者联系工作人员");
			throw new ServiceException("保存预约流程节点失败");
		}
		//当前环节处理人
		appointmentVo.setUpdateTime(curDate);
		appointmentVo.setDealer(appointmentVo.getName());
		appointmentVo.setDealerId(-1);
		flag = this.edit(appointmentVo);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "更新预约信息失败，请稍后再试或者联系工作人员");
			throw new ServiceException("保存预约流程节点失败");
		}
		//如果选择了优惠券
		if(couponDetailVo != null && couponUserRelations != null){
			//更新预约费用信息
			CostSearch costSearch = new CostSearch();
			costSearch.setSourceId(appointId);
			costSearch.setSourceType(CostSourceType.APPOINT);
			costSearch.setCostType(CostType.ACTIVITY_COUPON);
			List<CostVo> costList = this.costService.getList(costSearch);
			if(costList != null && costList.size() > 0){
				CostVo costVo = costList.get(0);
				costVo.setCostValue(couponDetailVo.getCouponValue()+0.0f);
				flag = this.costService.edit(costVo);
				if(!flag){
					result.setError(ResultCode.CODE_STATE_4006, "更新预约费用信息失败");
					throw new ServiceException("更新预约费用信息失败");
				}
				couponUserRelations.setIsUsed(1);	//已使用
				couponUserRelations.setUsedTime(new Date());
				flag = this.couponUserRelationsService.edit(couponUserRelations);
				if(!flag){
					result.setError(ResultCode.CODE_STATE_4006, "更新优惠券状态失败");
					throw new ServiceException("更新优惠券状态失败");
				}
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "获取预约费用信息失败");
				throw new ServiceException("获取预约费用信息失败");
			}
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		return result;
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


}
