package com.aylson.dc.sys.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.sys.dao.HelpDao;
import com.aylson.dc.sys.po.Help;
import com.aylson.dc.sys.search.HelpSearch;
import com.aylson.dc.sys.service.HelpService;


@Service
public class HelpServiceImpl extends BaseServiceImpl<Help,HelpSearch> implements HelpService {

	@Autowired
	private HelpDao helpDao;

	@Override
	protected BaseDao<Help,HelpSearch> getBaseDao() {
		return helpDao;
	}


}
