package com.aylson.dc.sys.controller;

import java.util.ArrayList;
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
import com.aylson.dc.sys.search.StoreSearch;
import com.aylson.dc.sys.service.StoreService;
import com.aylson.dc.sys.vo.StoreVo;
import com.aylson.utils.StringUtil;

/**
 * 门店展示管理
 * @author wwx
 * @since  2017-03
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/sys/store")
public class StoreController extends BaseController {
	
	@Autowired
	private StoreService storeService;     //门店展示服务
	
	
	/**
	 * 获取列表（分页）
	 * @param storeSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPage", method = RequestMethod.GET)
	public Result getPage(StoreSearch storeSearch) {
		Result result = new Result();
		Page<StoreVo> page = this.storeService.getPage(storeSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", page);
		return result;
	}
	
	/**
	 * 获取列表（不分页）
	 * @param storeSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public Result getList(StoreSearch storeSearch) {
		Result result = new Result();
		List<StoreVo> list = this.storeService.getList(storeSearch);
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
		StoreVo storeVo = this.storeService.getById(id);
		if(storeVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取信息失败，请稍后再试或者联系管理员！");
		}else{
			//处理礼品导航图片
			if(StringUtil.isNotEmpty(storeVo.getStoreImgs())){
				String[] storeImgItem = storeVo.getStoreImgs().split("<;>");
				if(storeImgItem != null && storeImgItem.length > 0){
					List<String> storeImgList = new ArrayList<String>();
					for(int i=0; i<storeImgItem.length; i++){
						storeImgList.add(storeImgItem[i]);
					}
					storeVo.setStoreImgList(storeImgList);
				}
			}
			result.setOK(ResultCode.CODE_STATE_200, "操作成功", storeVo);
		}
		return result;
	}
	
	/**
	 * 新增
	 * @param storeVo
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(StoreVo storeVo) {
		Result result = new Result();
		try{
			if(StringUtil.isEmpty(storeVo.getStoreName())){
				result.setError(ResultCode.CODE_STATE_4006, "门店名称不能为空");
				return result;
			}
			if(StringUtil.isEmpty(storeVo.getOpenTime())){
				result.setError(ResultCode.CODE_STATE_4006, "营业时间不能为空");
				return result;
			}
			//处理门店图片地址
			String[] storeImgItem = request.getParameterValues("storeImgItem");
			if(storeImgItem != null && storeImgItem.length > 0){
				StringBuffer sb = new StringBuffer();
				for(int i=0; i<storeImgItem.length; i++){
					sb.append(storeImgItem[i]);//图片地址
					sb.append("<;>");          //内容分割符
				}
				storeVo.setStoreImgs(sb.toString());
			}
			Boolean flag = this.storeService.add(storeVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("接口：/sys/store/add，获取主键失败"+e.getMessage());
			result.setError(ResultCode.CODE_STATE_500, "操作失败，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	/**
	 * 修改
	 * @param storeVo
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(StoreVo storeVo) {
		Result result = new Result();
		try {
			if(storeVo.getId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
				return result;
			}
			if(StringUtil.isEmpty(storeVo.getStoreName())){
				result.setError(ResultCode.CODE_STATE_4006, "门店名称不能为空");
				return result;
			}
			if(StringUtil.isEmpty(storeVo.getOpenTime())){
				result.setError(ResultCode.CODE_STATE_4006, "营业时间不能为空");
				return result;
			}
			//处理门店图片地址
			String[] storeImgItem = request.getParameterValues("storeImgItem");
			if(storeImgItem != null && storeImgItem.length > 0){
				StringBuffer sb = new StringBuffer();
				for(int i=0; i<storeImgItem.length; i++){
					sb.append(storeImgItem[i]);//图片地址
					sb.append("<;>");          //内容分割符
				}
				storeVo.setStoreImgs(sb.toString());
			}
			Boolean flag = this.storeService.edit(storeVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("接口：/sys/store/update，获取主键失败"+e.getMessage());
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
			Boolean flag = this.storeService.deleteById(id);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "删除成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "删除失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("接口：/sys/store/deleteById，获取主键失败"+e.getMessage());
			result.setError(ResultCode.CODE_STATE_500, "操作失败，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	
}
