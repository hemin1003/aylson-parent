package com.aylson.dc.owner.service;

import java.util.List;

import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.owner.po.DesignDetailDW;
import com.aylson.dc.owner.search.DesignDetailDWSearch;
import com.aylson.dc.owner.vo.DesignDetailDWVo;

public interface DesignDetailDWService extends BaseService<DesignDetailDW,DesignDetailDWSearch> {
	
	/**
	 * 根据设计信息表id获取明细
	 * @param designId
	 * @return
	 */
	public List<DesignDetailDWVo> getByDesignId(Integer designId);

}
