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
import com.aylson.dc.base.GeneralConstant.AttachmentType;
import com.aylson.dc.base.GeneralConstant.ProjectStatus;
import com.aylson.dc.base.GeneralConstant.ProjectStatusMerge;
import com.aylson.dc.mem.search.ProjectFlowNodeSearch;
import com.aylson.dc.mem.search.ProjectInfoSearch;
import com.aylson.dc.mem.service.ProjectFlowNodeService;
import com.aylson.dc.mem.service.ProjectInfoService;
import com.aylson.dc.mem.vo.ProjectFlowNodeVo;
import com.aylson.dc.mem.vo.ProjectInfoVo;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.dc.sys.po.Attachment;
import com.aylson.dc.sys.search.AttachmentSearch;
import com.aylson.dc.sys.service.AttachmentService;
import com.aylson.utils.StringUtil;

/**
 * 方案设计管理
 * @author wwx
 * @since  2016-08
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/mem/projectInfo")
public class ProjectInfoController extends BaseController {
	
	@Autowired
	private ProjectInfoService projectInfoService;             //组织机构服务
	@Autowired
	private ProjectFlowNodeService projectFlowNodeService;     //方案流程服务
	@Autowired
	private AttachmentService attachmentService;               //附件服务
	
	
	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex(Integer memberType) {
		this.request.setAttribute("memberType", memberType);
		this.request.setAttribute("projectStatusMergeMap", ProjectStatusMerge.ProjectStatusMergeMap);
		return "/jsp/mem/admin/projectInfo/index";
	}	
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(ProjectInfoSearch projectInfoSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			projectInfoSearch.setIsPage(true);
			List<ProjectInfoVo> list = this.projectInfoService.getList(projectInfoSearch);
			result.setTotal(this.projectInfoService.getRowCount(projectInfoSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取流程列表
	 * @param projectFlowNodeSearch
	 * @return
	 */
	@RequestMapping(value = "/admin/flowNodeList", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectFlowNodeVo> flowNodeList(ProjectFlowNodeSearch projectFlowNodeSearch){
		try{
			List<ProjectFlowNodeVo> list = this.projectFlowNodeService.getList(projectFlowNodeSearch);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据条件获取会员账号列表信息
	 * @param projectInfoSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<ProjectInfoVo> getList(ProjectInfoSearch projectInfoSearch) {
		List<ProjectInfoVo> list = this.projectInfoService.getList(projectInfoSearch);
		return list;
	}
	
	/**
	 * 后台-添加页面
	 * @param projectInfoVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(ProjectInfoVo projectInfoVo) {
		this.request.setAttribute("projectInfoVo",projectInfoVo);
		return "/jsp/mem/admin/projectInfo/add";
	}	
	
	/**
	 * 后台-添加保存
	 * @param projectInfoVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(ProjectInfoVo projectInfoVo) {
		Result result = new Result();
		try{
			Boolean flag = this.projectInfoService.add(projectInfoVo);
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
			ProjectInfoVo projectInfoVo = this.projectInfoService.getById(id);
			this.request.setAttribute("projectInfoVo",projectInfoVo);
		}
		return "/jsp/mem/admin/projectInfo/add";
	}
	
	/**
	 * 后台-编辑保存
	 * @param projectInfoVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(ProjectInfoVo projectInfoVo) {
		Result result = new Result();
		try {
			//添加流程节点
			//如果审核通过，那么需要送积分
			Boolean flag = this.projectInfoService.edit(projectInfoVo);
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
	 * 后台-审核页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/toVerify", method = RequestMethod.GET)
	public String toVerify(Integer id) {
		if(id!=null){
			ProjectInfoVo projectInfoVo = this.projectInfoService.getById(id);
			this.request.setAttribute("projectInfoVo",projectInfoVo);
		}
		this.request.setAttribute("projectStatusMap", ProjectStatus.ProjectStatusMap);
		return "/jsp/mem/admin/projectInfo/verify";
	}	
	
	/**
	 * 后台-审核保存
	 * @param projectInfoVo
	 * @return
	 */
	@RequestMapping(value = "/admin/verify", method = RequestMethod.POST)
	@ResponseBody
	public Result verify(ProjectInfoVo projectInfoVo) {
		Result result = new Result();
		try {
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");//缓存信息
			if(sessionInfo != null && sessionInfo.getUser() != null){
				projectInfoVo.setOper(sessionInfo.getUser().getUserName());
				projectInfoVo.setOperId(sessionInfo.getUser().getId());
			}
			if(StringUtil.isNotEmpty(projectInfoVo.getAuditOpition())){
				projectInfoVo.setRemark(projectInfoVo.getAuditOpition());
			}
			result = this.projectInfoService.verifyPorjectInfo(projectInfoVo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 后台-审核页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/toVerifyWork", method = RequestMethod.GET)
	public String toVerifyWork(Integer id) {
		if(id!=null){
			ProjectInfoVo projectInfoVo = this.projectInfoService.getById(id);
			this.request.setAttribute("projectInfoVo",projectInfoVo);
		}
		this.request.setAttribute("projectStatusMap", ProjectStatus.ProjectStatusMap);
		return "/jsp/mem/admin/projectInfo/workVerify";
	}	
	
	/**
	 * 后台-审核保存
	 * @param projectInfoVo
	 * @return
	 */
	@RequestMapping(value = "/admin/verifyWork", method = RequestMethod.POST)
	@ResponseBody
	public Result verifyWork(ProjectInfoVo projectInfoVo) {
		Result result = new Result();
		try {
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");//缓存信息
			if(sessionInfo != null && sessionInfo.getUser() != null){
				projectInfoVo.setOper(sessionInfo.getUser().getUserName());
				projectInfoVo.setOperId(sessionInfo.getUser().getId());
			}
			if(StringUtil.isNotEmpty(projectInfoVo.getAuditOpition())){
				projectInfoVo.setRemark(projectInfoVo.getAuditOpition());
			}
			result = this.projectInfoService.verifyWork(projectInfoVo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 后台-开拓者业主资料审核页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/toVerifyPioneer", method = RequestMethod.GET)
	public String toVerifyPioneer(Integer id) {
		if(id!=null){
			ProjectInfoVo projectInfoVo = this.projectInfoService.getById(id);
			this.request.setAttribute("projectInfoVo",projectInfoVo);
		}
		this.request.setAttribute("projectStatusMap", ProjectStatus.ProjectStatusMap);
		return "/jsp/mem/admin/projectInfo/workVerify";
	}	
	
	/**
	 * 后台-开拓者业主资料审核保存
	 * @param projectInfoVo
	 * @return
	 */
	@RequestMapping(value = "/admin/verifyPioneer", method = RequestMethod.POST)
	@ResponseBody
	public Result verifyPioneer(ProjectInfoVo projectInfoVo) {
		Result result = new Result();
		try {
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");//缓存信息
			if(sessionInfo != null && sessionInfo.getUser() != null){
				projectInfoVo.setOper(sessionInfo.getUser().getUserName());
				projectInfoVo.setOperId(sessionInfo.getUser().getId());
			}
			if(StringUtil.isNotEmpty(projectInfoVo.getAuditOpition())){
				projectInfoVo.setRemark(projectInfoVo.getAuditOpition());
			}
			result = this.projectInfoService.verifyPioneer(projectInfoVo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 后台-结算页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/toSettle", method = RequestMethod.GET)
	public String toSettle(Integer id) {
		if(id!=null){
			ProjectInfoVo projectInfoVo = this.projectInfoService.getProjectInfoDetail(id);
			this.request.setAttribute("projectInfoVo",projectInfoVo);
			this.request.setAttribute("projectDesignVo",projectInfoVo.getProjectDesignVo());
		}
		this.request.setAttribute("projectStatusMap", ProjectStatus.ProjectStatusMap);
		return "/jsp/mem/admin/projectInfo/settle";
	}
	
	/**
	 * 后台-结算保存
	 * @param projectInfoVo
	 * @return
	 */
	@RequestMapping(value = "/admin/settle", method = RequestMethod.POST)
	@ResponseBody
	public Result settle(ProjectInfoVo projectInfoVo) {
		Result result = new Result();
		try {
			//result = this.projectInfoService.settle(projectInfoVo);
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");//缓存信息
			if(sessionInfo != null && sessionInfo.getUser() != null){
				projectInfoVo.setOper(sessionInfo.getUser().getUserName());
				projectInfoVo.setOperId(sessionInfo.getUser().getId());
			}
			result = this.projectInfoService.settleProject(projectInfoVo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 后台-审结算页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/toDesign", method = RequestMethod.GET)
	public String toDesign(Integer id) {
		if(id!=null){
			ProjectInfoVo projectInfoVo = this.projectInfoService.getProjectInfoDetail(id);
			this.request.setAttribute("projectInfoVo",projectInfoVo);
			this.request.setAttribute("projectDesignVo",projectInfoVo.getProjectDesignVo());
		}
		this.request.setAttribute("projectStatusMap", ProjectStatus.ProjectStatusMap);
		return "/jsp/mem/admin/projectInfo/design";
	}
	
	/**
	 * 后台-设计保存
	 * @param projectInfoVo
	 * @return
	 */
	@RequestMapping(value = "/admin/design", method = RequestMethod.POST)
	@ResponseBody
	public Result design(ProjectInfoVo projectInfoVo) {
		Result result = new Result();
		try {
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");//缓存信息
			if(sessionInfo != null && sessionInfo.getUser() != null){
				projectInfoVo.setOper(sessionInfo.getUser().getUserName());
				projectInfoVo.setOperId(sessionInfo.getUser().getId());
			}
			if(StringUtil.isNotEmpty(projectInfoVo.getAuditOpition())){
				projectInfoVo.setRemark(projectInfoVo.getAuditOpition());
			}
			result = this.projectInfoService.saveDesign(projectInfoVo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 退回设计需求
	 * @param projectInfoVo
	 * @return
	 */
	@RequestMapping(value = "/admin/returnRequirement", method = RequestMethod.POST)
	@ResponseBody
	public Result returnRequirement(ProjectInfoVo projectInfoVo) {
		Result result = new Result();
		try {
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");//缓存信息
			if(sessionInfo != null && sessionInfo.getUser() != null){
				projectInfoVo.setOper(sessionInfo.getUser().getUserName());
				projectInfoVo.setOperId(sessionInfo.getUser().getId());
			}
			if(StringUtil.isNotEmpty(projectInfoVo.getAuditOpition())){
				projectInfoVo.setRemark(projectInfoVo.getAuditOpition());
			}
			result = this.projectInfoService.returnRequirement(projectInfoVo);
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
			Boolean flag = this.projectInfoService.deleteById(id);
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
	 * 查看页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/toView", method = RequestMethod.GET)
	public String toView(Integer id) {
		if(id!=null){
			ProjectInfoVo projectInfoVo = this.projectInfoService.getProjectInfoDetail(id);
			this.request.setAttribute("projectInfoVo",projectInfoVo);
			this.request.setAttribute("projectDesignVo",projectInfoVo.getProjectDesignVo());
		}
		this.request.setAttribute("projectStatusMap", ProjectStatus.ProjectStatusMap);
		return "/jsp/mem/admin/projectInfo/view";
	}
	
	@RequestMapping(value = "/admin/reback", method = RequestMethod.POST)
	@ResponseBody
	public Result reback(Integer id) {
		Result result = new Result();
		try{
			Boolean flag = this.projectInfoService.deleteById(id);
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
	 * 后台-成功案例编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/toSuccessCase", method = RequestMethod.GET)
	public String toSuccessCase(Integer id) {
		if(id!=null){
			ProjectInfoVo projectInfoVo = this.projectInfoService.getById(id);
			AttachmentSearch attachmentSearch = new AttachmentSearch();
			attachmentSearch.setSourceId(id);
			attachmentSearch.setType(AttachmentType.PROJECT_SUCCESSCASE);
			attachmentSearch.setStatus(1);
			List<Attachment> successCaseList = this.attachmentService.getList(attachmentSearch);
			projectInfoVo.setSuccessCaseList(successCaseList);
			this.request.setAttribute("projectInfoVo",projectInfoVo);
//			this.request.setAttribute("id",id);
		}
		return "/jsp/mem/admin/projectInfo/successCase";
	}
	
	/**
	 * 后台-保存成功案例
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/successCase", method = RequestMethod.POST)
	@ResponseBody
	public Result successCase(Integer id, Boolean isSuccessfulCase, String successCaseImg) {
		Result result = new Result();
		try {
			String[] imgUrl = request.getParameterValues("imgUrl");
			result = this.projectInfoService.successCase(imgUrl, id, isSuccessfulCase, successCaseImg);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	
}
