package com.aylson.dc.owner.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.owner.dao.OrderDao;
import com.aylson.dc.owner.po.Order;
import com.aylson.dc.owner.search.OrderSearch;

@Repository
public class OrderDaoImpl extends BaseDaoImpl<Order,OrderSearch> implements OrderDao {

	
}
