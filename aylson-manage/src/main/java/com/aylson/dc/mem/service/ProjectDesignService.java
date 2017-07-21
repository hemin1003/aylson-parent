package com.aylson.dc.mem.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.mem.po.ProjectDesign;
import com.aylson.dc.mem.search.ProjectDesignSearch;
import com.aylson.dc.mem.vo.ProjectDesignVo;

public interface ProjectDesignService extends BaseService<ProjectDesign,ProjectDesignSearch> {
	
	/**
	 * 根据工程项目id查询项目信息
	 * @param projectId
	 * @return
	 */
	public ProjectDesignVo getByProjectId(Integer projectId);
	
	/**
	 * 保存设计方案需求
	 * @param projectDesignVo
	 * @return
	 */
	public Result saveProjectDesign(ProjectDesignVo projectDesignVo, Integer memberId);
	
	/**
	 * 通用有效信息校验
	 * @param projectDesignVo
	 * @return
	 */
	public Result commonValid(ProjectDesignVo projectDesignVo);
}
