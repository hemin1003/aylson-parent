package com.aylson.dc.mem.dao;

import java.util.List;
import java.util.Map;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.dc.mem.po.Publish;
import com.aylson.dc.mem.po.PublishRead;
import com.aylson.dc.mem.search.PublishSearch;

public interface PublishDao extends BaseDao<Publish,PublishSearch> {
	
	/**
	 * 查询-读取情况总条数
	 * @param params
	 * @return
	 */
	public Long selectReadCount(Map<String, Object> params);
	
	/**
	 * 查询读取情况
	 * @param params
	 * @return
	 */
	public List<PublishRead> selectReadInfo(Map<String, Object> params);
	
	/**
	 * 添加读取情况
	 * @param publishRead
	 * @return
	 */
	public Boolean insertReadInfo(PublishRead publishRead);
	
}
