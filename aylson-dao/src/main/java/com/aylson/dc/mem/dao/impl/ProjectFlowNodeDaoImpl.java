package com.aylson.dc.mem.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.mem.dao.ProjectFlowNodeDao;
import com.aylson.dc.mem.po.ProjectFlowNode;
import com.aylson.dc.mem.search.ProjectFlowNodeSearch;

@Repository
public class ProjectFlowNodeDaoImpl extends BaseDaoImpl<ProjectFlowNode,ProjectFlowNodeSearch> implements ProjectFlowNodeDao {

}
