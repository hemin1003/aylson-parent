package com.aylson.dc.sys.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.sys.dao.ProductDao;
import com.aylson.dc.sys.po.Product;
import com.aylson.dc.sys.search.ProductSearch;
import com.aylson.dc.sys.service.ProductService;


@Service
public class ProductServiceImpl extends BaseServiceImpl<Product,ProductSearch> implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Override
	protected BaseDao<Product,ProductSearch> getBaseDao() {
		return productDao;
	}


}
