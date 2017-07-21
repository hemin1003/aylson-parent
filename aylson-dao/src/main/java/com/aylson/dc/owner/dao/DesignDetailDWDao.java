package com.aylson.dc.owner.dao;

import java.util.List;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.dc.owner.po.DesignDetailDW;
import com.aylson.dc.owner.search.DesignDetailDWSearch;
import com.aylson.dc.owner.vo.DesignDetailDWVo;

public interface DesignDetailDWDao extends BaseDao<DesignDetailDW,DesignDetailDWSearch> {
	
	public List<DesignDetailDWVo> selectByDesignId(Integer designId);
	
}
