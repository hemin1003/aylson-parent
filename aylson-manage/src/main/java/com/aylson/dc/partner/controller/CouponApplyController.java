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
import com.aylson.dc.base.PartnerGeneralConstant.AccountState;
import com.aylson.dc.partner.search.CouponApplySearch;
import com.aylson.dc.partner.service.CouponApplyService;
import com.aylson.dc.partner.vo.CouponApplyVo;
import com.aylson.dc.partner.vo.PartnerAccountVo;

/**
 * 优惠券配置管理
 * @author wwx
 * @since  2017-01
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/partner/couponApply")
public class CouponApplyController extends BaseController {
	
	@Autowired
	private CouponApplyService couponApplyService;                     //优惠券配置服务
	
	
	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/partner/admin/couponApply/index";
	}	
	
	/**
	 * 获取列表-分页
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(CouponApplySearch couponApplySearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			couponApplySearch.setIsPage(true);
			List<CouponApplyVo> list = this.couponApplyService.getList(couponApplySearch);
			result.setTotal(this.couponApplyService.getRowCount(couponApplySearch));
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
	 * @param couponApplySearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<CouponApplyVo> getList(CouponApplySearch couponApplySearch) {
		List<CouponApplyVo> list = this.couponApplyService.getList(couponApplySearch);
		return list;
	}
	
	/**
	 * 后台-添加页面
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd() {
		return "/jsp/partner/admin/couponApply/add";
	}	
	
	/**
	 * 后台-添加
	 * @param couponApplyVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(CouponApplyVo couponApplyVo) {
		Result result = new Result();
		try{
			Boolean flag = this.couponApplyService.add(couponApplyVo);
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
			CouponApplyVo couponApplyVo = this.couponApplyService.getById(id);
			this.request.setAttribute("couponApplyVo",couponApplyVo);
		}
		return "/jsp/partner/admin/couponApply/add";
	}	
	
	/**
	 * 后台-编辑保存
	 * @param couponApplyVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(CouponApplyVo couponApplyVo) {
		Result result = new Result();
		try {
			Boolean flag = this.couponApplyService.edit(couponApplyVo);
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
			Boolean flag = this.couponApplyService.deleteById(id);
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
	
	@RequestMapping(value = "/admin/verify", method = RequestMethod.POST)
	@ResponseBody
	public Result verify(Integer id, Boolean isPass) {
		Result result = null;
		try {
			result = this.couponApplyService.verify(id, isPass);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}	
	
	
	
}
