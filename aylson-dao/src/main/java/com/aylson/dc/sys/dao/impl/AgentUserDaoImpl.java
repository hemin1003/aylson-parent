package com.aylson.dc.sys.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.sys.dao.AgentUserDao;
import com.aylson.dc.sys.po.AgentUser;
import com.aylson.dc.sys.search.AgentUserSearch;
import com.aylson.dc.sys.vo.AgentUserVo;

@Repository
public class AgentUserDaoImpl extends BaseDaoImpl<AgentUser,AgentUserSearch> implements AgentUserDao {

	@Override
	public AgentUserVo selectByUserId(Integer userId) {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectByUserId"), userId);
	}

	
}
