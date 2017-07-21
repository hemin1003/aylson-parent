package com.aylson.dc.mem.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.mem.dao.ProjectClientDao;
import com.aylson.dc.mem.po.ProjectClient;
import com.aylson.dc.mem.search.ProjectClientSearch;
import com.aylson.dc.mem.vo.ClientInfoVo;
import com.aylson.dc.mem.vo.MemAccountVo;

@Repository
public class ProjectClientDaoImpl extends BaseDaoImpl<ProjectClient,ProjectClientSearch> implements ProjectClientDao {

	@Override
	public List<MemAccountVo> selectDistributionMember() {
		return this.sqlSessionTemplate.selectList(getSqlName("selectDistributionMember"));
	}

	@Override
	public List<ClientInfoVo> selectDistributionClient() {
		return this.sqlSessionTemplate.selectList(getSqlName("selectDistributionClient"));
	}


}
