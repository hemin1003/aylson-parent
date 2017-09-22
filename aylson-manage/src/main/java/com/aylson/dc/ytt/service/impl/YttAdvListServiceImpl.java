package com.aylson.dc.ytt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.ytt.dao.YttAdvListDao;
import com.aylson.dc.ytt.po.YttAdvList;
import com.aylson.dc.ytt.search.YttAdvListSearch;
import com.aylson.dc.ytt.service.YttAdvListService;

@Service
public class YttAdvListServiceImpl  extends BaseServiceImpl<YttAdvList, YttAdvListSearch> implements YttAdvListService {

	@Autowired
	private YttAdvListDao advListDao;

	@Override
	protected BaseDao<YttAdvList, YttAdvListSearch> getBaseDao() {
		return advListDao;
	}
}
