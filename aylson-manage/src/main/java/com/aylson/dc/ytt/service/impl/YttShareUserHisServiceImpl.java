package com.aylson.dc.ytt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.ytt.dao.YttShareUserHisDao;
import com.aylson.dc.ytt.po.YttShareUserHis;
import com.aylson.dc.ytt.search.YttShareUserHisSearch;
import com.aylson.dc.ytt.service.YttShareUserHisService;

@Service
public class YttShareUserHisServiceImpl extends BaseServiceImpl<YttShareUserHis, YttShareUserHisSearch> implements YttShareUserHisService {

	@Autowired
	private YttShareUserHisDao shareUserHisDao;

	@Override
	protected BaseDao<YttShareUserHis, YttShareUserHisSearch> getBaseDao() {
		return shareUserHisDao;
	}
}
