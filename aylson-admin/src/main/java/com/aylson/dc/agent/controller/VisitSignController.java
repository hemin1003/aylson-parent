package com.aylson.dc.agent.controller;

import java.util.Date;
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
import com.aylson.dc.agent.search.VisitSignSearch;
import com.aylson.dc.agent.service.VisitSignService;
import com.aylson.dc.agent.vo.VisitSignVo;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.utils.StringUtil;
import com.aylson.utils.VerificationUtils;

/**
 * 反馈管理
 * @author wwx
 * @since  2016-08
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/agent/visitSign")
public class VisitSignController extends BaseController {
	
	@Autowired
	private VisitSignService visitSignService;     //反馈服务
	
	
	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/agent/admin/visitSign/index";
	}	
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(VisitSignSearch visitSignSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			if(sessionInfo != null && sessionInfo.getUser() != null){
				visitSignSearch.setAgentId(sessionInfo.getUser().getId());
			}else{
				return result;
			}
			List<VisitSignVo> list = this.visitSignService.getList(visitSignSearch);
			result.setTotal(this.visitSignService.getRowCount(visitSignSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据条件获取列表信息
	 * @param visitSignSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<VisitSignVo> getList(VisitSignSearch visitSignSearch) {
		List<VisitSignVo> list = this.visitSignService.getList(visitSignSearch);
		return list;
	}
	
	/**
	 * 后台-添加页面
	 * @param visitSignVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(VisitSignVo visitSignVo) {
		this.request.setAttribute("visitSignVo",visitSignVo);
		return "/jsp/agent/admin/visitSign/add";
	}	
	
	/**
	 * 后台-添加保存
	 * @param visitSignVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(VisitSignVo visitSignVo) {
		Result result = new Result();
		try{
			if(StringUtil.isEmpty(visitSignVo.getMobilePhone())){
				result.setError(ResultCode.CODE_STATE_4006, "手机号码不能为空");
				return result;
			}
			if(!VerificationUtils.isValid(visitSignVo.getMobilePhone(), VerificationUtils.MOBILE)){
				result.setError(ResultCode.CODE_STATE_4006, "请输入有效的手机号码");
				return result;
			}
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			if(sessionInfo != null && sessionInfo.getUser() != null){
				visitSignVo.setAgentId(sessionInfo.getUser().getId());
				visitSignVo.setAgentName(sessionInfo.getUser().getUserName());
			}
			visitSignVo.setCreateTime(new Date());
			Boolean flag = this.visitSignService.add(visitSignVo);
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
		if(id != null){
			VisitSignVo visitSignVo = this.visitSignService.getById(id);
			this.request.setAttribute("visitSignVo",visitSignVo);
		}
		return "/jsp/agent/admin/visitSign/add";
	}	
	
	/**
	 * 后台-编辑保存
	 * @param visitSignVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(VisitSignVo visitSignVo) {
		Result result = new Result();
		try {
			Boolean flag = this.visitSignService.edit(visitSignVo);
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
			Boolean flag = this.visitSignService.deleteById(id);
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
