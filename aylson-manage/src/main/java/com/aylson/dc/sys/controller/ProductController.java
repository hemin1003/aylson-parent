package com.aylson.dc.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.easyui.EasyuiDataGridJson;
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
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/sys/admin/product/index";
	}	
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(ProductSearch productSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			productSearch.setIsPage(true);
			List<ProductVo> list = this.productService.getList(productSearch);
			result.setTotal(this.productService.getRowCount(productSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据条件获取列表信息
	 * @param productSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<ProductVo> getList(ProductSearch productSearch) {
		List<ProductVo> list = this.productService.getList(productSearch);
		return list;
	}
	
	/**
	 * 后台-添加页面
	 * @param productVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(ProductVo productVo) {
		this.request.setAttribute("productVo",productVo);
		return "/jsp/sys/admin/product/add";
	}	
	
	/**
	 * 后台-添加保存
	 * @param productVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(ProductVo productVo) {
		Result result = new Result();
		try{
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
//			Boolean flag = true;
			Boolean flag = this.productService.add(productVo);
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
			ProductVo productVo = this.productService.getById(id);
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
			this.request.setAttribute("productVo",productVo);
		}
		return "/jsp/sys/admin/product/add";
	}	
	
	/**
	 * 后台-编辑保存
	 * @param productVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(ProductVo productVo) {
		Result result = new Result();
		try {
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
			Boolean flag = this.productService.deleteById(id);
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
	 * 后台-查看页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/toView", method = RequestMethod.GET)
	public String toView(Integer id) {
		if(id != null){
			ProductVo productVo = this.productService.getById(id);
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
			this.request.setAttribute("productVo",productVo);
		}
		return "/jsp/sys/admin/product/view";
	}	
	
	
}
