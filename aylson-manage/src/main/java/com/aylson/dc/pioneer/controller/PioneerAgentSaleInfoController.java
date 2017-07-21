package com.aylson.dc.pioneer.controller;

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
import com.aylson.dc.base.GeneralConstant.IntegralConfigType;
import com.aylson.dc.pioneer.search.PioneerAgentSaleInfoSearch;
import com.aylson.dc.pioneer.service.PioneerAgentSaleInfoService;
import com.aylson.dc.pioneer.vo.PioneerAgentSaleInfoVo;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.dc.sys.service.IntegralConfigService;
import com.aylson.dc.sys.vo.IntegralConfigVo;

/**
 * 开拓者代理商销售情况管理
 * @author wwx
 * @since  2016-09
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/pioneer/pioneerAgentSaleInfo")
public class PioneerAgentSaleInfoController extends BaseController {
	
	@Autowired
	private PioneerAgentSaleInfoService pioneerAgentSaleInfoService;     //开拓者代理商销售服务
	@Autowired
	private IntegralConfigService integralConfigService;                 //积分配置服务
	
	
	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/pioneer/admin/pioneerAgentSaleInfo/index";
	}	
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(PioneerAgentSaleInfoSearch pioneerAgentSaleInfoSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			pioneerAgentSaleInfoSearch.setIsPage(true);
			List<PioneerAgentSaleInfoVo> list = this.pioneerAgentSaleInfoService.getList(pioneerAgentSaleInfoSearch);
			result.setTotal(this.pioneerAgentSaleInfoService.getRowCount(pioneerAgentSaleInfoSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据条件获取列表信息
	 * @param pioneerAgentSaleInfoSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<PioneerAgentSaleInfoVo> getList(PioneerAgentSaleInfoSearch pioneerAgentSaleInfoSearch) {
		List<PioneerAgentSaleInfoVo> list = this.pioneerAgentSaleInfoService.getList(pioneerAgentSaleInfoSearch);
		return list;
	}
	
	/**
	 * 后台-添加页面
	 * @param pioneerAgentSaleInfoVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(PioneerAgentSaleInfoVo pioneerAgentSaleInfoVo) {
		IntegralConfigVo integralConfigVo = this.integralConfigService.getIntegralConfig(IntegralConfigType.P_REBATE_TO_REFERRAL, null);
		if(integralConfigVo != null){
			pioneerAgentSaleInfoVo.setRebateRate(integralConfigVo.getRate());
		}
		this.request.setAttribute("pioneerAgentSaleInfoVo",pioneerAgentSaleInfoVo);
		return "/jsp/pioneer/admin/pioneerAgentSaleInfo/add";
	}	
	
	/**
	 * 后台-添加保存
	 * @param pioneerAgentSaleInfoVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(PioneerAgentSaleInfoVo pioneerAgentSaleInfoVo) {
		Result result = new Result();
		try{
			//信息校验
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");//缓存信息
			if(sessionInfo != null && sessionInfo.getUser() != null){
				pioneerAgentSaleInfoVo.setCreater(sessionInfo.getUser().getUserName());
				pioneerAgentSaleInfoVo.setCreateId(sessionInfo.getUser().getId());
				pioneerAgentSaleInfoVo.setCreateTime(new Date());
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "获取当前操作人信息失败，请稍后再试");
				return result;
			}
			result = this.pioneerAgentSaleInfoService.addSaleInfo(pioneerAgentSaleInfoVo);
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
			PioneerAgentSaleInfoVo pioneerAgentSaleInfoVo = this.pioneerAgentSaleInfoService.getById(id);
			this.request.setAttribute("pioneerAgentSaleInfoVo",pioneerAgentSaleInfoVo);
		}
		return "/jsp/pioneer/admin/pioneerAgentSaleInfo/add";
	}	
	
	/**
	 * 后台-编辑保存
	 * @param pioneerAgentSaleInfoVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(PioneerAgentSaleInfoVo pioneerAgentSaleInfoVo) {
		Result result = new Result();
		try {
			Boolean flag = this.pioneerAgentSaleInfoService.edit(pioneerAgentSaleInfoVo);
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
			Boolean flag = this.pioneerAgentSaleInfoService.deleteById(id);
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
