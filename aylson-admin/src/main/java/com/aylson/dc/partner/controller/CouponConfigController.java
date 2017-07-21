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
import com.aylson.dc.partner.search.CouponConfigSearch;
import com.aylson.dc.partner.service.CouponConfigService;
import com.aylson.dc.partner.vo.CouponConfigVo;
import com.aylson.utils.StringUtil;

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
	 * 获取列表（分页）
	 * @param couponConfigSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPage", method = RequestMethod.GET)
	public Result getPage(CouponConfigSearch couponConfigSearch) {
		Result result = new Result();
		Page<CouponConfigVo> page = this.couponConfigService.getPage(couponConfigSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", page);
		return result;
	}
	
	/**
	 * 获取列表（不分页）
	 * @param couponConfigSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public Result getList(CouponConfigSearch couponConfigSearch) {
		Result result = new Result();
		List<CouponConfigVo> list = this.couponConfigService.getList(couponConfigSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", list);
		return result;
	}
	
	/**
	 * 获取列表（不分页）
	 * @param couponConfigSearch
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
		CouponConfigVo couponConfigVo = this.couponConfigService.getById(id);
		if(couponConfigVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取信息失败，请稍后再试或者联系管理员！");
		}else{
			result.setOK(ResultCode.CODE_STATE_200, "操作成功", couponConfigVo);
		}
		return result;
	}
	
	/**
	 * 新增
	 * @param couponConfigVo
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(CouponConfigVo couponConfigVo) {
		Result result = new Result();
		try{
			if(StringUtil.isEmpty(couponConfigVo.getCouponName())){
				result.setError(ResultCode.CODE_STATE_4006, "券名不能为空！");
				return result;
			}
			if(couponConfigVo.getCouponValue() == null){
				result.setError(ResultCode.CODE_STATE_4006, "券面值不能为空！");
				return result;
			}
			if(couponConfigVo.getAchieveMoney() == null){
				result.setError(ResultCode.CODE_STATE_4006, "使用条件不能为空！");
				return result;
			}
			if(couponConfigVo.getEffectDays() == null){
				result.setError(ResultCode.CODE_STATE_4006, "有效天数不能为空！");
				return result;
			}
			Boolean flag = this.couponConfigService.add(couponConfigVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("接口：/partner/couponConfig/add，获取主键失败"+e.getMessage());
			result.setError(ResultCode.CODE_STATE_500, "操作失败，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	/**
	 * 修改
	 * @param couponConfigVo
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(CouponConfigVo couponConfigVo) {
		Result result = new Result();
		try {
			if(couponConfigVo.getId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
				return result;
			}
			if(StringUtil.isEmpty(couponConfigVo.getCouponName())){
				result.setError(ResultCode.CODE_STATE_4006, "券名不能为空！");
				return result;
			}
			if(couponConfigVo.getCouponValue() == null){
				result.setError(ResultCode.CODE_STATE_4006, "券面值不能为空！");
				return result;
			}
			if(couponConfigVo.getAchieveMoney() == null){
				result.setError(ResultCode.CODE_STATE_4006, "使用条件不能为空！");
				return result;
			}
			if(couponConfigVo.getEffectDays() == null){
				result.setError(ResultCode.CODE_STATE_4006, "有效天数不能为空！");
				return result;
			}
			Boolean flag = this.couponConfigService.edit(couponConfigVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("接口：/partner/couponConfig/update，获取主键失败"+e.getMessage());
			result.setError(ResultCode.CODE_STATE_500, "操作失败，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteById", method = RequestMethod.POST)
	@ResponseBody
	public Result deleteById(Integer id) {
		Result result = new Result();
		try{
			if(id == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
				return result;
			}
			CouponConfigVo couponConfigVo = this.couponConfigService.getById(id);
			if(couponConfigVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取信息失败，请稍后再试或者联系管理员！");
				return result;
			}
			if(couponConfigVo.getState() != null && couponConfigVo.getState().intValue() != 0){
				result.setError(ResultCode.CODE_STATE_4006, "已经发布过的不能删除！");
				return result;
			}
			Boolean flag = this.couponConfigService.deleteById(id);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "删除成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "删除失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("接口：/partner/couponConfig/deleteById，获取主键失败"+e.getMessage());
			result.setError(ResultCode.CODE_STATE_500, "操作失败，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	/**
	 * 上架/下架
	 * @param couponConfigVo
	 * @return
	 */
	@RequestMapping(value = "/upDown", method = RequestMethod.POST)
	@ResponseBody
	public Result upDown(Integer id) {
		Result result = new Result();
		try {
			if(id == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
				return result;
			}
			CouponConfigVo couponConfigVo = this.couponConfigService.getById(id);
			if(couponConfigVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "根据获取信息失败，请稍后再试或者联系管理员！");
				return result;
			}
			if(couponConfigVo.getState().intValue() == 0){
				couponConfigVo.setState(1);   //已上架
			}else if(couponConfigVo.getState().intValue() == 1){
				couponConfigVo.setState(2);   //已下架
			}
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
