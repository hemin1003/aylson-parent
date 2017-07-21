package com.aylson.dc.owner.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.owner.dao.OrderDao;
import com.aylson.dc.owner.po.Order;
import com.aylson.dc.owner.search.OrderSearch;
import com.aylson.dc.owner.service.OrderService;


@Service
public class OrderServiceImpl extends BaseServiceImpl<Order,OrderSearch> implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Override
	protected BaseDao<Order,OrderSearch> getBaseDao() {
		return orderDao;
	}


}
