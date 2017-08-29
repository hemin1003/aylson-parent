package com.aylson.dc.qmtt.controller;

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
import com.aylson.dc.qmtt.search.ShareUserHisSearch;
import com.aylson.dc.qmtt.service.ShareUserHisService;
import com.aylson.dc.qmtt.vo.ShareUserHisVo;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.utils.DateUtil2;
import com.aylson.utils.UUIDUtils;

/**
 * 邀请徒弟明细记录
 * @author Minbo
 */
@Controller
@RequestMapping("/qmtt/shareUserHis")
public class ShareUserHisController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(ShareUserHisController.class);

	@Autowired
	private ShareUserHisService shareUserHisService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/qmtt/admin/shareUserHis/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(ShareUserHisSearch shareUserHisSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			shareUserHisSearch.setIsPage(true);
			List<ShareUserHisVo> list = this.shareUserHisService.getList(shareUserHisSearch);
			result.setTotal(this.shareUserHisService.getRowCount(shareUserHisSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 后台-添加页面
	 * @param shareUserHisVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(ShareUserHisVo shareUserHisVo) {
		this.request.setAttribute("shareUserHisVo", shareUserHisVo);
		return "/jsp/qmtt/admin/shareUserHis/add";
	}
	
	/**
	 * 后台-添加保存
	 * @param shareUserHisVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(ShareUserHisVo shareUserHisVo) {
		Result result = new Result();
		try{
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			shareUserHisVo.setId(UUIDUtils.create());
			String cTime = DateUtil2.getCurrentLongDateTime();
			shareUserHisVo.setCreatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
			shareUserHisVo.setCreateDate(cTime);
			shareUserHisVo.setUpdateDate(cTime);
			Boolean flag = this.shareUserHisService.add(shareUserHisVo);
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
			ShareUserHisVo shareUserHisVo = this.shareUserHisService.getById(id);
			this.request.setAttribute("shareUserHisVo", shareUserHisVo);
		}
		return "/jsp/qmtt/admin/shareUserHis/add";
	}
	
	/**
	 * 后台-编辑保存
	 * @param shareUserHisVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(ShareUserHisVo shareUserHisVo) {
		Result result = new Result();
		try {
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			shareUserHisVo.setUpdatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
			shareUserHisVo.setUpdateDate(DateUtil2.getCurrentLongDateTime());
			Boolean flag = this.shareUserHisService.edit(shareUserHisVo);
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
			Boolean flag = this.shareUserHisService.deleteById(id);
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
