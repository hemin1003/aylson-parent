package com.aylson.dc.ytt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.ytt.dao.YttAwardHisDao;
import com.aylson.dc.ytt.po.YttAwardHis;
import com.aylson.dc.ytt.search.YttAwardHisSearch;
import com.aylson.dc.ytt.service.YttAwardHisService;

@Service
public class YttAwardHisServiceImpl  extends BaseServiceImpl<YttAwardHis, YttAwardHisSearch> implements YttAwardHisService {

	@Autowired
	private YttAwardHisDao awardHisDao;

	@Override
	protected BaseDao<YttAwardHis, YttAwardHisSearch> getBaseDao() {
		return awardHisDao;
	}
}
