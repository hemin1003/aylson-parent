package com.aylson.dc.mem.dao;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.dc.mem.po.WithdrawalsApply;
import com.aylson.dc.mem.search.WithdrawalsApplySearch;

public interface WithdrawalsApplyDao extends BaseDao<WithdrawalsApply,WithdrawalsApplySearch> {
	
	public Float sumByApplierId(Integer applierId);
	
}
