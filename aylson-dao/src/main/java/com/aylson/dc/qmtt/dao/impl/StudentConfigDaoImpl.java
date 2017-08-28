package com.aylson.dc.qmtt.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.qmtt.dao.StudentConfigDao;
import com.aylson.dc.qmtt.po.StudentConfig;
import com.aylson.dc.qmtt.search.StudentConfigSearch;

@Repository
public class StudentConfigDaoImpl extends BaseDaoImpl<StudentConfig, StudentConfigSearch> implements StudentConfigDao {

}
