package com.aylson.dc.sys.service;

import java.util.List;

import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.sys.po.Menu;
import com.aylson.dc.sys.search.MenuSearch;
import com.aylson.dc.sys.vo.MenuVo;

public interface MenuService extends BaseService<Menu,MenuSearch> {
	
	/**
	 * 获取同步树
	 * @param list
	 * @param parentId
	 * @return
	 */
	public List<MenuVo> getSyncGridTree(List<MenuVo> list, Integer parentId);
	
	/**
	 * 保存角色勾选的菜单
	 * @param roleId
	 * @param menuIds
	 * @return
	 * @throws Exception
	 */
	public Boolean saveRoleMenu(Integer roleId,String[] menuIds) throws Exception;
	
	/**
	 * 删除菜单
	 * @param id
	 * @return
	 */
	public Boolean deleteMenu(Integer id);
	
	/**
	 * 根据用户获取配置菜单
	 * @param userId
	 * @return
	 */
	public List<MenuVo> getUserRoleMenu(Integer userId, Integer parentId);

	
}
