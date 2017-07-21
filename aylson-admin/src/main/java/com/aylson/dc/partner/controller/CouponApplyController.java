package com.aylson.dc.partner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.frame.base.Page;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.partner.search.CouponApplySearch;
import com.aylson.dc.partner.service.CouponApplyService;
import com.aylson.dc.partner.vo.CouponApplyVo;

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
	 * 获取列表（分页）
	 * @param couponApplySearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPage", method = RequestMethod.GET)
	public Result getPage(CouponApplySearch couponApplySearch) {
		Result result = new Result();
		Page<CouponApplyVo> page = this.couponApplyService.getPage(couponApplySearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", page);
		return result;
	}
	
	/**
	 * 获取列表（不分页）
	 * @param couponApplySearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public Result getList(CouponApplySearch couponApplySearch) {
		Result result = new Result();
		List<CouponApplyVo> list = this.couponApplyService.getList(couponApplySearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", list);
		return result;
	}
	
	/**
	 * 获取列表（不分页）
	 * @param couponApplySearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getById", method = RequestMethod.GET)
	public Result getById(Integer id) {
		Result result = new Result();
		if(id == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
			return result;
		}
		CouponApplyVo couponApplyVo = this.couponApplyService.getById(id);
		if(couponApplyVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取信息失败，请稍后再试或者联系管理员！");
		}else{
			result.setOK(ResultCode.CODE_STATE_200, "操作成功", couponApplyVo);
		}
		return result;
	}
	
	/**
	 * 审核
	 * @param id
	 * @param isPass
	 * @return
	 */
	@RequestMapping(value = "/verify", method = RequestMethod.POST)
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
