package com.aylson.dc.mem.dao;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.dc.mem.po.ProjectDesign;
import com.aylson.dc.mem.search.ProjectDesignSearch;
import com.aylson.dc.mem.vo.ProjectDesignVo;

public interface ProjectDesignDao extends BaseDao<ProjectDesign,ProjectDesignSearch> {
	
	/**
	 * 根据工程项目id查询项目信息
	 * @param projectId
	 * @return
	 */
	public ProjectDesignVo selectByProjectId(Integer projectId);
	
}
