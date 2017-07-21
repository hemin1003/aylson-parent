package com.aylson.dc.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.easyui.EasyuiDataGridJson;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.dc.sys.search.RegionSearch;
import com.aylson.dc.sys.service.RegionService;
import com.aylson.dc.sys.vo.RegionVo;

/**
 * 区域管理
 * @author wwx
 * @since  2016-08
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/sys/region")
public class RegionController extends BaseController {
	
	@Autowired
	private RegionService regionService;     //区域服务
	
	
	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/sys/admin/region/index";
	}	
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(RegionSearch regionSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			regionSearch.setIsPage(true);
			List<RegionVo> list = this.regionService.getList(regionSearch);
			result.setTotal(this.regionService.getRowCount(regionSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据条件获取供应商列表信息
	 * @param dictionarySearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<RegionVo> getList(RegionSearch regionSearch) {
		List<RegionVo> list = this.regionService.getList(regionSearch);
		return list;
	}
	
	
}
