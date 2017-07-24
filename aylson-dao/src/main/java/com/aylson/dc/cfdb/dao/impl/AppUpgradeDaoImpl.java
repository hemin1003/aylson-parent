package com.aylson.dc.cfdb.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.cfdb.dao.AppUpgradeDao;
import com.aylson.dc.cfdb.po.AppUpgrade;
import com.aylson.dc.cfdb.search.AppUpgradeSearch;

@Repository
public class AppUpgradeDaoImpl extends BaseDaoImpl<AppUpgrade, AppUpgradeSearch> implements AppUpgradeDao {

}
