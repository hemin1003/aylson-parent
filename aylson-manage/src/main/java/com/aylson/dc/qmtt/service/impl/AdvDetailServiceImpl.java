package com.aylson.dc.qmtt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.qmtt.dao.AdvDetailDao;
import com.aylson.dc.qmtt.po.AdvDetail;
import com.aylson.dc.qmtt.search.AdvDetailSearch;
import com.aylson.dc.qmtt.service.AdvDetailService;

@Service
public class AdvDetailServiceImpl  extends BaseServiceImpl<AdvDetail, AdvDetailSearch> implements AdvDetailService {

	@Autowired
	private AdvDetailDao advDetailDao;

	@Override
	protected BaseDao<AdvDetail, AdvDetailSearch> getBaseDao() {
		return advDetailDao;
	}
}
