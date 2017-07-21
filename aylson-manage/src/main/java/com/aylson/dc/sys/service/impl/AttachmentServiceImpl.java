package com.aylson.dc.sys.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.sys.dao.AttachmentDao;
import com.aylson.dc.sys.po.Attachment;
import com.aylson.dc.sys.search.AttachmentSearch;
import com.aylson.dc.sys.service.AttachmentService;


@Service
public class AttachmentServiceImpl extends BaseServiceImpl<Attachment,AttachmentSearch> implements AttachmentService {

	@Autowired
	private AttachmentDao attachmentDao;

	@Override
	protected BaseDao<Attachment,AttachmentSearch> getBaseDao() {
		return attachmentDao;
	}

	

}
