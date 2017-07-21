package com.aylson.dc.sys.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.sys.dao.MemoDao;
import com.aylson.dc.sys.po.Memo;
import com.aylson.dc.sys.search.MemoSearch;

@Repository
public class MemoDaoImpl extends BaseDaoImpl<Memo,MemoSearch> implements MemoDao {

	
}
