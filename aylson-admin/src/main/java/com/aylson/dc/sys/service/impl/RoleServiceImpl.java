package com.aylson.dc.sys.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.dc.sys.dao.RoleDao;
import com.aylson.dc.sys.po.Role;
import com.aylson.dc.sys.search.RoleSearch;
import com.aylson.dc.sys.service.RoleService;
import com.aylson.dc.sys.vo.RoleVo;


@Service
public class RoleServiceImpl extends BaseServiceImpl<Role,RoleSearch> implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	protected BaseDao<Role,RoleSearch> getBaseDao() {
		return roleDao;
	}

	

}
