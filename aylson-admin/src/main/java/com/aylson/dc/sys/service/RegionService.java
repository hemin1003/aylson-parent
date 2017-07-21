package com.aylson.dc.sys.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.sys.po.Region;
import com.aylson.dc.sys.search.RegionSearch;

public interface RegionService extends BaseService<Region,RegionSearch> {
	
	/**
	 * 获取位置信息
	 * @param regionSearch
	 * @return
	 */
	public Result getLocation(RegionSearch regionSearch);
}
