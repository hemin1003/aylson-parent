package com.aylson.dc.partner.controller;

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
import com.aylson.dc.partner.search.CouponConfigSearch;
import com.aylson.dc.partner.service.CouponConfigService;
import com.aylson.dc.partner.vo.CouponConfigVo;

/**
 * 优惠券配置管理
 * @author wwx
 * @since  2017-01
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/partner/couponConfig")
public class CouponConfigController extends BaseController {
	
	@Autowired
	private CouponConfigService couponConfigService;                     //优惠券配置服务
	
	
	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/partner/admin/couponConfig/index";
	}	
	
	/**
	 * 获取列表-分页
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(CouponConfigSearch couponConfigSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			couponConfigSearch.setIsPage(true);
			List<CouponConfigVo> list = this.couponConfigService.getList(couponConfigSearch);
			result.setTotal(this.couponConfigService.getRowCount(couponConfigSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取列表-不分页
	 * 根据条件获取列表信息
	 * @param couponConfigSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<CouponConfigVo> getList(CouponConfigSearch couponConfigSearch) {
		List<CouponConfigVo> list = this.couponConfigService.getList(couponConfigSearch);
		return list;
	}
	
	/**
	 * 后台-添加页面
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd() {
		return "/jsp/partner/admin/couponConfig/add";
	}	
	
	/**
	 * 后台-添加
	 * @param couponConfigVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(CouponConfigVo couponConfigVo) {
		Result result = new Result();
		try{
			Boolean flag = this.couponConfigService.add(couponConfigVo);
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
			CouponConfigVo couponConfigVo = this.couponConfigService.getById(id);
			this.request.setAttribute("couponConfigVo",couponConfigVo);
		}
		return "/jsp/partner/admin/couponConfig/add";
	}	
	
	/**
	 * 后台-编辑保存
	 * @param couponConfigVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(CouponConfigVo couponConfigVo) {
		Result result = new Result();
		try {
			Boolean flag = this.couponConfigService.edit(couponConfigVo);
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
			Boolean flag = this.couponConfigService.deleteById(id);
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
	 * 上架/下架
	 * @param couponConfigVo
	 * @return
	 */
	@RequestMapping(value = "/admin/upDown", method = RequestMethod.POST)
	@ResponseBody
	public Result upDown(CouponConfigVo couponConfigVo) {
		Result result = new Result();
		try {
			Boolean flag = this.couponConfigService.edit(couponConfigVo);
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
	
	
}
