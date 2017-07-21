package com.aylson.dc.owner.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.owner.dao.QuotationDao;
import com.aylson.dc.owner.po.Quotation;
import com.aylson.dc.owner.search.QuotationSearch;
import com.aylson.dc.owner.service.QuotationDetailDWService;
import com.aylson.dc.owner.service.QuotationDetailSRService;
import com.aylson.dc.owner.service.QuotationService;
import com.aylson.dc.owner.vo.QuotationDetailDWVo;
import com.aylson.dc.owner.vo.QuotationDetailSRVo;
import com.aylson.dc.owner.vo.QuotationVo;
import com.aylson.utils.DateUtil;


@Service
public class QuotationServiceImpl extends BaseServiceImpl<Quotation,QuotationSearch> implements QuotationService {

	@Autowired
	private QuotationDao quotationDao;
	@Autowired
	private QuotationDetailDWService quotationDetailDWService;
	@Autowired
	private QuotationDetailSRService quotationDetailSRService;

	@Override
	protected BaseDao<Quotation,QuotationSearch> getBaseDao() {
		return quotationDao;
	}
	
	@Override
	public QuotationVo getQuotationVo(Integer designId, Integer designType) {
		QuotationVo quotationVo = null;
		if(designId != null){
			quotationVo  = this.quotationDao.selectByDesignId(designId);
			if(quotationVo != null){
				if(quotationVo.getOrderTime() != null){
					quotationVo.setOrderTimeStr(DateUtil.format(quotationVo.getOrderTime(), true));
				}
				if(quotationVo.getDeliveryTime() != null){
					quotationVo.setDeliveryTimeStr(DateUtil.format(quotationVo.getDeliveryTime(), true));
				}
				if(designType != null){
					if(3 == designType.intValue()){
						List<QuotationDetailSRVo> detailSRVoList = this.quotationDetailSRService.getByQuotationId(quotationVo.getId());
						quotationVo.setDetailSRVoList(detailSRVoList);
					}else{
						List<QuotationDetailDWVo> detailDWVoList = this.quotationDetailDWService.getByQuotationId(quotationVo.getId());
						quotationVo.setDetailDWVoList(detailDWVoList);
					}
				}
			}
		}
		return quotationVo;
	}

	

}
