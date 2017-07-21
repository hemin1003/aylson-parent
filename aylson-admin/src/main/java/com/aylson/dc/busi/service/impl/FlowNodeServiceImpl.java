package com.aylson.dc.busi.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.busi.dao.FlowNodeDao;
import com.aylson.dc.busi.po.FlowNode;
import com.aylson.dc.busi.search.FlowNodeSearch;
import com.aylson.dc.busi.service.FlowNodeService;


@Service
public class FlowNodeServiceImpl extends BaseServiceImpl<FlowNode,FlowNodeSearch> implements FlowNodeService {

	@Autowired
	private FlowNodeDao flowNodeDao;

	@Override
	protected BaseDao<FlowNode,FlowNodeSearch> getBaseDao() {
		return flowNodeDao;
	}


}
