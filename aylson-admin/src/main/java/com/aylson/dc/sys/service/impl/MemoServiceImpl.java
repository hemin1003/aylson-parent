package com.aylson.dc.sys.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.sys.dao.MemoDao;
import com.aylson.dc.sys.po.Memo;
import com.aylson.dc.sys.search.MemoSearch;
import com.aylson.dc.sys.service.MemoService;


@Service
public class MemoServiceImpl extends BaseServiceImpl<Memo,MemoSearch> implements MemoService {

	@Autowired
	private MemoDao memoDao;

	@Override
	protected BaseDao<Memo,MemoSearch> getBaseDao() {
		return memoDao;
	}


}
