package com.aylson.dc.sys.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.sys.dao.NoticeDao;
import com.aylson.dc.sys.po.Notice;
import com.aylson.dc.sys.search.NoticeSearch;
import com.aylson.dc.sys.service.NoticeService;


@Service
public class NoticeServiceImpl extends BaseServiceImpl<Notice,NoticeSearch> implements NoticeService {

	@Autowired
	private NoticeDao noticeDao;

	@Override
	protected BaseDao<Notice,NoticeSearch> getBaseDao() {
		return noticeDao;
	}


}
