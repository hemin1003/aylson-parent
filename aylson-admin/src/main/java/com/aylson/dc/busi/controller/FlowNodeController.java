package com.aylson.dc.busi.controller;

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
import com.aylson.dc.busi.search.FlowNodeSearch;
import com.aylson.dc.busi.service.FlowNodeService;
import com.aylson.dc.busi.vo.FlowNodeVo;

/**
 * 问题帮助管理
 * @author wwx
 * @since  2017-03
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/busi/flowNode")
public class FlowNodeController extends BaseController {
	
	@Autowired
	private FlowNodeService flowNodeService;     //帮助服务
	
	
	/**
	 * 获取列表（分页）
	 * @param flowNodeSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPage", method = RequestMethod.GET)
	public Result getPage(FlowNodeSearch flowNodeSearch) {
		Result result = new Result();
		Page<FlowNodeVo> page = this.flowNodeService.getPage(flowNodeSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", page);
		return result;
	}
	
	/**
	 * 获取列表（不分页）
	 * @param flowNodeSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public Result getList(FlowNodeSearch flowNodeSearch) {
		Result result = new Result();
		List<FlowNodeVo> list = this.flowNodeService.getList(flowNodeSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", list);
		return result;
	}
	
	
}
