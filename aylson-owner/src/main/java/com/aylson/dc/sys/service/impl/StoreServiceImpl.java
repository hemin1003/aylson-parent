package com.aylson.dc.sys.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.sys.dao.StoreDao;
import com.aylson.dc.sys.po.Store;
import com.aylson.dc.sys.search.StoreSearch;
import com.aylson.dc.sys.service.StoreService;


@Service
public class StoreServiceImpl extends BaseServiceImpl<Store,StoreSearch> implements StoreService {

	@Autowired
	private StoreDao storeDao;

	@Override
	protected BaseDao<Store,StoreSearch> getBaseDao() {
		return storeDao;
	}


}
