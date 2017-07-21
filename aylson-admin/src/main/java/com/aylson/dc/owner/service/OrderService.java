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
	 * 总部确认订单
	 * @param orderVo
	 * @return
	 */
	public Result confirmOrder(OrderVo orderVo);
	
	/**
	 * 经销商提交下一步：总部确认订单
	 * @param orderVo
	 * @return
	 */
	public Result next(OrderVo orderVo);
	
	/**
	 * 修改订单状态
	 * @param orderVo
	 * @return
	 */
	public Result changeState(OrderVo orderVo);
	
	/**
	 * 根据订单id获取订单详情
	 * @param id
	 * @return
	 */
	public Result getOrderInfo(Integer orderId);
	
	/**
	 * 总部提交下一步：确认订单
	 * @param id
	 * @return
	 */
	public Result orgNext(Integer id);
	
	
}
