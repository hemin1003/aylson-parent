package com.aylson.dc.owner.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.owner.dao.QuotationDetailDWDao;
import com.aylson.dc.owner.po.QuotationDetailDW;
import com.aylson.dc.owner.search.QuotationDetailDWSearch;
import com.aylson.dc.owner.service.QuotationDetailDWService;
import com.aylson.dc.owner.vo.QuotationDetailDWVo;


@Service
public class QuotationDetailDWServiceImpl extends BaseServiceImpl<QuotationDetailDW,QuotationDetailDWSearch> implements QuotationDetailDWService {

	@Autowired
	private QuotationDetailDWDao quotationDetailDWDao;

	@Override
	protected BaseDao<QuotationDetailDW,QuotationDetailDWSearch> getBaseDao() {
		return quotationDetailDWDao;
	}

	@Override
	public List<QuotationDetailDWVo> getByQuotationId(Integer quotationId) {
		return this.quotationDetailDWDao.selectByQuotationId(quotationId);
	}

	
}
