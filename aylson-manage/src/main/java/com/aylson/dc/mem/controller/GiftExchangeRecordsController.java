package com.aylson.dc.mem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.easyui.EasyuiDataGridJson;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.dc.base.GeneralConstant.MemberType;
import com.aylson.dc.mem.search.GiftExchangeRecordsSearch;
import com.aylson.dc.mem.service.GiftExchangeRecordsService;
import com.aylson.dc.mem.vo.GiftExchangeRecordsVo;

/**
 * 积分兑换礼品记录管理
 * @author wwx
 * @since  2016-08
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/mem/giftExchangeRecords")
public class GiftExchangeRecordsController extends BaseController {
	
	@Autowired
	private GiftExchangeRecordsService giftExchangeRecordsService;     //发布管理服务
	
	
	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		this.request.setAttribute("memberTypeMap", MemberType.MemberTypeMap);
		return "/jsp/mem/admin/giftExchangeRecords/index";
	}	
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(GiftExchangeRecordsSearch giftExchangeRecordsSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			giftExchangeRecordsSearch.setIsPage(true);
			List<GiftExchangeRecordsVo> list = this.giftExchangeRecordsService.getList(giftExchangeRecordsSearch);
			result.setTotal(this.giftExchangeRecordsService.getRowCount(giftExchangeRecordsSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据条件获取列表信息
	 * @param giftExchangeRecordsSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<GiftExchangeRecordsVo> getList(GiftExchangeRecordsSearch giftExchangeRecordsSearch) {
		List<GiftExchangeRecordsVo> list = this.giftExchangeRecordsService.getList(giftExchangeRecordsSearch);
		return list;
	}
	
	
}
