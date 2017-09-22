package com.aylson.dc.ytt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.ytt.dao.YttAdvDetailDao;
import com.aylson.dc.ytt.po.YttAdvDetail;
import com.aylson.dc.ytt.search.YttAdvDetailSearch;
import com.aylson.dc.ytt.service.YttAdvDetailService;

@Service
public class YttAdvDetailServiceImpl  extends BaseServiceImpl<YttAdvDetail, YttAdvDetailSearch> implements YttAdvDetailService {

	@Autowired
	private YttAdvDetailDao advDetailDao;

	@Override
	protected BaseDao<YttAdvDetail, YttAdvDetailSearch> getBaseDao() {
		return advDetailDao;
	}
}
