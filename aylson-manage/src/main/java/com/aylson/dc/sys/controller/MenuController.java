package com.aylson.dc.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.dc.sys.search.MenuSearch;
import com.aylson.dc.sys.service.MenuService;
import com.aylson.dc.sys.vo.MenuVo;

/**
 * 菜单管理
 * @author wwx
 * @since  2016-05
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/sys/menu")
public class MenuController extends BaseController {
	
	@Autowired
	private MenuService menuService;     //菜单服务
	
	
	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/sys/admin/menu/index";
	}	
	
	/**
	 * 获取树形列表
	 * @param menuSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/gridTree", method = RequestMethod.GET)
	public List<MenuVo> gridTree(MenuSearch menuSearch) {
		if(menuSearch.getId() == null){
			menuSearch.setParentId(0);
		}else{
			menuSearch.setParentId(menuSearch.getId());
			menuSearch.setId(null);
		}
		List<MenuVo> list = this.menuService.getList(menuSearch);
		for(MenuVo mItem: list){
			if(!mItem.getIsLeaf()){
				mItem.setState("closed");
			}else{
				mItem.setState("");
			}
		}
		return list;
	}
	
	/**
	 * 获取菜单同步树
	 * @param menuSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getSyncGridTree", method = RequestMethod.GET)
	public List<MenuVo> getSyncGridTree(Integer roleId,Integer curNodeId,String curNodePath) {
		MenuSearch menuSearch = new MenuSearch();
		if(curNodeId == null){
			curNodeId = 0;
//			curNodePath = "#0#";
		}
		menuSearch.setRoleId(roleId);
//		menuSearch.setNodePathLR(curNodePath);
//		menuSearch.setNodeId(curNodeId);
		List<MenuVo> list = this.menuService.getList(menuSearch);
		return this.menuService.getSyncGridTree(list, curNodeId);
	}
	
	/**
	 * 后台-添加页面
	 * @param menuVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(MenuVo menuVo) {
		this.request.setAttribute("menuVo",menuVo);
		return "/jsp/sys/admin/menu/add";
	}	
	
	/**
	 * 后台-添加保存
	 * @param menuVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(MenuVo menuVo) {
		Result result = new Result();
		try{
			if(menuVo.getParentId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取上层菜单id失败");
				return result;
			}
			MenuVo parent = this.menuService.getById(menuVo.getParentId());
			if(parent == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取上层菜单信息失败");
				return result;
			}
			if(parent.getType() != null && parent.getType().intValue() == 2){
				result.setError(ResultCode.CODE_STATE_4006, "操作类型的菜单不允许添加下级菜单或操作");
				return result;
			}
			menuVo.setIsLeaf(true);
			Boolean flag = this.menuService.add(menuVo);
			if(flag){
				//MenuVo parent = new MenuVo();
				//parent.setId(menuVo.getParentId());
				parent.setIsLeaf(false);
				flag = this.menuService.edit(parent);
				if(flag){
					result.setOK(ResultCode.CODE_STATE_200, "操作成功");
				}
			}
			if (!flag) {
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 后台-编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/toEdit", method = RequestMethod.GET)
	public String toEdit(Integer id) {
		if(id!=null){
			MenuVo menuVo = this.menuService.getById(id);
			this.request.setAttribute("menuVo",menuVo);
		}
		return "/jsp/sys/admin/menu/add";
	}	
	
	/**
	 * 后台-编辑保存
	 * @param menuVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(MenuVo menuVo) {
		Result result = new Result();
		try {
			if(menuVo.getParentId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取上层菜单id失败");
				return result;
			}
			MenuVo parent = this.menuService.getById(menuVo.getParentId());
			if(parent == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取上层菜单信息失败");
				return result;
			}
			if(parent.getType() != null && parent.getType().intValue() == 2){
				result.setError(ResultCode.CODE_STATE_4006, "操作类型的菜单不允许添加下级菜单或操作");
				return result;
			}
			Boolean flag = this.menuService.edit(menuVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 保存角色菜单关系
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	@RequestMapping(value = "/saveRoleMenu", method = RequestMethod.POST)
	@ResponseBody
	public Result saveRoleMenu(Integer roleId,String menuIds) {
		Result result = new Result();
		try {
			Boolean flag = this.menuService.saveRoleMenu(roleId, menuIds.split(","));
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteById", method = RequestMethod.POST)
	@ResponseBody
	public Result deleteById(Integer id) {
		Result result = new Result();
		try{
			Boolean flag = this.menuService.deleteMenu(id);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "删除成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "删除失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 获取菜单同步树
	 * @param menuSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserMenu", method = RequestMethod.GET)
	public List<MenuVo> getUserMenu(Integer parentId,Boolean isUserMenuTree) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute("sessionInfo");
		if(sessionInfo != null){
			Integer curUserId = sessionInfo.getUser().getId();//获取当前用户id
			if(curUserId != null && parentId != null){
				List<MenuVo> list =  this.menuService.getUserRoleMenu(curUserId, parentId);
				if(isUserMenuTree){
					list = this.menuService.getSyncGridTree(list, parentId);
				}
				return list;
			}
		}
		return null;
	}
	
}
