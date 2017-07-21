package com.aylson.dc.mem.controller;

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
import com.aylson.dc.base.GeneralConstant.PublishStatus;
import com.aylson.dc.mem.search.PublishSearch;
import com.aylson.dc.mem.service.PublishService;
import com.aylson.dc.mem.vo.PublishVo;

/**
 * 发布管理
 * @author wwx
 * @since  2016-08
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/mem/publish")
public class PublishController extends BaseController {
	
	@Autowired
	private PublishService publishService;     //发布管理服务
	
	
	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex(Integer type) {
		this.request.setAttribute("type", type);
		return "/jsp/mem/admin/publish/index";
	}	
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(PublishSearch publishSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			publishSearch.setIsPage(true);
			List<PublishVo> list = this.publishService.getList(publishSearch);
			result.setTotal(this.publishService.getRowCount(publishSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据条件获取发布列表信息
	 * @param publishSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<PublishVo> getList(PublishSearch publishSearch) {
		List<PublishVo> list = this.publishService.getList(publishSearch);
		return list;
	}
	
	/**
	 * 后台-添加页面
	 * @param publishVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(PublishVo publishVo) {
		this.request.setAttribute("publishVo",publishVo);
		return "/jsp/mem/admin/publish/add";
	}	
	
	/**
	 * 后台-添加保存
	 * @param publishVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(PublishVo publishVo) {
		Result result = new Result();
		try{
			publishVo.setCreateTime(new Date());
			Boolean flag = this.publishService.add(publishVo);
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
			PublishVo publishVo = this.publishService.getById(id);
			this.request.setAttribute("publishVo",publishVo);
		}
		return "/jsp/mem/admin/publish/add";
	}	
	
	/**
	 * 后台-编辑保存
	 * @param publishVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(PublishVo publishVo) {
		Result result = new Result();
		try {
			Boolean flag = this.publishService.edit(publishVo);
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
	
	@RequestMapping(value = "/admin/changeStatus", method = RequestMethod.POST)
	@ResponseBody
	public Result publish(PublishVo publishVo) {
		Result result = new Result();
		try {
			if(publishVo.getStatus() == null){
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
				return result;
			}
			if(publishVo.getStatus().intValue() == PublishStatus.PUBLISH){
				publishVo.setPublishTime(new Date());
			}
			Boolean flag = this.publishService.edit(publishVo);
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
			Boolean flag = this.publishService.deleteById(id);
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
	
	@RequestMapping(value = "/admin/toView", method = RequestMethod.GET)
	public String toView(Integer id) {
		if(id!=null){
			PublishVo publishVo = this.publishService.getById(id);
			this.request.setAttribute("publishVo",publishVo);
		}
		return "/jsp/mem/admin/publish/view";
	}
	
	
}
