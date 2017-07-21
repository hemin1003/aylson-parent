package com.aylson.dc.owner.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.owner.dao.QuotationDao;
import com.aylson.dc.owner.po.Quotation;
import com.aylson.dc.owner.search.QuotationSearch;
import com.aylson.dc.owner.vo.QuotationVo;

@Repository
public class QuotationDaoImpl extends BaseDaoImpl<Quotation,QuotationSearch> implements QuotationDao {

	@Override
	public QuotationVo selectByDesignId(Integer designId) {
		return this.sqlSessionTemplate.selectOne(getSqlName("selectByDesignId"), designId);
	}

	
}
