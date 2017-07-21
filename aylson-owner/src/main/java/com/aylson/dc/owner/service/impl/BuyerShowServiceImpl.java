package com.aylson.dc.owner.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.owner.dao.BuyerShowDao;
import com.aylson.dc.owner.po.BuyerShow;
import com.aylson.dc.owner.search.BuyerShowSearch;
import com.aylson.dc.owner.service.BuyerShowService;


@Service
public class BuyerShowServiceImpl extends BaseServiceImpl<BuyerShow,BuyerShowSearch> implements BuyerShowService {

	@Autowired
	private BuyerShowDao buyerShowDao;

	@Override
	protected BaseDao<BuyerShow,BuyerShowSearch> getBaseDao() {
		return buyerShowDao;
	}



}
