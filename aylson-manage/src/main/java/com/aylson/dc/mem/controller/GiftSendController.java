package com.aylson.dc.mem.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.aylson.dc.mem.search.GiftSendDetailSearch;
import com.aylson.dc.mem.search.WxShareSearch;
import com.aylson.dc.mem.service.GiftSendDetailService;
import com.aylson.dc.mem.service.GiftSendService;
import com.aylson.dc.mem.service.WxShareService;
import com.aylson.dc.mem.vo.GiftSendDetailVo;
import com.aylson.dc.mem.vo.GiftSendVo;
import com.aylson.dc.mem.vo.WxShareVo;
import com.aylson.utils.DateUtil;
import com.aylson.utils.StringUtil;

/**
 * 积分发放管理
 * @author wwx
 * @since  2016-08
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/mem/giftSend")
public class GiftSendController extends BaseController {
	
	@Autowired
	private GiftSendService giftSendService;                 //礼品发放管理服务
	@Autowired
	private GiftSendDetailService giftSendDetailService;     //礼品发放详情管理服务
	@Autowired
	private WxShareService wxShareService;                   //微信分享服务
	
	
	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/mem/admin/giftSend/index";
	}	
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(GiftSendDetailSearch giftSendDetailSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			giftSendDetailSearch.setIsPage(true);
			List<GiftSendDetailVo> list = this.giftSendDetailService.getList(giftSendDetailSearch);
			result.setTotal(this.giftSendDetailService.getRowCount(giftSendDetailSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据条件获取列表信息
	 * @param giftSendSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<GiftSendDetailVo> getList(GiftSendDetailSearch giftSendDetailSearch) {
		List<GiftSendDetailVo> list = this.giftSendDetailService.getList(giftSendDetailSearch);
		return list;
	}
	
	@RequestMapping(value = "/admin/deal", method = RequestMethod.POST)
	@ResponseBody
	public Result deal(Integer id, Integer state) {
		Result result = new Result();
		try{
			if(id == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取信息失败");
				return result;
			}
			if(state == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取处理状态失败");
				return result;
			}
			GiftSendVo giftSendVo = new GiftSendVo();
			giftSendVo.setId(id);
			giftSendVo.setState(state);
			giftSendVo.setUpdateTime(new Date());
			Boolean flag = this.giftSendService.edit(giftSendVo);
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
	@RequestMapping(value = "/admin/toView", method = RequestMethod.GET)
	public String toView(Integer id) {
		WxShareVo wxShareVo = null;
		GiftSendVo giftSendVo = null;
		List<WxShareVo> wxShareList = null;
		if(id != null){
			giftSendVo = this.giftSendService.getById(id);
			if(giftSendVo != null && StringUtil.isNotEmpty(giftSendVo.getChannel())
					&& StringUtil.isNotEmpty(giftSendVo.getAccountPk())){
				WxShareSearch wxShareSearch = new WxShareSearch();
				wxShareSearch.setChannel(giftSendVo.getChannel());
				wxShareSearch.setWxOpenId(giftSendVo.getAccountPk());
				wxShareSearch.setInviterWxOpenId("join");
				List<WxShareVo> list = this.wxShareService.getList(wxShareSearch);
				if(list != null && list.size() > 0){
					wxShareVo = list.get(0);
				}
				WxShareSearch wxShareSearch2 = new WxShareSearch();
				wxShareSearch2.setChannel(giftSendVo.getChannel());
				wxShareSearch2.setInviterWxOpenId(giftSendVo.getAccountPk());
				wxShareList = this.wxShareService.getList(wxShareSearch2);
			}
		}
		if(giftSendVo != null && giftSendVo.getCreateTime() != null){
			giftSendVo.setCreateTimeStr(DateUtil.format(giftSendVo.getCreateTime()));
		}
		if(wxShareVo != null && wxShareVo.getCreateTime() != null){
			wxShareVo.setCreateTimeStr(DateUtil.format(wxShareVo.getCreateTime()));
		}
		this.request.setAttribute("giftSendVo",giftSendVo);
		this.request.setAttribute("wxShareVo",wxShareVo);
		this.request.setAttribute("wxShareList",wxShareList);
		return "/jsp/mem/admin/giftSend/view";
	}	
	
	
}
