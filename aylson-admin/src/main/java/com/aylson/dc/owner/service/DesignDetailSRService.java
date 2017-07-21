package com.aylson.dc.owner.service;

import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.owner.po.DesignDetailSR;
import com.aylson.dc.owner.search.DesignDetailSRSearch;
import com.aylson.dc.owner.vo.DesignDetailSRVo;

public interface DesignDetailSRService extends BaseService<DesignDetailSR,DesignDetailSRSearch> {
	
	public DesignDetailSRVo getByDesignId(Integer designId);

}
