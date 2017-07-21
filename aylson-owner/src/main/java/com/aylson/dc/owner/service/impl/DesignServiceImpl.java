package com.aylson.dc.owner.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aylson.core.exception.ServiceException;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.base.OwnerGeneralConstant.AppointmentState;
import com.aylson.dc.base.OwnerGeneralConstant.DesignState;
import com.aylson.dc.owner.dao.DesignDao;
import com.aylson.dc.owner.po.Design;
import com.aylson.dc.owner.search.DesignSearch;
import com.aylson.dc.owner.service.AppointmentService;
import com.aylson.dc.owner.service.DesignService;
import com.aylson.dc.owner.service.OrderService;
import com.aylson.dc.owner.service.QuotationService;
import com.aylson.dc.owner.vo.AppointmentVo;
import com.aylson.dc.owner.vo.DesignVo;
import com.aylson.dc.owner.vo.OrderVo;
import com.aylson.dc.owner.vo.QuotationVo;
import com.aylson.dc.sys.po.CouponUserRelations;
import com.aylson.dc.sys.service.CouponDetailService;
import com.aylson.dc.sys.service.CouponUserRelationsService;
import com.aylson.dc.sys.vo.CouponDetailVo;


@Service
public class DesignServiceImpl extends BaseServiceImpl<Design,DesignSearch> implements DesignService {

	@Autowired
	private DesignDao designDao;
	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private CouponUserRelationsService couponUserRelationsService;   //优惠券活动
	@Autowired
	private CouponDetailService couponDetailService;	             //优惠券活动明细配置
	@Autowired
	private QuotationService quotationService;	                     //优惠券活动明细配置

	@Override
	protected BaseDao<Design,DesignSearch> getBaseDao() {
		return designDao;
	}


