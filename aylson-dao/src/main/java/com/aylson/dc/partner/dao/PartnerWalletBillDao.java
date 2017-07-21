package com.aylson.dc.partner.dao;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.dc.partner.po.PartnerWalletBill;
import com.aylson.dc.partner.search.PartnerWalletBillSearch;

public interface PartnerWalletBillDao extends BaseDao<PartnerWalletBill,PartnerWalletBillSearch> {
	
	public Float selectSum(PartnerWalletBillSearch partnerWalletBillSearch);
	
	
}
