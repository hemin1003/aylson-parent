package com.aylson.dc.mem.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.mem.dao.ProjectDesignDao;
import com.aylson.dc.mem.po.ProjectDesign;
import com.aylson.dc.mem.search.ProjectDesignSearch;
import com.aylson.dc.mem.vo.ProjectDesignVo;

@Repository
public class ProjectDesignDaoImpl extends BaseDaoImpl<ProjectDesign,ProjectDesignSearch> implements ProjectDesignDao {

	@Override
	public ProjectDesignVo selectByProjectId(Integer projectId) {
		return this.sqlSessionTemplate.selectOne(getSqlName("selectByProjectId"), projectId);
	}

}
