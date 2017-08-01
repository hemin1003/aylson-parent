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
import com.aylson.dc.cfdb.search.SdkChannelConfigSearch;
import com.aylson.dc.cfdb.service.SdkChannelConfigService;
import com.aylson.dc.cfdb.vo.SdkChannelConfigVo;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.utils.DateUtil2;
import com.aylson.utils.UUIDUtils;

/**
 * SDK平台渠道分成配置
 * @author Minbo
 */
@Controller
@RequestMapping("/cfdb/sdkChannelConfig")
public class SdkChannelConfigController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(SdkChannelConfigController.class);

	@Autowired
	private SdkChannelConfigService sdkChannelConfigService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/cfdb/admin/sdkChannelConfig/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(SdkChannelConfigSearch sdkChannelConfigSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			sdkChannelConfigSearch.setIsPage(true);
			List<SdkChannelConfigVo> list = this.sdkChannelConfigService.getList(sdkChannelConfigSearch);
			result.setTotal(this.sdkChannelConfigService.getRowCount(sdkChannelConfigSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 后台-添加页面
	 * @param sdkChannelConfigVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(SdkChannelConfigVo sdkChannelConfigVo) {
		this.request.setAttribute("sdkChannelConfigVo", sdkChannelConfigVo);
		return "/jsp/cfdb/admin/sdkChannelConfig/add";
	}
	
	/**
	 * 后台-添加保存
	 * @param sdkChannelConfigVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(SdkChannelConfigVo sdkChannelConfigVo) {
		Result result = new Result();
		try{
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			sdkChannelConfigVo.setId(UUIDUtils.create());
			String cTime = DateUtil2.getCurrentLongDateTime();
			sdkChannelConfigVo.setCreatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
			sdkChannelConfigVo.setCreateDate(cTime);
			sdkChannelConfigVo.setUpdateDate(cTime);
			Boolean flag = this.sdkChannelConfigService.add(sdkChannelConfigVo);
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
			SdkChannelConfigVo sdkChannelConfigVo = this.sdkChannelConfigService.getById(id);
			this.request.setAttribute("sdkChannelConfigVo", sdkChannelConfigVo);
		}
		return "/jsp/cfdb/admin/sdkChannelConfig/add";
	}
	
	/**
	 * 后台-编辑保存
	 * @param sdkChannelConfigVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(SdkChannelConfigVo sdkChannelConfigVo) {
		Result result = new Result();
		try {
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			sdkChannelConfigVo.setUpdatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
			sdkChannelConfigVo.setUpdateDate(DateUtil2.getCurrentLongDateTime());
			Boolean flag = this.sdkChannelConfigService.edit(sdkChannelConfigVo);
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
			Boolean flag = this.sdkChannelConfigService.deleteById(id);
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
