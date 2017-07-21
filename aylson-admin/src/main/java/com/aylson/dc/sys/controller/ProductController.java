package com.aylson.dc.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.frame.base.Page;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.sys.search.ProductSearch;
import com.aylson.dc.sys.service.ProductService;
import com.aylson.dc.sys.vo.ProductVo;
import com.aylson.utils.StringUtil;

/**
 * 产品展示管理
 * @author wwx
 * @since  2016-11
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/sys/product")
public class ProductController extends BaseController {
	
	@Autowired
	private ProductService productService;     //产品展示服务
	
	
	/**
	 * 获取列表（分页）
	 * @param productSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPage", method = RequestMethod.GET)
	public Result getPage(ProductSearch productSearch) {
		Result result = new Result();
		Page<ProductVo> page = this.productService.getPage(productSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", page);
		return result;
	}
	
	/**
	 * 获取列表（不分页）
	 * @param productSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public Result getList(ProductSearch productSearch) {
		Result result = new Result();
		List<ProductVo> list = this.productService.getList(productSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", list);
		return result;
	}
	
	/**
	 * 根据id查询
	 * @param id
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
		ProductVo productVo = this.productService.getById(id);
		if(productVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取信息失败，请稍后再试或者联系管理员！");
		}else{
			//处理图片展示信息
			if(StringUtil.isNotEmpty(productVo.getPictureShows())){
				String[] pictureShowsItem = productVo.getPictureShows().split("<;>");
				String[] pictureDescItem = productVo.getPictureDesc().split("<;>");
				if(pictureShowsItem != null && pictureShowsItem.length > 0){
					Map<String, String> pictureInfo =  new HashMap<String, String>();
					for(int i=0; i<pictureShowsItem.length; i++){
						pictureInfo.put(pictureShowsItem[i], pictureDescItem[i]);
					}
					productVo.setPictureInfo(pictureInfo);
				}
			}
			result.setOK(ResultCode.CODE_STATE_200, "操作成功", productVo);
		}
		return result;
	}
	
	/**
	 * 新增
	 * @param productVo
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(ProductVo productVo) {
		Result result = new Result();
		try{
			if(productVo.getCategory() == null){
				result.setError(ResultCode.CODE_STATE_4006, "产品类别id不能为空！");
			}
			if(StringUtil.isEmpty(productVo.getCategoryName())){
				result.setError(ResultCode.CODE_STATE_4006, "产品类别不能为空！");
			}
			if(StringUtil.isEmpty(productVo.getProductName())){
				result.setError(ResultCode.CODE_STATE_4006, "产品名称不能为空！");
			}
			//处理门店展示内容
			String[] pictureShowsItem = request.getParameterValues("pictureShowsItem");
			String[] pictureDescItem = request.getParameterValues("pictureDescItem");
			if(pictureShowsItem != null && pictureShowsItem.length > 0){
				StringBuffer sb = new StringBuffer();
				StringBuffer sb1 = new StringBuffer();
				for(int i=0; i<pictureShowsItem.length; i++){
					sb.append(pictureShowsItem[i]);//图片地址
					sb1.append(pictureDescItem[i]);//图片描述
					if(i != pictureShowsItem.length-1){
						sb.append("<;>");          //内容分割符
						sb1.append("<;>");         //内容分割符
					}
				}
				productVo.setPictureShows(sb.toString());
				productVo.setPictureDesc(sb1.toString());
			}
			Boolean flag = this.productService.add(productVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("接口：/sys/product/add，获取主键失败"+e.getMessage());
			result.setError(ResultCode.CODE_STATE_500, "操作失败，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	/**
	 * 修改
	 * @param productVo
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(ProductVo productVo) {
		Result result = new Result();
		try {
			if(productVo.getId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
				return result;
			}
			if(productVo.getCategory() == null){
				result.setError(ResultCode.CODE_STATE_4006, "产品类别id不能为空！");
			}
			if(StringUtil.isEmpty(productVo.getCategoryName())){
				result.setError(ResultCode.CODE_STATE_4006, "产品类别不能为空！");
			}
			if(StringUtil.isEmpty(productVo.getProductName())){
				result.setError(ResultCode.CODE_STATE_4006, "产品名称不能为空！");
			}
			//处理门店展示内容
			String[] pictureShowsItem = request.getParameterValues("pictureShowsItem");
			String[] pictureDescItem = request.getParameterValues("pictureDescItem");
			if(pictureShowsItem != null && pictureShowsItem.length > 0){
				StringBuffer sb = new StringBuffer();
				StringBuffer sb1 = new StringBuffer();
				for(int i=0; i<pictureShowsItem.length; i++){
					sb.append(pictureShowsItem[i]);//图片地址
					sb1.append(pictureDescItem[i]);//图片描述
					if(i != pictureShowsItem.length-1){
						sb.append("<;>");          //内容分割符
						sb1.append("<;>");         //内容分割符
					}
				}
				productVo.setPictureShows(sb.toString());
				productVo.setPictureDesc(sb1.toString());
			}
			Boolean flag = this.productService.edit(productVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("接口：/sys/product/update，获取主键失败"+e.getMessage());
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
			Boolean flag = this.productService.deleteById(id);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "删除成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "删除失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("接口：/sys/product/deleteById，获取主键失败"+e.getMessage());
			result.setError(ResultCode.CODE_STATE_500, "操作失败，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	
	
}
