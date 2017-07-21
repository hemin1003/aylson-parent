package com.aylson.dc.sys.dao;

import java.util.List;
import java.util.Map;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.dc.sys.po.Menu;
import com.aylson.dc.sys.search.MenuSearch;
import com.aylson.dc.sys.vo.MenuVo;

public interface MenuDao extends BaseDao<Menu,MenuSearch> {
	
	/**
	 * 批量插入角色菜单
	 * @param roleMenuList
	 * @return
	 */
	public Boolean batchInsertRoleMenu(List<Map<String, Object>> roleMenuList);
	
	/**
	 * 通过角色id删除角色菜单
	 * @param roleId
	 * @return
	 */
	public Boolean deleteRoleMenuByRoleId(Integer roleId);
	
	/**
	 * 组合条件删除角色菜单
	 * @param params
	 * @return
	 */
	public Boolean deleteRoleMenu(Map<String, Object> params);
	
	/**
	 * 获取用户角色菜单
	 * @param params
	 * @return
	 */
	public List<MenuVo> getUserRoleMenu(Map<String, Object> params);
	
}
