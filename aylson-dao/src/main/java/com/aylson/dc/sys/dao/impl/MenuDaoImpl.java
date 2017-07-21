package com.aylson.dc.sys.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.sys.dao.MenuDao;
import com.aylson.dc.sys.po.Menu;
import com.aylson.dc.sys.search.MenuSearch;
import com.aylson.dc.sys.vo.MenuVo;

@Repository
public class MenuDaoImpl extends BaseDaoImpl<Menu,MenuSearch> implements MenuDao {

	@Override
	public Boolean batchInsertRoleMenu(List<Map<String, Object>> roleMenuList) {
		int rows = this.sqlSessionTemplate.insert(this.getSqlName("batchInsertRoleMenu"), roleMenuList);
		if(rows > 0) return true;
		return false;
	}
	
	@Override
	public Boolean deleteRoleMenuByRoleId(Integer roleId){
		int row = this.sqlSessionTemplate.delete(this.getSqlName("deleteRoleMenuByRoleId"), roleId);
		if(row>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Boolean deleteRoleMenu(Map<String, Object> params) {
		int row = this.sqlSessionTemplate.delete(this.getSqlName("deleteRoleMenu"), params);
		if(row>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<MenuVo> getUserRoleMenu(Map<String, Object> params) {
		return this.sqlSessionTemplate.selectList(this.getSqlName("getUserRoleMenu"), params);
	}
}
