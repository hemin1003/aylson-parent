package com.aylson.dc.partner.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.partner.dao.PartnerAccountDao;
import com.aylson.dc.partner.po.PartnerAccount;
import com.aylson.dc.partner.search.PartnerAccountSearch;

@Repository
public class PartnerAccountDaoImpl extends BaseDaoImpl<PartnerAccount,PartnerAccountSearch> implements PartnerAccountDao {

	@Override
	public List<Float> selectSumResult(Integer accountId) {
		return this.sqlSessionTemplate.selectList(this.getSqlName("selectSumResult"), accountId);
	}

	@Override
	public List<Integer> selectPCSummaryInfo(Integer accountId) {
		return this.sqlSessionTemplate.selectList(this.getSqlName("selectPCSummaryInfo"), accountId);
	}

	
}
