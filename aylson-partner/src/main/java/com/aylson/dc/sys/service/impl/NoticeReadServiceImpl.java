package com.aylson.dc.sys.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.sys.dao.NoticeReadDao;
import com.aylson.dc.sys.po.NoticeRead;
import com.aylson.dc.sys.search.NoticeReadSearch;
import com.aylson.dc.sys.service.NoticeReadService;


@Service
public class NoticeReadServiceImpl extends BaseServiceImpl<NoticeRead,NoticeReadSearch> implements NoticeReadService {

	@Autowired
	private NoticeReadDao noticeReadDao;

	@Override
	protected BaseDao<NoticeRead,NoticeReadSearch> getBaseDao() {
		return noticeReadDao;
	}


}
