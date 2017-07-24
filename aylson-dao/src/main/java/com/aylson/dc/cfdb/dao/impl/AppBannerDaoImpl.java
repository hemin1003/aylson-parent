package com.aylson.dc.cfdb.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.cfdb.dao.AppBannerDao;
import com.aylson.dc.cfdb.po.AppBanner;
import com.aylson.dc.cfdb.search.AppBannerSearch;

@Repository
public class AppBannerDaoImpl extends BaseDaoImpl<AppBanner, AppBannerSearch> implements AppBannerDao {

}
