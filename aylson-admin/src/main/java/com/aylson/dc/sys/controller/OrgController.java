package com.aylson.dc.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.frame.base.Page;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.dc.sys.search.OrgSearch;
import com.aylson.dc.sys.search.ProductSearch;
import com.aylson.dc.sys.search.OrgSearch;
import com.aylson.dc.sys.service.OrgService;
import com.aylson.dc.sys.vo.OrgVo;
import com.aylson.dc.sys.vo.ProductVo;
import com.aylson.dc.sys.vo.OrgVo;

/**
 * 菜单管理
 * @author wwx
 * @since  2016-05
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/sys/org")
public class OrgController extends BaseController {
	
	@Autowired
	private OrgService orgService;     //组织机构服务
	
	
	/**
	 * 获取列表（分页）
	 * @param productSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPage", method = RequestMethod.GET)
	public Result getPage(OrgSearch orgSearch) {
		Result result = new Result();
		if(orgSearch.getParentId() == null)orgSearch.setParentId(1);
		Page<OrgVo> page = this.orgService.getPage(orgSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", page);
		return result;
	}
	
	/**
	 * 获取列表（不分页）
	 * @param productSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public Result getList(OrgSearch orgSearch) {
		Result result = new Result();
		if(orgSearch.getParentId() == null)orgSearch.setParentId(1);
		List<OrgVo> list = this.orgService.getList(orgSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", list);
		return result;
	}
	
	
	
	
	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/sys/admin/org/index";
	}	
	
	/**
	 * 根据条件获取数据字典列表信息
	 * @param orgSearch
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<OrgVo> getList(OrgSearch orgSearch) {
		List<OrgVo> list = this.orgService.getList(orgSearch);
		return list;
	}*/
	
	/**
	 * 获取树形列表
	 * @param orgSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/gridTree", method = RequestMethod.GET)
	public List<OrgVo> gridTree(OrgSearch orgSearch) {
		if(orgSearch.getId() == null){
			orgSearch.setParentId(0);
		}else{
			orgSearch.setParentId(orgSearch.getId());
			orgSearch.setId(null);
		}
		List<OrgVo> list = this.orgService.getList(orgSearch);
		for(OrgVo mItem: list){
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
	 * @param orgSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getSyncGridTree", method = RequestMethod.GET)
	public List<OrgVo> getSyncGridTree(Integer roleId,Integer curNodeId,String curNodePath) {
		OrgSearch orgSearch = new OrgSearch();
		if(curNodeId == null){
			curNodeId = 0;
		}
		List<OrgVo> list = this.orgService.getList(orgSearch);
		return this.orgService.getSyncGridTree(list, curNodeId);
	}
	
	/**
	 * 后台-添加页面
	 * @param orgVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(OrgVo orgVo) {
		this.request.setAttribute("orgVo",orgVo);
		return "/jsp/sys/admin/org/add";
	}	
	
	/**
	 * 后台-添加保存
	 * @param orgVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(OrgVo orgVo) {
		Result result = new Result();
		try{
			orgVo.setIsLeaf(true);
			Boolean flag = this.orgService.add(orgVo);
			if(flag){
				OrgVo parent = new OrgVo();
				parent.setId(orgVo.getParentId());
				parent.setIsLeaf(false);
				flag = this.orgService.edit(parent);
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
			OrgVo orgVo = this.orgService.getById(id);
			this.request.setAttribute("orgVo",orgVo);
		}
		return "/jsp/sys/admin/org/add";
	}	
	
	/**
	 * 后台-编辑保存
	 * @param orgVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(OrgVo orgVo) {
		Result result = new Result();
		try {
			Boolean flag = this.orgService.edit(orgVo);
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
			Boolean flag = this.orgService.deleteOrg(id);
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
	
	
}
