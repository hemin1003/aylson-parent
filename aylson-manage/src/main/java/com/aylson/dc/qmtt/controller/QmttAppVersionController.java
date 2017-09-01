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
import com.aylson.dc.qmtt.search.QmttAppVersionSearch;
import com.aylson.dc.qmtt.service.QmttAppVersionService;
import com.aylson.dc.qmtt.vo.QmttAppVersionVo;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.utils.DateUtil2;
import com.aylson.utils.UUIDUtils;

/**
 * APK渠道版本配置
 * @author Minbo
 */
@Controller
@RequestMapping("/qmtt/qmttAppVersion")
public class QmttAppVersionController extends BaseController {
	
	protected static final Logger logger = Logger.getLogger(QmttAppVersionController.class);

	@Autowired
	private QmttAppVersionService qmttAppVersionService;
	
	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/qmtt/admin/qmttAppVersion/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(QmttAppVersionSearch qmttAppVersionSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			qmttAppVersionSearch.setIsPage(true);
			List<QmttAppVersionVo> list = this.qmttAppVersionService.getList(qmttAppVersionSearch);
			result.setTotal(this.qmttAppVersionService.getRowCount(qmttAppVersionSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 后台-添加页面
	 * @param qmttAppVersionVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(QmttAppVersionVo qmttAppVersionVo) {
		this.request.setAttribute("qmttAppVersionVo", qmttAppVersionVo);
		return "/jsp/qmtt/admin/qmttAppVersion/add";
	}
	
	/**
	 * 后台-添加保存
	 * @param qmttAppVersionVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(QmttAppVersionVo qmttAppVersionVo) {
		Result result = new Result();
		try{
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			qmttAppVersionVo.setId(UUIDUtils.create());
			String cTime = DateUtil2.getCurrentLongDateTime();
			qmttAppVersionVo.setCreatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
			qmttAppVersionVo.setCreateDate(cTime);
			qmttAppVersionVo.setUpdateDate(cTime);
			Boolean flag = this.qmttAppVersionService.add(qmttAppVersionVo);
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
			QmttAppVersionVo qmttAppVersionVo = this.qmttAppVersionService.getById(id);
			this.request.setAttribute("qmttAppVersionVo", qmttAppVersionVo);
		}
		return "/jsp/qmtt/admin/qmttAppVersion/add";
	}
	
	/**
	 * 后台-编辑保存
	 * @param qmttAppVersionVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(QmttAppVersionVo qmttAppVersionVo) {
		Result result = new Result();
		try {
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			qmttAppVersionVo.setUpdatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
			qmttAppVersionVo.setUpdateDate(DateUtil2.getCurrentLongDateTime());
			Boolean flag = this.qmttAppVersionService.edit(qmttAppVersionVo);
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
			Boolean flag = this.qmttAppVersionService.deleteById(id);
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
