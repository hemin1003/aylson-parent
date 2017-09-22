package com.aylson.dc.ytt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.ytt.dao.YttReadHisDao;
import com.aylson.dc.ytt.po.YttReadHis;
import com.aylson.dc.ytt.search.YttReadHisSearch;
import com.aylson.dc.ytt.service.YttReadHisService;

@Service
public class YttReadHisServiceImpl extends BaseServiceImpl<YttReadHis, YttReadHisSearch> implements YttReadHisService {

	@Autowired
	private YttReadHisDao readHisDao;

	@Override
	protected BaseDao<YttReadHis, YttReadHisSearch> getBaseDao() {
		return readHisDao;
	}
}
