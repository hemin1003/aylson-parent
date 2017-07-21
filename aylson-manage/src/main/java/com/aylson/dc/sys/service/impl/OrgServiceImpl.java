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
import com.aylson.dc.sys.dao.OrgDao;
import com.aylson.dc.sys.po.Org;
import com.aylson.dc.sys.search.OrgSearch;
import com.aylson.dc.sys.service.OrgService;
import com.aylson.dc.sys.vo.OrgVo;

@Service
public class OrgServiceImpl extends BaseServiceImpl<Org,OrgSearch> implements OrgService {
	
	private static final Logger logger = LoggerFactory.getLogger(OrgServiceImpl.class);
	
	@Autowired
	private OrgDao orgDao;
	
	@Override
	protected BaseDao<Org,OrgSearch> getBaseDao() {
		return this.orgDao;
	}

	@Override
	public List<OrgVo> getSyncGridTree(List<OrgVo> list, Integer parentId) {
		List<OrgVo> orgTreeGrid = new ArrayList<OrgVo>();//返回的树形结构的组织机构列表
		for(OrgVo orgVo: list){
			int id = orgVo.getId();
			int pid = orgVo.getParentId();
			if(pid == parentId){
				List<OrgVo> children = getSyncGridTree(list, id);
				orgVo.setChildren(children);
				orgTreeGrid.add(orgVo);
			}
		}
		return orgTreeGrid;
	}

	@Override
	public Boolean saveRoleOrg(Integer roleId, String[] orgIds)
			throws Exception {
		Boolean flag = false;
		try{
			//先删除之前的角色菜单对应关系
			this.orgDao.deleteRoleOrgByRoleId(roleId);
			//添加新的对应关系
			if(orgIds != null && orgIds.length > 0){
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				for(int i=0; i<orgIds.length; i++){
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("roleId", roleId);
					params.put("orgId", orgIds[i]);
					list.add(params);
				}
				flag = this.orgDao.batchInsertRoleOrg(list);
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
	public Boolean deleteOrg(Integer id) {
		Boolean flag = false;
		try{
			if(id != null){
				//根据菜单id删除关联的菜单角色关系
				//Map<String, Object> params = new HashMap<String, Object>();
				//params.put("orgId", id);
				//this.orgDao.deleteRoleOrg(params);
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
	public List<OrgVo> getUserRoleOrg(Integer userId, Integer parentId) {
		if(userId != null && parentId != null){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", userId);
			params.put("parentId", parentId);
			List<OrgVo> list = this.orgDao.getUserRoleOrg(params);
			return list;
		}
		return null;
	}
	
	
}
