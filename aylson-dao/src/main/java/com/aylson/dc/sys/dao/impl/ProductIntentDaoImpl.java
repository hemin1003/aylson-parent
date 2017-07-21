package com.aylson.dc.sys.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.sys.dao.ProductIntentDao;
import com.aylson.dc.sys.po.ProductIntent;
import com.aylson.dc.sys.search.ProductIntentSearch;

@Repository
public class ProductIntentDaoImpl extends BaseDaoImpl<ProductIntent,ProductIntentSearch> implements ProductIntentDao {

	@Override
	public void accessCountDW() {
		this.sqlSessionTemplate.update(this.getSqlName("accessCountDW"));
		
	}

	@Override
	public void accessCountHK() {
		this.sqlSessionTemplate.update(this.getSqlName("accessCountHK"));
	}

	
}
