package com.aylson.dc.owner.dao;

import java.util.List;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.dc.owner.po.QuotationDetailDW;
import com.aylson.dc.owner.search.QuotationDetailDWSearch;
import com.aylson.dc.owner.vo.QuotationDetailDWVo;

public interface QuotationDetailDWDao extends BaseDao<QuotationDetailDW,QuotationDetailDWSearch> {
	
	public List<QuotationDetailDWVo> selectByQuotationId(Integer quotationId);
	
}
