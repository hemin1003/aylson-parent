package com.aylson.dc.owner.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.owner.po.Quotation;
import com.aylson.dc.owner.search.QuotationSearch;
import com.aylson.dc.owner.vo.QuotationVo;

public interface QuotationService extends BaseService<Quotation,QuotationSearch> {
	
	/**
	 * 添加订货单
	 * @param quotationVo
	 * @param quotationDetailDWVoListJson
	 * @return
	 */
	public Result addQuotation(QuotationVo quotationVo,String quotationDetailDWVoListJson);
	
	/**
	 * 根据设计信息表id查询报价订货单信息
	 * @param designId
	 * @return
	 */
	public QuotationVo getByDesignId(Integer designId);
	
	/**
	 * 根据设计信息id和设计信息类型查询
	 * @param designId
	 * @param designType
	 * @return
	 */
	public QuotationVo getQuotationVo(Integer designId, Integer designType);
	
	/**
	 * 修改报价订货单信息
	 * @param quotationVo
	 * @param quotationDetailDWVoListJson
	 * @return
	 */
	public Result editQuotation(QuotationVo quotationVo,String quotationDetailDWVoListJson);

}
