package com.aylson.dc.partner.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.partner.dao.PartnerWalletBillDao;
import com.aylson.dc.partner.po.PartnerWalletBill;
import com.aylson.dc.partner.search.PartnerWalletBillSearch;
import com.aylson.dc.partner.service.PartnerWalletBillService;


@Service
public class PartnerWalletBillServiceImpl extends BaseServiceImpl<PartnerWalletBill,PartnerWalletBillSearch> implements PartnerWalletBillService {

	@Autowired
	private PartnerWalletBillDao partnerWalletBillDao;

	@Override
	protected BaseDao<PartnerWalletBill,PartnerWalletBillSearch> getBaseDao() {
		return partnerWalletBillDao;
	}

	

}
