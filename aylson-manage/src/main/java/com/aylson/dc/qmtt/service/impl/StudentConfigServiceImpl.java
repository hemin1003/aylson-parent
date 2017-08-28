package com.aylson.dc.qmtt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.qmtt.dao.StudentConfigDao;
import com.aylson.dc.qmtt.po.StudentConfig;
import com.aylson.dc.qmtt.search.StudentConfigSearch;
import com.aylson.dc.qmtt.service.StudentConfigService;

@Service
public class StudentConfigServiceImpl  extends BaseServiceImpl<StudentConfig, StudentConfigSearch> implements StudentConfigService {

	@Autowired
	private StudentConfigDao studentConfigDao;

	@Override
	protected BaseDao<StudentConfig, StudentConfigSearch> getBaseDao() {
		return studentConfigDao;
	}
}
