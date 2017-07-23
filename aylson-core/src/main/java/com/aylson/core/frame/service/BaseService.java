package com.aylson.core.frame.service;

import java.io.Serializable;
import java.util.List;

import com.aylson.core.frame.base.Page;
import com.aylson.core.frame.search.BaseSearch;

/**
 * 基础的Service接口
 * @author 
 * @date 2014年3月7日下午2:22:13
 */
public interface BaseService<T extends Serializable, S extends BaseSearch> {
	/**
	 * 组合查询列表
	 * @param search
	 * @return
	 */
	public <V extends T> List<V> getList(S search);
	
	/**
	 * 根据id获取实体对象
	 * @param id
	 * @return
	 */
	public <V extends T> V getById(Integer id);
	
	/**
	 * 根据id获取实体对象
	 * @param id
	 * @return
	 */
	public <V extends T> V getById(String id);
	
	/**
	 * 插入一条记录
	 * @param entity
	 * @return
	 */
	public Boolean add(T entity);
	
	/**
	 * 更新一条记录的一个字段或所有字段
	 * @param entity
	 * @return
	 */
	public Boolean edit(T entity);
	
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
	
	/**
	 * 获取分页对象
	 * @param search
	 * @return
	 */
	public <V extends T> Page<V> getPage(S search);
	
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	public <V extends T> Boolean  batchAdd( List<V>  list);
	
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	public <V extends T> Boolean  batchUpdate( List<V>  list);
	
	
}