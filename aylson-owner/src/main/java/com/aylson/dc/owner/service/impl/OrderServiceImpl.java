package com.aylson.dc.owner.service.impl;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aylson.core.constants.SysConstant.BillCodePrefix;
import com.aylson.core.exception.ServiceException;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.owner.dao.OrderDao;
import com.aylson.dc.owner.po.Order;
import com.aylson.dc.owner.search.OrderSearch;
import com.aylson.dc.owner.service.OrderScheduleService;
import com.aylson.dc.owner.service.OrderService;
import com.aylson.dc.owner.vo.OrderScheduleVo;
import com.aylson.dc.owner.vo.OrderVo;
import com.aylson.utils.BillNumUtils;
import com.aylson.utils.VerificationUtils;


@Service
public class OrderServiceImpl extends BaseServiceImpl<Order,OrderSearch> implements OrderService {

	@Autowired
	private OrderDao orderDao;
	@Autowired
	private OrderScheduleService orderScheduleService;

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
			result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "添加订单失败");
		}
		return result;
	}
	
	@Override
	@Transactional
	public Result addOrder(OrderVo orderVo, int index) {
		Result result = new Result();
		//信息校验
		if(!VerificationUtils.isValid(orderVo.getMobilePhone(), VerificationUtils.MOBILE)){
			result.setError(ResultCode.CODE_STATE_4006, "请输入有效的预约人手机号码");
			return result;
		}
		orderVo.setCreateTime(new Date());
		orderVo.setUpdateTime(new Date());
		orderVo.setOrderNo(BillNumUtils.getBillCode(BillCodePrefix.ORDER,orderVo.getMobilePhone(),index+1));
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
			result.setOK(ResultCode.CODE_STATE_200, "操作成功");
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


}
