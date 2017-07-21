package com.aylson.dc.mem.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.mem.dao.BankDao;
import com.aylson.dc.mem.po.Bank;
import com.aylson.dc.mem.search.BankSearch;
import com.aylson.dc.mem.service.BankService;

@Service
public class BankServiceImpl extends BaseServiceImpl<Bank,BankSearch> implements BankService {
	
	private static final Logger logger = LoggerFactory.getLogger(BankServiceImpl.class);
	
	@Autowired
	private BankDao bankDao;
	
	@Override
	protected BaseDao<Bank,BankSearch> getBaseDao() {
		return this.bankDao;
	}


	
}
