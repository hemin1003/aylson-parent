package com.aylson.dc.owner.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.owner.dao.QuotationDetailSRDao;
import com.aylson.dc.owner.po.QuotationDetailSR;
import com.aylson.dc.owner.search.QuotationDetailSRSearch;
import com.aylson.dc.owner.service.QuotationDetailSRService;
import com.aylson.dc.owner.vo.QuotationDetailSRVo;


@Service
public class QuotationDetailSRServiceImpl extends BaseServiceImpl<QuotationDetailSR,QuotationDetailSRSearch> implements QuotationDetailSRService {

	@Autowired
	private QuotationDetailSRDao quotationDetailSRDao;

	@Override
	protected BaseDao<QuotationDetailSR,QuotationDetailSRSearch> getBaseDao() {
		return quotationDetailSRDao;
	}

	@Override
	public List<QuotationDetailSRVo> getByQuotationId(Integer quotationId) {
		return this.quotationDetailSRDao.selectByQuotationId(quotationId);
	}


}
