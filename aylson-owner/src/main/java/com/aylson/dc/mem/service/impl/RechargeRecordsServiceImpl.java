package com.aylson.dc.mem.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.mem.dao.RechargeRecordsDao;
import com.aylson.dc.mem.po.RechargeRecords;
import com.aylson.dc.mem.search.RechargeRecordsSearch;
import com.aylson.dc.mem.service.RechargeRecordsService;
import com.aylson.dc.mem.vo.RechargeRecordsVo;
import com.aylson.utils.StringUtil;

@Service
public class RechargeRecordsServiceImpl extends BaseServiceImpl<RechargeRecords,RechargeRecordsSearch> implements RechargeRecordsService {
	
	private static final Logger logger = LoggerFactory.getLogger(RechargeRecordsServiceImpl.class);

	@Autowired
	private RechargeRecordsDao rechargeRecordsDao;
	
	@Override
	protected BaseDao<RechargeRecords, RechargeRecordsSearch> getBaseDao() {
		return this.rechargeRecordsDao;
	}

	@Override
	public RechargeRecordsVo getByOrderNo(String orderNo) {
		if(StringUtil.isNotEmpty(orderNo)){
			RechargeRecordsSearch search = new RechargeRecordsSearch();
			search.setOrderNo(orderNo);
			List<RechargeRecordsVo> list = this.rechargeRecordsDao.select(search);
			if(list != null && list.size() == 1){
				return list.get(0);
			}
		}
		return null;
	}
	

	
	
}
