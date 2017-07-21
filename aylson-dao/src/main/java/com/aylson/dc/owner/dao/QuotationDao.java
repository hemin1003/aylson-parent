package com.aylson.dc.owner.dao;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.dc.owner.po.Quotation;
import com.aylson.dc.owner.search.QuotationSearch;
import com.aylson.dc.owner.vo.QuotationVo;

public interface QuotationDao extends BaseDao<Quotation,QuotationSearch> {
	
	public QuotationVo selectByDesignId(Integer designId);
	
	
}
