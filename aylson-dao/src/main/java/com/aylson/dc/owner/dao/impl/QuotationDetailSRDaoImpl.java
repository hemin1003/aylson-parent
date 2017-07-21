package com.aylson.dc.owner.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.owner.dao.QuotationDetailSRDao;
import com.aylson.dc.owner.po.QuotationDetailSR;
import com.aylson.dc.owner.search.QuotationDetailSRSearch;
import com.aylson.dc.owner.vo.QuotationDetailSRVo;

@Repository
public class QuotationDetailSRDaoImpl extends BaseDaoImpl<QuotationDetailSR,QuotationDetailSRSearch> implements QuotationDetailSRDao {

	@Override
	public List<QuotationDetailSRVo> selectByQuotationId(Integer quotationId) {
		return this.sqlSessionTemplate.selectList(getSqlName("selectByQuotationId"), quotationId);
	}

	
}
