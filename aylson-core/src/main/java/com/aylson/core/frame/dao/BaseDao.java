package com.aylson.core.frame.dao;

import java.io.Serializable;
import java.util.List;

import com.aylson.core.frame.search.BaseSearch;

/**
 * 基本的CRUD操作封装
 * @author Administrator
 *
 * @param <T>
 * @param <S>
 */
public interface BaseDao<T extends Serializable, S extends BaseSearch> {

	/**
	 * 组合查询列表
	 * @param search
	 * @return
	 */
	public <V extends T> List<V> select(S search);
	
	/**
	 * 根据id获取实体对象
	 * @param id
	 * @return
	 */
	public <V extends T> V selectById(Integer id);
	
	/**
	 * 根据id获取实体对象
	 * @param id
	 * @return
	 */
	public <V extends T> V selectById(String id);
	
	/**
	 * 插入一条记录
	 * @param entity
	 * @return
	 */
	public Boolean insert(T entity);
	
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	public <V extends T> Boolean  batchInsert( List<V>  list);
	
	/**
	 * 更新一条记录的一个字段或所有字段
	 * @param entity
	 * @return
	 */
	public Boolean updateById(T entity);
	
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	public <V extends T> Boolean  batchUpdate( List<V>  list);
	
	/**
	 * 根据id删除记录
	 * @param id
	 * @return
	 */
	public Boolean deleteById(Integer id);
	
	/**
	 * 根据id删除记录
	 * @param id
	 * @return
	 */
	public Boolean deleteById(String id);
	
	/**
	 * 根据组合条件删除记录
	 * @param search
	 * @return
	 */
	public Boolean delete(S search);
	
	/**
	 * 根据条件获取返回记录行数
	 * @param search
	 * @return
	 */
	public Long getRowCount(S search);
	
	
}
