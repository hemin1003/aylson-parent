package com.aylson.dc.mem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.easyui.EasyuiDataGridJson;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.base.GeneralConstant.UserType;
import com.aylson.dc.mem.search.MemAccountSearch;
import com.aylson.dc.mem.service.MemAccountService;
import com.aylson.dc.mem.vo.MemAccountVo;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.dc.sys.service.AgentUserService;

/**
 * 会员账号管理
 * @author wwx
 * @since  2016-08
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/mem/memAccount")
public class MemAccountController extends BaseController {
	
	@Autowired
	private MemAccountService memAccountService;     //会员账号服务
	@Autowired
	private AgentUserService agentUserService;       //代理商用户服务
	
	
	/**
	 * 后台-首页
	 * @param isOnlyMy   是否我的设计师
	 * @param memberType 账号类型
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex(Boolean isOnlyMy, Integer memberType) {
		this.request.setAttribute("isOnlyMy", isOnlyMy);
		this.request.setAttribute("memberType", memberType);
		return "/jsp/mem/admin/memAccount/index";
	}	
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(MemAccountSearch memAccountSearch, Boolean isOnlyMy){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			memAccountSearch.setIsPage(true);
			if(isOnlyMy != null && isOnlyMy){//是否只查询属于我的会员
				SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
				if(sessionInfo != null && sessionInfo.getUser() != null && sessionInfo.getUser().getType() == UserType.AGENT_USER){
					memAccountSearch.setByAgentUserId(sessionInfo.getUser().getId());
				}else{
					return result;
				}
			}
			List<MemAccountVo> list = this.memAccountService.getList(memAccountSearch);
			result.setTotal(this.memAccountService.getRowCount(memAccountSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据条件获取会员账号列表信息
	 * @param memAccountSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<MemAccountVo> getList(MemAccountSearch memAccountSearch) {
		List<MemAccountVo> list = this.memAccountService.getList(memAccountSearch);
		return list;
	}
	
	/**
	 * 后台-添加页面
	 * @param memAccountVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(MemAccountVo memAccountVo) {
		this.request.setAttribute("memAccountVo",memAccountVo);
		return "/jsp/mem/admin/memAccount/add";
	}	
	
	/**
	 * 后台-添加保存
	 * @param memAccountVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(MemAccountVo memAccountVo) {
		Result result = new Result();
		try{
			Boolean flag = this.memAccountService.add(memAccountVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
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
			MemAccountVo memAccountVo = this.memAccountService.getById(id);
			this.request.setAttribute("memAccountVo",memAccountVo);
		}
		return "/jsp/mem/admin/memAccount/add";
	}	
	
	/**
	 * 后台-编辑保存
	 * @param memAccountVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(MemAccountVo memAccountVo) {
		Result result = new Result();
		try {
			Boolean flag = this.memAccountService.edit(memAccountVo);
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
	@RequestMapping(value = "/admin/deleteById", method = RequestMethod.POST)
	@ResponseBody
	public Result deleteById(Integer id) {
		Result result = new Result();
		try{
			Boolean flag = this.memAccountService.deleteById(id);
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
