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
import com.aylson.dc.partner.search.CouponSearch;
import com.aylson.dc.partner.service.CouponService;
import com.aylson.dc.partner.vo.CouponVo;

/**
 * 优惠券管理
 * @author wwx
 * @since  2017-01
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/partner/coupon")
public class CouponController extends BaseController {
	
	@Autowired
	private CouponService couponService;                     //优惠券服务
	
	
	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/partner/admin/coupon/index";
	}	
	
	/**
	 * 获取列表-分页
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(CouponSearch couponSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			couponSearch.setIsPage(true);
			List<CouponVo> list = this.couponService.getList(couponSearch);
			result.setTotal(this.couponService.getRowCount(couponSearch));
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
	 * @param couponSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<CouponVo> getList(CouponSearch couponSearch) {
		List<CouponVo> list = this.couponService.getList(couponSearch);
		return list;
	}
	
	/**
	 * 后台-添加页面
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd() {
		return "/jsp/partner/admin/coupon/add";
	}	
	
	/**
	 * 后台-添加
	 * @param couponVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(CouponVo couponVo) {
		Result result = new Result();
		try{
			Boolean flag = this.couponService.add(couponVo);
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
			CouponVo couponVo = this.couponService.getById(id);
			this.request.setAttribute("couponVo",couponVo);
		}
		return "/jsp/partner/admin/coupon/add";
	}	
	
	/**
	 * 后台-编辑保存
	 * @param couponVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(CouponVo couponVo) {
		Result result = new Result();
		try {
			Boolean flag = this.couponService.edit(couponVo);
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
			Boolean flag = this.couponService.deleteById(id);
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
	 * @param couponVo
	 * @return
	 */
	@RequestMapping(value = "/admin/upDown", method = RequestMethod.POST)
	@ResponseBody
	public Result upDown(CouponVo couponVo) {
		Result result = new Result();
		try {
			Boolean flag = this.couponService.edit(couponVo);
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
