package com.aylson.dc.owner.dao;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.dc.owner.po.DesignDetailSR;
import com.aylson.dc.owner.search.DesignDetailSRSearch;
import com.aylson.dc.owner.vo.DesignDetailSRVo;

public interface DesignDetailSRDao extends BaseDao<DesignDetailSR,DesignDetailSRSearch> {
	
	public DesignDetailSRVo selectByDesignId(Integer designId);
	
}
