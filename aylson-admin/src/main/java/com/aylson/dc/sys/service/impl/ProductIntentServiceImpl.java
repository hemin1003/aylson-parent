package com.aylson.dc.sys.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.sys.dao.ProductIntentDao;
import com.aylson.dc.sys.po.ProductIntent;
import com.aylson.dc.sys.search.ProductIntentSearch;
import com.aylson.dc.sys.service.ProductIntentService;


@Service
public class ProductIntentServiceImpl extends BaseServiceImpl<ProductIntent,ProductIntentSearch> implements ProductIntentService {

	@Autowired
	private ProductIntentDao productIntentDao;

	@Override
	protected BaseDao<ProductIntent,ProductIntentSearch> getBaseDao() {
		return productIntentDao;
	}


}
