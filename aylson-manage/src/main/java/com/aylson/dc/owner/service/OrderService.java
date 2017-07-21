package com.aylson.dc.owner.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.owner.po.Order;
import com.aylson.dc.owner.search.OrderSearch;
import com.aylson.dc.owner.vo.OrderVo;

public interface OrderService extends BaseService<Order,OrderSearch> {
	
	/**
	 * 添加订单
	 * @param order
	 * @return
	 */
	public Result addOrder(OrderVo orderVo);
	
	/**
	 * 修改订单
	 * @param orderVo
	 * @return
	 */
	public Result editOrder(OrderVo orderVo);
	
	/**
	 * 分红
	 * @return
	 */
	public Result bonus(Integer orderId);
	
	/**
	 * 用券
	 * @return
	 */
	public Result useCoupon(Integer orderId, String orderCode, String[] couponIds);
	
	/**
	 * 结算
	 * @return
	 */
	public Result settle(Integer orderId, String orderCode, String[] couponIds );
	
	/**
	 * 结算订货单生产的订单
	 * @return
	 */
	public Result settle(Integer orderId, String couponIds );
	
	/**
	 * 结算后台生产的订单
	 * @param orderId
	 * @param couponIds
	 * @return
	 */
	public Result settleOrder(Integer orderId, String couponIds);
	
	
	
}
