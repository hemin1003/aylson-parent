package com.aylson.dc.owner.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.owner.dao.DesignDetailSRDao;
import com.aylson.dc.owner.po.DesignDetailSR;
import com.aylson.dc.owner.search.DesignDetailSRSearch;
import com.aylson.dc.owner.service.DesignDetailSRService;
import com.aylson.dc.owner.vo.DesignDetailSRVo;


@Service
public class DesignDetailSRServiceImpl extends BaseServiceImpl<DesignDetailSR,DesignDetailSRSearch> implements DesignDetailSRService {

	@Autowired
	private DesignDetailSRDao designDetailSRDao;

	@Override
	protected BaseDao<DesignDetailSR,DesignDetailSRSearch> getBaseDao() {
		return designDetailSRDao;
	}

	@Override
	public DesignDetailSRVo getByDesignId(Integer designId) {
		if(designId != null){
			return this.designDetailSRDao.selectByDesignId(designId);
		}
		return null;
	}


}
