package com.aylson.dc.sys.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.sys.dao.ProductDao;
import com.aylson.dc.sys.po.Product;
import com.aylson.dc.sys.search.ProductSearch;

@Repository
public class ProductDaoImpl extends BaseDaoImpl<Product,ProductSearch> implements ProductDao {

	
}
