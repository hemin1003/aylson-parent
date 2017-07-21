package com.aylson.dc.partner.dao;

import java.util.List;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.dc.partner.po.PartnerAccount;
import com.aylson.dc.partner.search.PartnerAccountSearch;

public interface PartnerAccountDao extends BaseDao<PartnerAccount,PartnerAccountSearch> {
	
	
	public List<Float> selectSumResult(Integer accountId);
	
	public List<Integer> selectPCSummaryInfo(Integer accountId);
	
}
