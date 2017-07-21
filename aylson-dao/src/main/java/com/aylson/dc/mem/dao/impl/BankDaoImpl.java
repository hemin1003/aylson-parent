package com.aylson.dc.mem.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.mem.dao.BankDao;
import com.aylson.dc.mem.po.Bank;
import com.aylson.dc.mem.search.BankSearch;

@Repository
public class BankDaoImpl extends BaseDaoImpl<Bank,BankSearch> implements BankDao {



}
