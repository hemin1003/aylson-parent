package com.aylson.dc.sys.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.sys.dao.OrgUserDao;
import com.aylson.dc.sys.po.OrgUser;
import com.aylson.dc.sys.search.OrgUserSearch;
import com.aylson.dc.sys.vo.OrgUserVo;

@Repository
public class OrgUserDaoImpl extends BaseDaoImpl<OrgUser,OrgUserSearch> implements OrgUserDao {

	@Override
	public OrgUserVo selectByUserId(Integer userId) {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectByUserId"), userId);
	}

	
}
