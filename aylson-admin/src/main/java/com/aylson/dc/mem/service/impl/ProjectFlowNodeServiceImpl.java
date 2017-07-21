package com.aylson.dc.mem.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.mem.dao.ProjectFlowNodeDao;
import com.aylson.dc.mem.po.ProjectFlowNode;
import com.aylson.dc.mem.search.ProjectFlowNodeSearch;
import com.aylson.dc.mem.service.ProjectFlowNodeService;

@Service
public class ProjectFlowNodeServiceImpl extends BaseServiceImpl<ProjectFlowNode,ProjectFlowNodeSearch> implements ProjectFlowNodeService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectFlowNodeServiceImpl.class);
	
	@Autowired
	private ProjectFlowNodeDao projectFlowNodeDao;
	
	@Override
	protected BaseDao<ProjectFlowNode,ProjectFlowNodeSearch> getBaseDao() {
		return this.projectFlowNodeDao;
	}

	@Override
	public ProjectFlowNode getProjectFlowNode(Integer projectId, Integer status) {
		ProjectFlowNode projectFlowNode = null;
		if(projectId != null && status != null){
			ProjectFlowNodeSearch projectFlowNodeSearch = new ProjectFlowNodeSearch();
			projectFlowNodeSearch.setProjectId(projectId);
			projectFlowNodeSearch.setStatus(status);
			List<ProjectFlowNode> list = this.getList(projectFlowNodeSearch);
			if(list != null && list.size() > 0){
				return list.get(list.size()-1);
			}
		}
		return projectFlowNode;
	}


	
	
}
