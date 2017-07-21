package com.aylson.dc.mem.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.mem.dao.ProjectInfoDao;
import com.aylson.dc.mem.po.ProjectInfo;
import com.aylson.dc.mem.search.ProjectInfoSearch;

@Repository
public class ProjectInfoDaoImpl extends BaseDaoImpl<ProjectInfo,ProjectInfoSearch> implements ProjectInfoDao {

}
