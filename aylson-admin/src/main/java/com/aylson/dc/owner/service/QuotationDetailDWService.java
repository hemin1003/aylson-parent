package com.aylson.dc.owner.service;

import java.util.List;

import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.owner.po.QuotationDetailDW;
import com.aylson.dc.owner.search.QuotationDetailDWSearch;
import com.aylson.dc.owner.vo.QuotationDetailDWVo;

public interface QuotationDetailDWService extends BaseService<QuotationDetailDW,QuotationDetailDWSearch> {
	
	/**
	 * 根据设计信息表id获取明细
	 * @param quotationId
	 * @return
	 */
	public List<QuotationDetailDWVo> getByQuotationId(Integer quotationId);

}
