package com.aylson.dc.owner.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.owner.dao.QuotationDetailDWDao;
import com.aylson.dc.owner.po.QuotationDetailDW;
import com.aylson.dc.owner.search.QuotationDetailDWSearch;
import com.aylson.dc.owner.vo.QuotationDetailDWVo;

@Repository
public class QuotationDetailDWDaoImpl extends BaseDaoImpl<QuotationDetailDW,QuotationDetailDWSearch> implements QuotationDetailDWDao {

	@Override
	public List<QuotationDetailDWVo> selectByQuotationId(Integer quotationId) {
		return this.sqlSessionTemplate.selectList(getSqlName("selectByQuotationId"), quotationId);
	}

	
}
