package com.aylson.dc.cfdb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.cfdb.dao.AppBannerDao;
import com.aylson.dc.cfdb.po.AppBanner;
import com.aylson.dc.cfdb.search.AppBannerSearch;
import com.aylson.dc.cfdb.service.AppBannerService;

@Service
public class AppBannerServiceImpl  extends BaseServiceImpl<AppBanner, AppBannerSearch> implements AppBannerService {

	@Autowired
	private AppBannerDao appBanner;

	@Override
	protected BaseDao<AppBanner, AppBannerSearch> getBaseDao() {
		return appBanner;
	}
}
