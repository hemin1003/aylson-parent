package com.aylson.dc.sys.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.sys.dao.CourseDao;
import com.aylson.dc.sys.po.Course;
import com.aylson.dc.sys.search.CourseSearch;
import com.aylson.dc.sys.service.CourseService;


@Service
public class CourseServiceImpl extends BaseServiceImpl<Course,CourseSearch> implements CourseService {

	@Autowired
	private CourseDao courseDao;

	@Override
	protected BaseDao<Course,CourseSearch> getBaseDao() {
		return courseDao;
	}


}
