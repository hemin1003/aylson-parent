package com.aylson.dc.partner.service;

import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.partner.po.PartnerWalletBill;
import com.aylson.dc.partner.search.PartnerWalletBillSearch;

public interface PartnerWalletBillService extends BaseService<PartnerWalletBill,PartnerWalletBillSearch> {
	
	/**
	 * 汇总明细
	 * @param partnerWalletBillSearch
	 * @return
	 */
	public Float getBillSum(PartnerWalletBillSearch partnerWalletBillSearch);
	
	
}
