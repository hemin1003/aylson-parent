package com.aylson.dc.owner.dao;

import java.util.List;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.dc.owner.po.QuotationDetailSR;
import com.aylson.dc.owner.search.QuotationDetailSRSearch;
import com.aylson.dc.owner.vo.QuotationDetailSRVo;

public interface QuotationDetailSRDao extends BaseDao<QuotationDetailSR,QuotationDetailSRSearch> {
	
	public List<QuotationDetailSRVo> selectByQuotationId(Integer quotationId);
}
