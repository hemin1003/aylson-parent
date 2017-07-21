package com.aylson.dc.owner.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.owner.dao.DesignDetailDWDao;
import com.aylson.dc.owner.po.DesignDetailDW;
import com.aylson.dc.owner.search.DesignDetailDWSearch;
import com.aylson.dc.owner.vo.DesignDetailDWVo;

@Repository
public class DesignDetailDWDaoImpl extends BaseDaoImpl<DesignDetailDW,DesignDetailDWSearch> implements DesignDetailDWDao {

	@Override
	public List<DesignDetailDWVo> selectByDesignId(Integer designId) {
		return this.sqlSessionTemplate.selectList(getSqlName("selectByDesignId"), designId);
	}

	
}
