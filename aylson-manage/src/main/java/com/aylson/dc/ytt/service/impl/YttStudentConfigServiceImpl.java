package com.aylson.dc.ytt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.ytt.dao.YttStudentConfigDao;
import com.aylson.dc.ytt.po.YttStudentConfig;
import com.aylson.dc.ytt.search.YttStudentConfigSearch;
import com.aylson.dc.ytt.service.YttStudentConfigService;

@Service
public class YttStudentConfigServiceImpl extends BaseServiceImpl<YttStudentConfig, YttStudentConfigSearch> implements YttStudentConfigService {

	@Autowired
	private YttStudentConfigDao studentConfigDao;

	@Override
	protected BaseDao<YttStudentConfig, YttStudentConfigSearch> getBaseDao() {
		return studentConfigDao;
	}
}
