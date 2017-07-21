package com.aylson.dc.owner.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.owner.dao.DesignDetailSRDao;
import com.aylson.dc.owner.po.DesignDetailSR;
import com.aylson.dc.owner.search.DesignDetailSRSearch;
import com.aylson.dc.owner.vo.DesignDetailSRVo;

@Repository
public class DesignDetailSRDaoImpl extends BaseDaoImpl<DesignDetailSR,DesignDetailSRSearch> implements DesignDetailSRDao {

	@Override
	public DesignDetailSRVo selectByDesignId(Integer designId) {
		return this.sqlSessionTemplate.selectOne(getSqlName("selectByDesignId"), designId);
	}

	
}
