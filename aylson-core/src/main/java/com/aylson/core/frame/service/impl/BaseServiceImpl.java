package com.aylson.core.frame.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.aylson.core.frame.base.Page;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.search.BaseSearch;
import com.aylson.core.frame.service.BaseService;

/**
 * 基础Service服务接口实现类
 */
public abstract class BaseServiceImpl<T extends Serializable,S extends BaseSearch> implements BaseService<T,S> {

	/**
	 * 获取基础数据库操作类
	 * @return
	 */
	protected abstract BaseDao<T,S> getBaseDao();
	
	/**
	 * 组合查询列表
	 * @param search
	 * @return
	 */
	public <V extends T> List<V> getList(S search){
		return this.getBaseDao().select(search);
	}
	
	/**
	 * 根据id获取实体对象
	 * @param id
	 * @return
	 */
	public <V extends T> V getById(Integer id){
		return this.getBaseDao().selectById(id);
	}
	
	/**
	 * 根据id获取实体对象
	 * @param id
	 * @return
	 */
	public <V extends T> V getById(String id){
		return this.getBaseDao().selectById(id);
	}
	
	/**
	 * 插入一条记录
	 * @param entity
	 * @return
	 */
	@Transactional
	public Boolean add(T entity){
		return this.getBaseDao().insert(entity);
	}
	
	/**
	 * 更新一条记录的一个字段或所有字段
	 * @param entity
	 * @return
	 */
	@Transactional
	public Boolean edit(T entity){
		return this.getBaseDao().updateById(entity);
	}
	
	/**
	 * 根据id删除记录
	 * @param id
	 * @return
	 */
	@Transactional
	public Boolean deleteById(Integer id){
		return this.getBaseDao().deleteById(id);
	}
	
	/**
	 * 根据id删除记录
	 * @param id
	 * @return
	 */
	@Transactional
	public Boolean deleteById(String id){
		return this.getBaseDao().deleteById(id);
	}
	
	/**
	 * 根据组合条件删除记录
	 * @param search
	 * @return
	 */
	@Transactional
	public Boolean delete(S search){
		return this.getBaseDao().delete(search);
	}
	

	/**
	 * 根据条件获取返回记录行数
	 * @param search
	 * @return
	 */
	public Long getRowCount(S search){
		return this.getBaseDao().getRowCount(search);
	}
	
	/**
	 * 根据条件获取分页对象
	 */
	public <V extends T> Page<V> getPage(S search){
		Page<V> page = new Page<V>();
		search.setIsPage(true);
		List<V> list = this.getBaseDao().select(search);
		page.setPage(search.getPage());
		page.setRows(search.getRows());
		page.setTotal(this.getBaseDao().getRowCount(search));
		page.setRowsObject(list);
		return page;
	}
	
	/**
	 * 批量插入记录
	 */
	@Transactional
	public <V extends T> Boolean  batchAdd( List<V>  list){
		return this.getBaseDao().batchInsert(list);
	}
	
	/**
	 * 批量更新记录
	 */
	@Transactional
	public <V extends T> Boolean  batchUpdate( List<V>  list){
		return this.getBaseDao().batchUpdate(list);
	}

}
