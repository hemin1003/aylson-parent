package com.aylson.dc.qmtt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.qmtt.dao.ShareUserHisDao;
import com.aylson.dc.qmtt.po.ShareUserHis;
import com.aylson.dc.qmtt.search.ShareUserHisSearch;
import com.aylson.dc.qmtt.service.ShareUserHisService;

@Service
public class ShareUserHisServiceImpl extends BaseServiceImpl<ShareUserHis, ShareUserHisSearch> implements ShareUserHisService {

	@Autowired
	private ShareUserHisDao shareUserHisDao;

	@Override
	protected BaseDao<ShareUserHis, ShareUserHisSearch> getBaseDao() {
		return shareUserHisDao;
	}
}
