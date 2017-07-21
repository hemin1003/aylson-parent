package com.aylson.dc.mem.service;

import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.mem.po.ProjectFlowNode;
import com.aylson.dc.mem.search.ProjectFlowNodeSearch;

public interface ProjectFlowNodeService extends BaseService<ProjectFlowNode,ProjectFlowNodeSearch> {
	
	/**
	 * 根据工程id和状态获取流程节点
	 * @param projectId
	 * @param status
	 * @return
	 */
	public ProjectFlowNode getProjectFlowNode(Integer projectId, Integer status);
	
}
