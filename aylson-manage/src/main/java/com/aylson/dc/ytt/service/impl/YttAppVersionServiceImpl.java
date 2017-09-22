package com.aylson.dc.ytt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.ytt.dao.YttAppVersionDao;
import com.aylson.dc.ytt.po.YttAppVersion;
import com.aylson.dc.ytt.search.YttAppVersionSearch;
import com.aylson.dc.ytt.service.YttAppVersionService;

@Service
public class YttAppVersionServiceImpl  extends BaseServiceImpl<YttAppVersion, YttAppVersionSearch> implements YttAppVersionService {

	@Autowired
	private YttAppVersionDao yttAppVersionDao;

	@Override
	protected BaseDao<YttAppVersion, YttAppVersionSearch> getBaseDao() {
		return yttAppVersionDao;
	}
}
