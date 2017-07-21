package com.aylson.dc.owner.service;

import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.owner.po.Quotation;
import com.aylson.dc.owner.search.QuotationSearch;
import com.aylson.dc.owner.vo.QuotationVo;

public interface QuotationService extends BaseService<Quotation,QuotationSearch> {
	
	
	/**
	 * 根据设计信息id和设计信息类型查询
	 * @param designId
	 * @param designType
	 * @return
	 */
	public QuotationVo getQuotationVo(Integer designId, Integer designType);
	

}
