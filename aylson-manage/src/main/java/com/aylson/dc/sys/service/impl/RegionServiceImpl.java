package com.aylson.dc.sys.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.sys.dao.RegionDao;
import com.aylson.dc.sys.po.Region;
import com.aylson.dc.sys.search.RegionSearch;
import com.aylson.dc.sys.service.RegionService;
import com.aylson.dc.sys.vo.RegionVo;


@Service
public class RegionServiceImpl extends BaseServiceImpl<Region,RegionSearch> implements RegionService {

	@Autowired
	private RegionDao regionDao;

	@Override
	protected BaseDao<Region,RegionSearch> getBaseDao() {
		return regionDao;
	}
	
	/**
	 * 获取位置信息
	 * @param regionSearch
	 * @return
	 */
	@Override
	public Result getLocation(RegionSearch regionSearch) {
		Result result = new Result();
		if(regionSearch.getParentId() == null ){
			regionSearch.setParentId(1);//默认中国
		}
		List<RegionVo> list = this.getList(regionSearch);
		result.setOK(ResultCode.CODE_STATE_200, "", list);
		return result;
	}


}
