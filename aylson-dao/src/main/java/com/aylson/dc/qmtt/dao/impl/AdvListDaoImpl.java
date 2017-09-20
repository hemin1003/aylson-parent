package com.aylson.dc.qmtt.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.qmtt.dao.AdvListDao;
import com.aylson.dc.qmtt.po.AdvList;
import com.aylson.dc.qmtt.search.AdvListSearch;

@Repository
public class AdvListDaoImpl extends BaseDaoImpl<AdvList, AdvListSearch> implements AdvListDao {

}
