package com.aylson.dc.sys.controller;

import java.util.Date;
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
import com.aylson.dc.sys.search.ProductIntentSearch;
import com.aylson.dc.sys.service.ProductIntentService;
import com.aylson.dc.sys.vo.ProductIntentVo;

/**
 * 课程发布管理
 * @author wwx
 * @since  2017-03
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/sys/productIntent")
public class ProductIntentController extends BaseController {
	
	@Autowired
	private ProductIntentService productIntentService;     //产品意向登记服务
	
	
	/**
	 * 获取列表（分页）
	 * @param productIntentSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPage", method = RequestMethod.GET)
	public Result getPage(ProductIntentSearch productIntentSearch) {
		Result result = new Result();
		Page<ProductIntentVo> page = this.productIntentService.getPage(productIntentSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", page);
		return result;
	}
	
	/**
	 * 获取列表（不分页）
	 * @param productIntentSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public Result getList(ProductIntentSearch productIntentSearch) {
		Result result = new Result();
		List<ProductIntentVo> list = this.productIntentService.getList(productIntentSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", list);
		return result;
	}
	
	/**
	 * 获取列表（不分页）
	 * @param productIntentSearch
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
		ProductIntentVo productIntentVo = this.productIntentService.getById(id);
		if(productIntentVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取信息失败，请稍后再试或者联系管理员！");
		}else{
			result.setOK(ResultCode.CODE_STATE_200, "操作成功", productIntentVo);
		}
		return result;
	}
	
	/**
	 * 根据id修改状态
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	@ResponseBody
	public Result change(Integer id) {
		Result result = new Result();
		Boolean flag = false;
		try{
			if(id == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
				return result;
			}
			ProductIntentVo productIntentVo = this.productIntentService.getById(id);
			if(productIntentVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "根据id获取信息失败，请稍后再试或者联系管理员！");
				return result;
			}
			if(productIntentVo.getState() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取课程状态失败，请稍后再试或者联系管理员！");
				return result;
			}
			if(1 == productIntentVo.getState().intValue()){
				result.setError(ResultCode.CODE_STATE_4006, "该记录已经回访过了！");
				return result;
			}
			
			productIntentVo.setState(1); //标识已经回访过了
			productIntentVo.setUpdateTime(new Date());
			flag = this.productIntentService.edit(productIntentVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "操作失败，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	
}
