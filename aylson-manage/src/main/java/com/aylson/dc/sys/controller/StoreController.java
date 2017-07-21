package com.aylson.dc.sys.controller;

import java.util.ArrayList;
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
import com.aylson.dc.sys.search.StoreSearch;
import com.aylson.dc.sys.service.StoreService;
import com.aylson.dc.sys.vo.StoreVo;
import com.aylson.utils.MapUtil;
import com.aylson.utils.StringUtil;

/**
 * 门店展示管理
 * @author wwx
 * @since  2016-11
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/sys/store")
public class StoreController extends BaseController {
	
	@Autowired
	private StoreService storeService;     //门店展示服务
	
	
	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/sys/admin/store/index";
	}	
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(StoreSearch storeSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			storeSearch.setIsPage(true);
			List<StoreVo> list = this.storeService.getList(storeSearch);
			result.setTotal(this.storeService.getRowCount(storeSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据条件获取列表信息
	 * @param storeSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<StoreVo> getList(StoreSearch storeSearch) {
		List<StoreVo> list = this.storeService.getList(storeSearch);
		return list;
	}
	
	/**
	 * 后台-添加页面
	 * @param storeVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(StoreVo storeVo) {
		this.request.setAttribute("storeVo",storeVo);
		return "/jsp/sys/admin/store/add";
	}	
	
	/**
	 * 后台-添加保存
	 * @param storeVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(StoreVo storeVo) {
		Result result = new Result();
		try{
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
			StoreVo storeVo = this.storeService.getById(id);
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
			this.request.setAttribute("storeVo",storeVo);
		}
		return "/jsp/sys/admin/store/add";
	}	
	
	/**
	 * 后台-编辑保存
	 * @param storeVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(StoreVo storeVo) {
		Result result = new Result();
		try {
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
			Boolean flag = this.storeService.deleteById(id);
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
	 * 根据地址获取经纬度
	 * @param address
	 * @return
	 */
	@RequestMapping(value = "/admin/getGpsByAddress", method = RequestMethod.POST)
	@ResponseBody
	public Result getGpsByAddress(String address) {
		Result result = new Result();
		try{
			if(StringUtil.isEmpty(address)){
				result.setError(ResultCode.CODE_STATE_4006, "地址不能为空");
				return result;
			}
			float[] gps = MapUtil.addressToGPS(address);
			if(gps == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取不到经纬度，请输入其他地址试试");
				return result;
			}
			result.setOK(ResultCode.CODE_STATE_200, "获取成功",gps);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	
}
