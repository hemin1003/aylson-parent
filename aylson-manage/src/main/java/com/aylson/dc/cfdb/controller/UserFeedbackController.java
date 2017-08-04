package com.aylson.dc.cfdb.controller;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.easyui.EasyuiDataGridJson;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.cfdb.search.UserFeedbackSearch;
import com.aylson.dc.cfdb.service.UserFeedbackService;
import com.aylson.dc.cfdb.vo.UserFeedbackVo;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.utils.DateUtil2;
import com.aylson.utils.UUIDUtils;

/**
 * 用户反馈数据记录
 * @author Minbo
 */
@Controller
@RequestMapping("/cfdb/userFeedback")
public class UserFeedbackController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(UserFeedbackController.class);

	@Autowired
	private UserFeedbackService userFeedbackService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/cfdb/admin/userFeedback/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(UserFeedbackSearch userFeedbackSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			userFeedbackSearch.setIsPage(true);
			List<UserFeedbackVo> list = this.userFeedbackService.getList(userFeedbackSearch);
			result.setTotal(this.userFeedbackService.getRowCount(userFeedbackSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 后台-添加页面
	 * @param userFeedbackVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(UserFeedbackVo userFeedbackVo) {
		this.request.setAttribute("userFeedbackVo", userFeedbackVo);
		return "/jsp/cfdb/admin/userFeedback/add";
	}
	
	/**
	 * 后台-添加保存
	 * @param userFeedbackVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(UserFeedbackVo userFeedbackVo) {
		Result result = new Result();
		try{
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			userFeedbackVo.setId(UUIDUtils.create());
			String cTime = DateUtil2.getCurrentLongDateTime();
			userFeedbackVo.setCreatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
			userFeedbackVo.setCreateDate(cTime);
			userFeedbackVo.setUpdateDate(cTime);
			Boolean flag = this.userFeedbackService.add(userFeedbackVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
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
	public String toEdit(String id) {
		if(id != null){
			UserFeedbackVo userFeedbackVo = this.userFeedbackService.getById(id);
			this.request.setAttribute("userFeedbackVo", userFeedbackVo);
		}
		return "/jsp/cfdb/admin/userFeedback/add";
	}
	
	/**
	 * 后台-编辑保存
	 * @param userFeedbackVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(UserFeedbackVo userFeedbackVo) {
		Result result = new Result();
		try {
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			userFeedbackVo.setUpdatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
			userFeedbackVo.setUpdateDate(DateUtil2.getCurrentLongDateTime());
			Boolean flag = this.userFeedbackService.edit(userFeedbackVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
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
	public Result deleteById(String id) {
		Result result = new Result();
		try{
			Boolean flag = this.userFeedbackService.deleteById(id);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "删除成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "删除失败");
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
}
