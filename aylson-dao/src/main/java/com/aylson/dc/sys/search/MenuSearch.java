package com.aylson.dc.sys.search;

import com.aylson.core.frame.search.BaseSearch;

public class MenuSearch extends BaseSearch {

	private static final long serialVersionUID = 7055314584366242417L;

	private Integer id;                       //主键
	private Integer parentId;                 //父ID
	private String nodePathLR;                //节点路径
	private Integer roleId;                   //角色id
	private Integer nodeId;                   //节点id
	private Boolean isOnlyRoleMenu=false;     //是否只有角色菜单
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public String getNodePathLR() {
		return nodePathLR;
	}
	public void setNodePathLR(String nodePathLR) {
		this.nodePathLR = nodePathLR;
	}
	
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	public Integer getNodeId() {
		return nodeId;
	}
	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}
	
	public Boolean getIsOnlyRoleMenu() {
		return isOnlyRoleMenu;
	}
	public void setIsOnlyRoleMenu(Boolean isOnlyRoleMenu) {
		this.isOnlyRoleMenu = isOnlyRoleMenu;
	}
	
	
}
