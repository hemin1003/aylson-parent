package com.aylson.dc.owner.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.owner.dao.OrderScheduleDao;
import com.aylson.dc.owner.po.OrderSchedule;
import com.aylson.dc.owner.search.OrderScheduleSearch;
import com.aylson.dc.owner.service.OrderScheduleService;


@Service
public class OrderScheduleServiceImpl extends BaseServiceImpl<OrderSchedule,OrderScheduleSearch> implements OrderScheduleService {

	@Autowired
	private OrderScheduleDao orderScheduleDao;

	@Override
	protected BaseDao<OrderSchedule,OrderScheduleSearch> getBaseDao() {
		return orderScheduleDao;
	}



}
