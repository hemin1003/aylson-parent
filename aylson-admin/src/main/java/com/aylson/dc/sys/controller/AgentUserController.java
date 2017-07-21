package com.aylson.dc.sys.controller;

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
import com.aylson.dc.sys.search.AgentUserSearch;
import com.aylson.dc.sys.search.DictionarySearch;
import com.aylson.dc.sys.service.AgentUserService;
import com.aylson.dc.sys.service.DictionaryService;
import com.aylson.dc.sys.vo.AgentUserVo;
import com.aylson.dc.sys.vo.DictionaryVo;
import com.aylson.utils.DateUtil;
import com.aylson.utils.StringUtil;

/**
 * 代理商管理
 * @author wwx
 * @since  2016-08
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/sys/agentUser")
public class AgentUserController extends BaseController {
	
	@Autowired
	private AgentUserService agentUserService;     //组织机构用户服务
	@Autowired
	private DictionaryService dictionaryService;   //数据字典服务
	
	
	/**
	 * 获取列表（分页）
	 * @param giftSendDetailSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPage", method = RequestMethod.GET)
	public Result getPage(AgentUserSearch agentUserSearch) {
		Result result = new Result();
		Page<AgentUserVo> page = this.agentUserService.getPage(agentUserSearch);
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
	public Result getList(AgentUserSearch agentUserSearch) {
		Result result = new Result();
		List<AgentUserVo> list = this.agentUserService.getList(agentUserSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", list);
		return result;
	}
	
	/**
	 * 根据id查询
	 * @param giftSendDetailSearch
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
		AgentUserVo agentUserVo = this.agentUserService.getById(id);
		if(agentUserVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取记录信息失败，请稍后再试或者联系管理员！");
			return result;
		}
		DictionarySearch dictionarySearch = new DictionarySearch();
		dictionarySearch.setDicType("ProductCategory_bigType");
		List<DictionaryVo> productTypesList = this.dictionaryService.getList(dictionarySearch);//获取产品类型列表
		if(agentUserVo != null && StringUtil.isNotEmpty(agentUserVo.getProductIds())){
			String[] productIdsArray = agentUserVo.getProductIds().split(",");
			if(productIdsArray != null && productIdsArray.length > 0){
				for(int i=0; i<productIdsArray.length; i++){
					for(DictionaryVo dic : productTypesList){
						int productId = Integer.parseInt(productIdsArray[i]);
						if(dic.getId() == productId){
							dic.setCk(true);
							break;
						}
					}
				}
			}
		}
		if(agentUserVo.getBirthday() != null){
			agentUserVo.setBirthdayStr(DateUtil.format(agentUserVo.getBirthday(), true));
		}
		agentUserVo.setProductTypesList(productTypesList);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", agentUserVo);
		return result;
	}
	
	/**
	 * 新增
	 * @param agentUserVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addAgent", method = RequestMethod.POST)
	public  Result addAgent(AgentUserVo agentUserVo) {
		Result result = new Result();
		try {
			result = this.agentUserService.addAgent(agentUserVo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍后再试或者联系管理员");
		}
		return result;
	}
	
	/**
	 * 修改
	 * @param agentUserVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAgent", method = RequestMethod.POST)
	public  Result updateAgent(AgentUserVo agentUserVo) {
		Result result = new Result();
		try {
			result = this.agentUserService.updateAgent(agentUserVo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍后再试或者联系管理员");
		}
		return result;
	}
		
	
}
