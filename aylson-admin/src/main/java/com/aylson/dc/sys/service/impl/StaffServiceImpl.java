package com.aylson.dc.sys.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.sys.dao.StaffDao;
import com.aylson.dc.sys.po.Staff;
import com.aylson.dc.sys.search.StaffSearch;
import com.aylson.dc.sys.service.StaffService;


@Service
public class StaffServiceImpl extends BaseServiceImpl<Staff,StaffSearch> implements StaffService {

	@Autowired
	private StaffDao staffDao;

	@Override
	protected BaseDao<Staff,StaffSearch> getBaseDao() {
		return staffDao;
	}


}
