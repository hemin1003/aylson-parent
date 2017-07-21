package com.aylson.dc.mem.controller;

import java.util.Date;
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
	 * 获取列表（分页）
	 * @param giftSendDetailSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPage", method = RequestMethod.GET)
	public Result getPage(GiftSendDetailSearch giftSendDetailSearch) {
		Result result = new Result();
		Page<GiftSendDetailVo> page = this.giftSendDetailService.getPage(giftSendDetailSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", page);
		return result;
	}
	
	/**
	 * 获取列表（不分页）
	 * @param giftSendDetailSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public Result getList(GiftSendDetailSearch giftSendDetailSearch) {
		Result result = new Result();
		List<GiftSendDetailVo> list = this.giftSendDetailService.getList(giftSendDetailSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", list);
		return result;
	}
	
	/**
	 * 获取活动参与情况
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getActInfo", method = RequestMethod.GET)
	public Result getActInfo(Integer id) {
		Result result = new Result();
		if(id == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
			return result;
		}
		Map<String, Object> data = new HashMap<String, Object>();
		GiftSendVo giftSendVo = null;
		WxShareVo wxShareVo = null;
		List<WxShareVo> wxShareList = null;
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
		if(giftSendVo != null && giftSendVo.getCreateTime() != null){
			giftSendVo.setCreateTimeStr(DateUtil.format(giftSendVo.getCreateTime()));
		}
		if(wxShareVo != null && wxShareVo.getCreateTime() != null){
			wxShareVo.setCreateTimeStr(DateUtil.format(wxShareVo.getCreateTime()));
		}
		data.put("giftSendVo",giftSendVo);
		data.put("wxShareVo",wxShareVo);
		data.put("wxShareList",wxShareList);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", data);
		return result;
	}
	
	/**
	 * 修改状态
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/updateState", method = RequestMethod.POST)
	@ResponseBody
	public Result updateState(Integer id) {
		Result result = new Result();
		try{
			if(id == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取信息失败");
				return result;
			}
			GiftSendVo giftSendVo = this.giftSendService.getById(id);
			if(giftSendVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "根据获取信息失败，请稍后再试或者联系管理员！");
				return result;
			}
			if(1 == giftSendVo.getState().intValue()){//已下单
				giftSendVo.setState(2);//已处理
			}else if(2 == giftSendVo.getState().intValue()){//已处理
				giftSendVo.setState(3);//已发货
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "不能再进行操作！");
				return result;
			}
			giftSendVo.setUpdateTime(new Date());
			Boolean flag = this.giftSendService.edit(giftSendVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("接口：/mem/giftSend/updateState，修改状态失败"+e.getMessage());
			result.setError(ResultCode.CODE_STATE_500, "操作失败，请稍后再试或者联系管理员！");
		}
		return result;
	}
	
	
}