	@Override
	@Transactional
	public Result confirmQuotation(Integer designId, Boolean isSatisfy) {
		Result result = new Result();
		//参数校验
		if(designId == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取不到设计信息表id");
			return result;
		}
		if(isSatisfy == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取不到确认的结果");
			return result;
		}
		//参数正确
		DesignVo designVo = this.designDao.selectById(designId);
		Boolean flag = false;                                 //更新结果
		Boolean canUpdateAppoint = false;                     //是否需要更新预约信息状态
		Integer appointState = AppointmentState.SATISFY_QUOTE;//预约信息表要更新的状态
		if(isSatisfy){//如果满意报价，将设计信息表的状态修改为：满意报价，同时如果相应预约信息的所有设计表都满意，那么预约信息表的状态也修改为：满意报价
			designVo.setState(DesignState.SATISFY_QUOTE);
		}else{//如果不满意报价,将设计信息表的状态修改为：不满意报价，如果预约信息表的状态还没有修改为：不满意报价，那么也要更新状态
			designVo.setState(DesignState.NOTSATISFY_QUOTE);
		}
		flag = this.designDao.updateById(designVo);//更新设计信息表状态
		if(flag){
			if(!isSatisfy && AppointmentState.NOTSATISFY_QUOTE != designVo.getAppointState()){
				appointState = AppointmentState.NOTSATISFY_QUOTE;
				canUpdateAppoint = true;
			}
			if(isSatisfy){
				DesignSearch designSearch = new DesignSearch();
				List<DesignVo> designVoList = this.designDao.select(designSearch);
				if(designVoList != null && designVoList.size() > 0){
					for(DesignVo temp: designVoList){
						if(DesignState.NOTSATISFY_QUOTE == temp.getState().intValue()){//不满意
							canUpdateAppoint = false;
							break;
						}
					}
				}
			}
			if(canUpdateAppoint){
				AppointmentVo appointmentVo = new AppointmentVo();
				appointmentVo.setId(designVo.getAppointId());
				appointmentVo.setState(appointState);
				flag = this.appointmentService.edit(appointmentVo);
				if(!flag){
					result.setError(ResultCode.CODE_STATE_4006, "更新预约信息状态失败");
					throw new ServiceException("更新预约信息状态失败");
				}
			}
			result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "更新设计信息表状态失败");
			return result;
		}
		return result;
	}


	@Override
	@Transactional
	public Result confirmAllQuotation(Integer appointId, Boolean isSatisfy) {
		Result result = new Result();
		//参数校验
		if(appointId == null){
			result.setError(ResultCode.CODE_STATE_4006, "找不到预约信息，请稍候再试");
			return result;
		}
		if(isSatisfy == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取不到确认的结果");
			return result;
		}
		//参数正确,处理数据
		DesignSearch designSearch = new DesignSearch();
		designSearch.setAppointId(appointId);
		List<DesignVo> designList = this.designDao.select(designSearch);
		AppointmentVo appointmentVo = new AppointmentVo();
		appointmentVo.setId(appointId);
		if(designList != null && designList.size() > 0){
			for(DesignVo designVo:designList){
				if(isSatisfy){//如果满意报价
					designVo.setState(DesignState.CONFIRM_ORDER);
					appointmentVo.setState(AppointmentState.CONFIRM_ORDER);
				}else{//如果不满意报价
					designVo.setState(DesignState.NOTSATISFY_QUOTE);
					appointmentVo.setState(AppointmentState.NOTSATISFY_QUOTE);
				}
			}
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "找不到对应的设计信息");
			return result;
		}
		//处理完毕，更新数据
		Boolean flag = this.appointmentService.edit(appointmentVo);
		if(flag){
			flag = this.designDao.batchUpdate(designList);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "更新报价状态信息失败");
				throw new ServiceException("更新报价状态信息失败");
			}
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "更新预约状态信息失败");
			return result;
		}
		//满意报价，添加订单
		if(isSatisfy){
			AppointmentVo appointVo = this.appointmentService.getById(appointId);//更新预约信息
			for(DesignVo designVo:designList){
				OrderVo orderVo = new OrderVo();
				orderVo.setAppointId(appointId);
				orderVo.setAppointNo(appointVo.getBillCode());
				orderVo.setMobilePhone(appointVo.getMobilePhone());
				orderVo.setName(appointVo.getName());
				orderVo.setProvince(appointVo.getProvince());
				orderVo.setProvinceId(appointVo.getProvinceId());
				orderVo.setCity(appointVo.getCity());
				orderVo.setCityId(appointVo.getCityId());
				orderVo.setArea(appointVo.getArea());
				orderVo.setAreaId(appointVo.getAreaId());
				orderVo.setAddress(appointVo.getAddress());
				orderVo.setDecorateProject(appointVo.getDecorateProject());
				orderVo.setDecorateProjectTypes(appointVo.getDecorateProjectTypes());
				orderVo.setDesignId(designVo.getId());
				orderVo.setDesignNo(designVo.getBillCode());
				orderVo.setDesignType(designVo.getDesignType());
				result = this.orderService.addOrder(orderVo);
				if(!result.isSuccess()){
					throw new ServiceException("添加订单失败");
				}
			}
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		return result;
	}
	
	
	@Override
	@Transactional
	public Result confirmAllQuotation(Integer appointId, Integer couponId, Integer couponUserId, Boolean isSatisfy) {
		Result result = new Result();
		CouponDetailVo couponDetailVo = null;            //优惠券明细
		CouponUserRelations couponUserRelations = null;  //优惠券用户关系
		//参数校验
		if(appointId == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取预约信息失败，请稍候再试");
			return result;
		}
		if(isSatisfy == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取不到确认的结果");
			return result;
		}
		AppointmentVo appointVo = this.appointmentService.getById(appointId);
		if(appointVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取预约信息失败，请稍候再试");
			return result;
		}
		if(appointVo != null && AppointmentState.CONFIRM_ORDER == appointVo.getState().intValue()){
			result.setError(ResultCode.CODE_STATE_4006, "已经下过单了，不能重复下单");
			return result;
		}
		if(isSatisfy){//如果满意并下单的校验
			if((couponId != null && couponUserId == null)
					|| (couponId == null && couponUserId != null)){
				result.setError(ResultCode.CODE_STATE_4006, "优惠券信息不完整");
				return result;
			}
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
		}
		//参数正确,处理数据
		DesignSearch designSearch = new DesignSearch();
		designSearch.setAppointId(appointId);
		List<DesignVo> designList = this.designDao.select(designSearch);
		AppointmentVo appointmentVo = new AppointmentVo();
		appointmentVo.setId(appointId);
		if(designList != null && designList.size() > 0){
			for(DesignVo designVo:designList){
				if(isSatisfy){//如果满意报价
					designVo.setState(DesignState.CONFIRM_ORDER);
					appointmentVo.setState(AppointmentState.CONFIRM_ORDER);
				}else{//如果不满意报价
					designVo.setState(DesignState.NOTSATISFY_QUOTE);
					appointmentVo.setState(AppointmentState.NOTSATISFY_QUOTE);
				}
			}
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "找不到对应的设计信息");
			return result;
		}
		//处理完毕，更新数据
		Boolean flag = this.appointmentService.edit(appointmentVo);
		if(flag){
			flag = this.designDao.batchUpdate(designList);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "更新报价状态信息失败");
				throw new ServiceException("更新报价状态信息失败");
			}
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "更新预约状态信息失败");
			return result;
		}
		//满意报价，添加订单
		if(isSatisfy){
			//AppointmentVo appointVo = this.appointmentService.getById(appointId);
			if(designList != null && designList.size() > 0){
				for(int i=0; i<designList.size(); i++){
					DesignVo designVo = designList.get(i);
					OrderVo orderVo = new OrderVo();
					orderVo.setAppointId(appointId);
					orderVo.setAppointNo(appointVo.getBillCode());
					orderVo.setMobilePhone(appointVo.getMobilePhone());
					orderVo.setName(appointVo.getName());
					orderVo.setProvince(appointVo.getProvince());
					orderVo.setProvinceId(appointVo.getProvinceId());
					orderVo.setCity(appointVo.getCity());
					orderVo.setCityId(appointVo.getCityId());
					orderVo.setArea(appointVo.getArea());
					orderVo.setAreaId(appointVo.getAreaId());
					orderVo.setAddress(appointVo.getAddress());
					orderVo.setDecorateProject(appointVo.getDecorateProject());
					orderVo.setDecorateProjectTypes(appointVo.getDecorateProjectTypes());
					orderVo.setDesignId(designVo.getId());
					orderVo.setDesignNo(designVo.getBillCode());
					orderVo.setDesignType(designVo.getDesignType());
					result = this.orderService.addOrder(orderVo,i);
					if(!result.isSuccess()){
						throw new ServiceException("添加订单失败");
					}
				}
			}
			//如果选择了优惠券
			if(couponDetailVo != null && couponUserRelations != null){
				//更新报价表的设计信息
				QuotationVo firstQuotationVo = this.quotationService.getByDesignId(designList.get(0).getId());
				if(firstQuotationVo != null){
					QuotationVo updateQuotationVo = new QuotationVo();
					updateQuotationVo.setId(firstQuotationVo.getId());
					updateQuotationVo.setCouponValue(couponDetailVo.getCouponValue());//券值
					updateQuotationVo.setCouponId(couponUserRelations.getId());//用户优惠券关系id
					flag = this.quotationService.edit(updateQuotationVo);
					if(!flag){
						result.setError(ResultCode.CODE_STATE_4006, "更新报价单优惠券信息失败");
						throw new ServiceException("更新报价单优惠券信息失败");
					}
					couponUserRelations.setIsUsed(1);	//已使用
					couponUserRelations.setUsedTime(new Date());
					flag = this.couponUserRelationsService.edit(couponUserRelations);
					if(!flag){
						result.setError(ResultCode.CODE_STATE_4006, "更新优惠券状态失败");
						throw new ServiceException("更新优惠券状态失败");
					}
				}else{
					result.setError(ResultCode.CODE_STATE_4006, "获取报价单信息失败");
					throw new ServiceException("获取报价单信息失败");
				}
			}
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		return result;
	}


}
