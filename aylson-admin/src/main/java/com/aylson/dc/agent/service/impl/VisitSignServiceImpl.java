package com.aylson.dc.agent.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.agent.dao.VisitSignDao;
import com.aylson.dc.agent.po.VisitSign;
import com.aylson.dc.agent.search.VisitSignSearch;
import com.aylson.dc.agent.service.VisitSignService;


@Service
public class VisitSignServiceImpl extends BaseServiceImpl<VisitSign,VisitSignSearch> implements VisitSignService {

	@Autowired
	private VisitSignDao visitSignDao;

	@Override
	protected BaseDao<VisitSign,VisitSignSearch> getBaseDao() {
		return visitSignDao;
	}

	

}
