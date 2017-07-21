package com.aylson.dc.sys.service;

import java.util.List;

import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.sys.po.Org;
import com.aylson.dc.sys.search.OrgSearch;
import com.aylson.dc.sys.vo.OrgVo;

public interface OrgService extends BaseService<Org,OrgSearch> {
	
	/**
	 * 获取同步树
	 * @param list
	 * @param parentId
	 * @return
	 */
	public List<OrgVo> getSyncGridTree(List<OrgVo> list, Integer parentId);
	
	/**
	 * 保存角色勾选的菜单
	 * @param roleId
	 * @param orgIds
	 * @return
	 * @throws Exception
	 */
	public Boolean saveRoleOrg(Integer roleId,String[] orgIds) throws Exception;
	
	/**
	 * 删除菜单
	 * @param id
	 * @return
	 */
	public Boolean deleteOrg(Integer id);
	
	/**
	 * 根据用户获取配置菜单
	 * @param userId
	 * @return
	 */
	public List<OrgVo> getUserRoleOrg(Integer userId, Integer parentId);

	
}
