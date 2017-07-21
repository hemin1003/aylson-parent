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
	 * 添加订单
	 * @param order
	 * @return
	 */
	public Result addOrder(OrderVo orderVo, int index);
	
	/**
	 * 修改订单
	 * @param orderVo
	 * @return
	 */
	public Result editOrder(OrderVo orderVo);
	
}
