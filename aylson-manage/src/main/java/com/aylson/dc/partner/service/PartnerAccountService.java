package com.aylson.dc.partner.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.partner.po.PartnerAccount;
import com.aylson.dc.partner.po.PartnerWalletBill;
import com.aylson.dc.partner.search.PartnerAccountSearch;

public interface PartnerAccountService extends BaseService<PartnerAccount,PartnerAccountSearch> {
	
	/**
	 * 更新合伙人钱包值，并添加相应的流水
	 * @param memWalletDetail
	 * @return
	 */
	public Result updateWallet(PartnerWalletBill partnerWalletBill);
	
	
}
