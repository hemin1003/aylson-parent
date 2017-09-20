package com.aylson.dc.qmtt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.qmtt.dao.AdvListDao;
import com.aylson.dc.qmtt.po.AdvList;
import com.aylson.dc.qmtt.search.AdvListSearch;
import com.aylson.dc.qmtt.service.AdvListService;

@Service
public class AdvListServiceImpl  extends BaseServiceImpl<AdvList, AdvListSearch> implements AdvListService {

	@Autowired
	private AdvListDao advListDao;

	@Override
	protected BaseDao<AdvList, AdvListSearch> getBaseDao() {
		return advListDao;
	}
}
