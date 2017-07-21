package com.aylson.dc.owner.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.owner.dao.DesignDetailDWDao;
import com.aylson.dc.owner.po.DesignDetailDW;
import com.aylson.dc.owner.search.DesignDetailDWSearch;
import com.aylson.dc.owner.service.DesignDetailDWService;
import com.aylson.dc.owner.vo.DesignDetailDWVo;


@Service
public class DesignDetailDWServiceImpl extends BaseServiceImpl<DesignDetailDW,DesignDetailDWSearch> implements DesignDetailDWService {

	@Autowired
	private DesignDetailDWDao designDetailDWDao;

	@Override
	protected BaseDao<DesignDetailDW,DesignDetailDWSearch> getBaseDao() {
		return designDetailDWDao;
	}

	@Override
	public List<DesignDetailDWVo> getByDesignId(Integer designId) {
		if(designId != null){
			return this.designDetailDWDao.selectByDesignId(designId);
		}
		return null;
	}


}
