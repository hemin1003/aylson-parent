package com.aylson.dc.qmtt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.qmtt.dao.AwardHisDao;
import com.aylson.dc.qmtt.po.AwardHis;
import com.aylson.dc.qmtt.search.AwardHisSearch;
import com.aylson.dc.qmtt.service.AwardHisService;

@Service
public class AwardHisServiceImpl  extends BaseServiceImpl<AwardHis, AwardHisSearch> implements AwardHisService {

	@Autowired
	private AwardHisDao awardHisDao;

	@Override
	protected BaseDao<AwardHis, AwardHisSearch> getBaseDao() {
		return awardHisDao;
	}
}
