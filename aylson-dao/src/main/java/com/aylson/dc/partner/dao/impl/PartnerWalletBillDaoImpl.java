package com.aylson.dc.partner.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.partner.dao.PartnerWalletBillDao;
import com.aylson.dc.partner.po.PartnerWalletBill;
import com.aylson.dc.partner.search.PartnerWalletBillSearch;

@Repository
public class PartnerWalletBillDaoImpl extends BaseDaoImpl<PartnerWalletBill,PartnerWalletBillSearch> implements PartnerWalletBillDao {

	@Override
	public Float selectSum(PartnerWalletBillSearch partnerWalletBillSearch) {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectSum"),partnerWalletBillSearch);
	}

	
}
