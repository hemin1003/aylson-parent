package com.aylson.dc.mem.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.mem.dao.PublishDao;
import com.aylson.dc.mem.po.Publish;
import com.aylson.dc.mem.po.PublishRead;
import com.aylson.dc.mem.search.PublishSearch;

@Repository
public class PublishDaoImpl extends BaseDaoImpl<Publish,PublishSearch> implements PublishDao {

	@Override
	public Long selectReadCount(Map<String, Object> params) {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectReadInfo"), params);
	}

	@Override
	public List<PublishRead> selectReadInfo(Map<String, Object> params) {
		return this.sqlSessionTemplate.selectList(this.getSqlName("selectReadInfo"), params);
	}

	@Override
	public Boolean insertReadInfo(PublishRead publishRead) {
		int rows = this.sqlSessionTemplate.insert(this.getSqlName("insertReadInfo"), publishRead);
		if(rows > 0) return true;
		return false;
	}

}
