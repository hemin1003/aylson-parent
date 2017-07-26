package com.aylson.core.frame.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.aylson.core.constants.SqlId;
import com.aylson.core.exception.DaoException;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.search.BaseSearch;
import com.aylson.utils.BeanUtils;

/**
 * 基础Dao接口实现类，实现改类的子类必须设置泛型类型
 */
public abstract class BaseDaoImpl<T extends Serializable, S extends BaseSearch>
		implements BaseDao<T, S> {
	@Autowired(required = true)
	protected SqlSessionTemplate sqlSessionTemplate;

	public static final String SQLNAME_SEPARATOR = ".";

	/**
	 * @fields sqlNamespace SqlMapping命名空间
	 */
	private String sqlNamespace = getDefaultSqlNamespace();

	/**
	 * 获取泛型类型的实体对象类全名
	 * 
	 * @author
	 * @return
	 * @date 2014年3月3日下午6:17:46
	 */
	protected String getDefaultSqlNamespace() {
		Class<?> genericClass = BeanUtils.getGenericClass(this.getClass());
		return genericClass == null ? null : genericClass.getName();
	}

	/**
	 * 获取SqlMapping命名空间
	 * 
	 * @author
	 * @return SqlMapping命名空间
	 * @date 2014年3月4日上午12:33:15
	 */
	public String getSqlNamespace() {
		return sqlNamespace;
	}

	/**
	 * 设置SqlMapping命名空间。 以改变默认的SqlMapping命名空间， 不能滥用此方法随意改变SqlMapping命名空间。
	 * 
	 * @author
	 * @param sqlNamespace
	 *            SqlMapping命名空间
	 * @date 2014年3月4日上午12:33:17
	 */
	public void setSqlNamespace(String sqlNamespace) {
		this.sqlNamespace = sqlNamespace;
	}

	/**
	 * 将SqlMapping命名空间与给定的SqlMapping名组合在一起。
	 * 
	 * @param sqlName
	 *            SqlMapping名
	 * @return 组合了SqlMapping命名空间后的完整SqlMapping名
	 */
	protected String getSqlName(String sqlName) {
		System.out.println(sqlNamespace + SQLNAME_SEPARATOR + sqlName);
		return sqlNamespace + SQLNAME_SEPARATOR + sqlName;
	}

	@Override
	public <V extends T> List<V> select(S search) {
		try {
			Map<String, Object> params = BeanUtils.toMap(search);
			return sqlSessionTemplate.selectList(getSqlName(SqlId.SQL_SELECT),
					params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象列表出错！语句：%s",
					getSqlName(SqlId.SQL_SELECT)), e);
		}
	}

	public <V extends T> V selectById(Integer id) {
		try {
			return sqlSessionTemplate.selectOne(
					getSqlName(SqlId.SQL_SELECT_BY_ID), id);
		} catch (Exception e) {
			throw new DaoException(String.format("根据ID查询对象出错！语句：%s",
					getSqlName(SqlId.SQL_SELECT_BY_ID)), e);
		}
	}

	@Transactional
	public <V extends T> V selectById(String id) {
		try {
			return sqlSessionTemplate.selectOne(
					getSqlName(SqlId.SQL_SELECT_BY_ID), id);
		} catch (Exception e) {
			throw new DaoException(String.format("根据ID查询对象出错！语句：%s",
					getSqlName(SqlId.SQL_SELECT_BY_ID)), e);
		}
	}
	
	@Transactional
	public <V extends T> Boolean  batchInsert( List<V>  list) {
		try {
			int rows = sqlSessionTemplate.insert(getSqlName(SqlId.SQL_BATCH_INSERT),list);
			if (rows > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new DaoException(String.format("添加对象出错！语句：%s",	getSqlName(SqlId.SQL_BATCH_INSERT)), e);
		}
	}
	
	@Transactional
	public Boolean insert(T entity) {
		try {
			int rows = sqlSessionTemplate.insert(getSqlName(SqlId.SQL_INSERT),
					entity);
			if (rows > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new DaoException(String.format("添加对象出错！语句：%s",
					getSqlName(SqlId.SQL_INSERT)), e);
		}
	}

	@Transactional
	public <V extends T> Boolean  batchUpdate( List<V>  list) {
		try {
			int rows = sqlSessionTemplate.update(getSqlName(SqlId.SQL_BATCH_UPDATE),list);
			if (rows > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new DaoException(String.format("添加对象出错！语句：%s",	getSqlName(SqlId.SQL_BATCH_UPDATE)), e);
		}
	}
	
	@Transactional
	public Boolean updateById(T entity) {
		try {
			int rows = sqlSessionTemplate.update(
					getSqlName(SqlId.SQL_UPDATE_BY_ID), entity);
			if (rows > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new DaoException(String.format("根据ID更新对象出错！语句：%s",
					getSqlName(SqlId.SQL_UPDATE_BY_ID)), e);
		}
	}

	@Transactional
	public Boolean deleteById(Integer id) {
		try {
			int rows = sqlSessionTemplate.delete(
					getSqlName(SqlId.SQL_DELETE_BY_ID), id);
			if (rows > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new DaoException(String.format("根据ID删除对象出错！语句：%s",
					getSqlName(SqlId.SQL_DELETE_BY_ID)), e);
		}
	}
	
	@Transactional
	public Boolean deleteById(String id) {
		try {
			int rows = sqlSessionTemplate.delete(
					getSqlName(SqlId.SQL_DELETE_BY_ID), id);
			if (rows > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new DaoException(String.format("根据ID删除对象出错！语句：%s",
					getSqlName(SqlId.SQL_DELETE_BY_ID)), e);
		}
	}

	@Transactional
	public Boolean delete(S search) {
		try {
			Map<String, Object> params = BeanUtils.toMap(search);
			int rows = sqlSessionTemplate.delete(getSqlName(SqlId.SQL_DELETE),
					params);
			if (rows > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new DaoException(String.format("删除对象出错！语句：%s",
					getSqlName(SqlId.SQL_DELETE)), e);
		}
	}
	
	/**
	 * 根据条件获取返回记录行数
	 * @param search
	 * @return
	 */
	public Long getRowCount(S search){
		try {
			Map<String, Object> params = BeanUtils.toMap(search);
			return sqlSessionTemplate.selectOne(getSqlName(SqlId.SQL_SELECT_COUNT), params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象总数出错！语句：%s", getSqlName(SqlId.SQL_SELECT_COUNT)), e);
		}
	}

}
