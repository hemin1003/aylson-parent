package com.aylson.dc.sys.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.sys.dao.RegionDao;
import com.aylson.dc.sys.po.Region;
import com.aylson.dc.sys.search.RegionSearch;

@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region, RegionSearch> implements RegionDao{

}
