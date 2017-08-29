package com.aylson.dc.qmtt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.qmtt.dao.ReadHisDao;
import com.aylson.dc.qmtt.po.ReadHis;
import com.aylson.dc.qmtt.search.ReadHisSearch;
import com.aylson.dc.qmtt.service.ReadHisService;

@Service
public class ReadHisServiceImpl  extends BaseServiceImpl<ReadHis, ReadHisSearch> implements ReadHisService {

	@Autowired
	private ReadHisDao readHisDao;

	@Override
	protected BaseDao<ReadHis, ReadHisSearch> getBaseDao() {
		return readHisDao;
	}
}
