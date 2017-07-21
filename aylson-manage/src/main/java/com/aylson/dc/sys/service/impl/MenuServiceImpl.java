package com.aylson.dc.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.exception.ServiceException;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.sys.dao.MenuDao;
import com.aylson.dc.sys.po.Menu;
import com.aylson.dc.sys.search.MenuSearch;
import com.aylson.dc.sys.service.MenuService;
import com.aylson.dc.sys.vo.MenuVo;

@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu,MenuSearch> implements MenuService {
	
	private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);
	
	@Autowired
	private MenuDao menuDao;
	
	@Override
	protected BaseDao<Menu,MenuSearch> getBaseDao() {
		return this.menuDao;
	}

	@Override
	public List<MenuVo> getSyncGridTree(List<MenuVo> list, Integer parentId) {
		List<MenuVo> menuTreeGrid = new ArrayList<MenuVo>();//返回的树形结构的菜单列表
		for(MenuVo menuVo: list){
			int id = menuVo.getId();
			int pid = menuVo.getParentId();
			if(pid == parentId){
				List<MenuVo> children = getSyncGridTree(list, id);
				menuVo.setChildren(children);
				menuTreeGrid.add(menuVo);
			}
		}
		return menuTreeGrid;
	}

	@Override
	public Boolean saveRoleMenu(Integer roleId, String[] menuIds)
			throws Exception {
		Boolean flag = false;
		try{
			//先删除之前的角色菜单对应关系
			this.menuDao.deleteRoleMenuByRoleId(roleId);
			//添加新的对应关系
			if(menuIds != null && menuIds.length > 0){
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				for(int i=0; i<menuIds.length; i++){
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("roleId", roleId);
					params.put("menuId", menuIds[i]);
					list.add(params);
				}
				flag = this.menuDao.batchInsertRoleMenu(list);
			}
			if(!flag){
				throw new ServiceException("保存角色菜单失败。");
			}
			return flag;
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException("保存角色菜单失败。");
		}
	}

	@Override
	public Boolean deleteMenu(Integer id) {
		Boolean flag = false;
		try{
			if(id != null){
				//根据菜单id删除关联的菜单角色关系
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("menuId", id);
				this.menuDao.deleteRoleMenu(params);
				//再删除菜单
				flag = this.deleteById(id);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException("删除菜单失败");
		}
		return flag;
	}

	@Override
	public List<MenuVo> getUserRoleMenu(Integer userId, Integer parentId) {
		if(userId != null && parentId != null){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", userId);
			params.put("parentId", parentId);
			List<MenuVo> list = this.menuDao.getUserRoleMenu(params);
			return list;
		}
		return null;
	}
	
	
}
