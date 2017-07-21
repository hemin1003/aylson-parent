package com.aylson.dc.owner.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.owner.dao.BuyerShowPraiseDao;
import com.aylson.dc.owner.po.BuyerShowPraise;
import com.aylson.dc.owner.search.BuyerShowPraiseSearch;
import com.aylson.dc.owner.service.BuyerShowPraiseService;


@Service
public class BuyerShowPraiseServiceImpl extends BaseServiceImpl<BuyerShowPraise,BuyerShowPraiseSearch> implements BuyerShowPraiseService {

	@Autowired
	private BuyerShowPraiseDao buyerShowPraiseDao;

	@Override
	protected BaseDao<BuyerShowPraise,BuyerShowPraiseSearch> getBaseDao() {
		return buyerShowPraiseDao;
	}



}
