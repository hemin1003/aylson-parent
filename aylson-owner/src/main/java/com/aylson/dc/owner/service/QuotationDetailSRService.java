package com.aylson.dc.owner.service;

import java.util.List;

import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.owner.po.QuotationDetailSR;
import com.aylson.dc.owner.search.QuotationDetailSRSearch;
import com.aylson.dc.owner.vo.QuotationDetailSRVo;

public interface QuotationDetailSRService extends BaseService<QuotationDetailSR,QuotationDetailSRSearch> {
	
	/**
	 * 根据设计信息表id获取明细
	 * @param quotationId
	 * @return
	 */
	public List<QuotationDetailSRVo> getByQuotationId(Integer quotationId);

}
