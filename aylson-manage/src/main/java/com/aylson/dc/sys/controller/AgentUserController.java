package com.aylson.dc.sys.controller;

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
import com.aylson.dc.sys.search.AgentUserSearch;
import com.aylson.dc.sys.search.DictionarySearch;
import com.aylson.dc.sys.service.AgentUserService;
import com.aylson.dc.sys.service.DictionaryService;
import com.aylson.dc.sys.service.UserService;
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
	private UserService userService;               //用户服务
	@Autowired
	private DictionaryService dictionaryService;   //数据字典服务
	
	
	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/sys/admin/agentUser/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(AgentUserSearch agentUserSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			agentUserSearch.setIsPage(true);
			List<AgentUserVo> list = this.agentUserService.getList(agentUserSearch);
			result.setTotal(this.agentUserService.getRowCount(agentUserSearch));
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
	public List<AgentUserVo> getList(AgentUserSearch agentUserSearch) {
		List<AgentUserVo> list = this.agentUserService.getList(agentUserSearch);
		return list;
	}

	/**
	 * 后台-添加页面
	 * @param agentUserVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(AgentUserVo agentUserVo) {
		DictionarySearch dictionarySearch = new DictionarySearch();
		dictionarySearch.setDicType("ProductCategory_bigType");
		List<DictionaryVo> productTypesList = this.dictionaryService.getList(dictionarySearch);//获取产品类型列表
		agentUserVo.setProductTypesList(productTypesList);
		this.request.setAttribute("agentUserVo", agentUserVo);
		this.request.setAttribute("flag", "add");
		return "/jsp/sys/admin/agentUser/add";
	}

	/**
	 * 后台-添加保存
	 * @param agentUserVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	public Result add(AgentUserVo agentUserVo) {
		Result result = new Result();
		try {
			String[] productArray = this.request.getParameterValues("product");
			String[] productIdArray = this.request.getParameterValues("productId");
			if(productArray != null && productArray.length > 0){
				StringBuffer products = new StringBuffer();
				StringBuffer productIds = new StringBuffer();
				for(int i=0; i<productArray.length; i++){
					if(i == productArray.length-1){
						products.append(productArray[i]);
						productIds.append(productIdArray[i]);
					}else{
						products.append(productArray[i]).append(",");
						productIds.append(productIdArray[i]).append(",");
					}
				}
				agentUserVo.setProducts(products.toString());
				agentUserVo.setProductIds(productIds.toString());
			}
			result = this.agentUserService.addAgentUser(agentUserVo);
		} catch (Exception e) {
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
		if (id != null) {
			DictionarySearch dictionarySearch = new DictionarySearch();
			dictionarySearch.setDicType("ProductCategory_bigType");
			List<DictionaryVo> productTypesList = this.dictionaryService.getList(dictionarySearch);//获取产品类型列表
			AgentUserVo agentUserVo = this.agentUserService.getById(id);
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
			this.request.setAttribute("agentUserVo", agentUserVo);
		}
		this.request.setAttribute("flag", "edit");
		return "/jsp/sys/admin/agentUser/add";
	}
	
	/**
	 * 后台-编辑保存
	 * @param agentUserVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	public Result update(AgentUserVo agentUserVo) {
		Result result = new Result();
		try {
			String[] productArray = this.request.getParameterValues("product");
			String[] productIdArray = this.request.getParameterValues("productId");
			if(productArray != null && productArray.length > 0){
				StringBuffer products = new StringBuffer();
				StringBuffer productIds = new StringBuffer();
				for(int i=0; i<productArray.length; i++){
					if(i == productArray.length-1){
						products.append(productArray[i]);
						productIds.append(productIdArray[i]);
					}else{
						products.append(productArray[i]).append(",");
						productIds.append(productIdArray[i]).append(",");
					}
				}
				agentUserVo.setProducts(products.toString());
				agentUserVo.setProductIds(productIds.toString());
			}
			result = this.agentUserService.editAgentUser(agentUserVo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	
}
