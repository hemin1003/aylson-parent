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
import com.aylson.dc.base.GeneralConstant.IntegralConfigType;
import com.aylson.dc.sys.search.IntegralConfigSearch;
import com.aylson.dc.sys.service.IntegralConfigService;
import com.aylson.dc.sys.vo.IntegralConfigVo;

/**
 * 积分规则配置管理
 * @author wwx
 * @since  2016-08
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/sys/integralConfig")
public class IntegralConfigController extends BaseController {
	
	@Autowired
	private IntegralConfigService integralConfigService;     //菜单服务
	
	
	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		//处理数据
		List<IntegralConfigVo> listAll = this.getList(null);
		IntegralConfigVo integralConfigVo_1 = null;
		IntegralConfigVo integralConfigVo_2 = null;
		IntegralConfigVo integralConfigVo_3 = null;
		IntegralConfigVo integralConfigVo_4 = null;
		List<IntegralConfigVo> list_5 = new ArrayList<IntegralConfigVo>();
		List<IntegralConfigVo> list_6 = new ArrayList<IntegralConfigVo>();
		IntegralConfigVo integralConfigVo_7 = null;
		IntegralConfigVo integralConfigVo_8 = null;
		IntegralConfigVo integralConfigVo_9 = null;
		IntegralConfigVo integralConfigVo_10 = null;
		IntegralConfigVo integralConfigVo_13 = null;
		IntegralConfigVo integralConfigVo_14 = null;
		IntegralConfigVo integralConfigVo_15 = null;
		IntegralConfigVo integralConfigVo_16 = null;
		IntegralConfigVo integralConfigVo_17 = null;
		IntegralConfigVo integralConfigVo_18 = null;
		IntegralConfigVo integralConfigVo_19 = null;
		List<IntegralConfigVo> list_20 = new ArrayList<IntegralConfigVo>();
		IntegralConfigVo integralConfigVo_21 = null;
		IntegralConfigVo integralConfigVo_22 = null;
		IntegralConfigVo integralConfigVo_23 = null;
		IntegralConfigVo integralConfigVo_24 = null;
		List<IntegralConfigVo> list_25 = new ArrayList<IntegralConfigVo>();
		if(listAll != null && listAll.size() > 0){
			for(IntegralConfigVo temp : listAll){
				if(IntegralConfigType.D_REGISTER_TO_REFERRAL == temp.getType().intValue()){
					integralConfigVo_1 = temp;
				}else if(IntegralConfigType.D_REGISTER_TO_REFERRAL_PARENT == temp.getType().intValue()){
					integralConfigVo_2 = temp;
				}else if(IntegralConfigType.D_SUBMIT == temp.getType().intValue()){
					integralConfigVo_3 = temp;
				}else if(IntegralConfigType.D_SUBMIT_TO_REFERRAL == temp.getType().intValue()){
					integralConfigVo_4 = temp;
				}else if(IntegralConfigType.D_SETTLE == temp.getType().intValue()){
					list_5.add(temp);
				}else if(IntegralConfigType.D_SETTLE_TO_REFERRAL == temp.getType().intValue()){
					list_6.add(temp);
				}else if(IntegralConfigType.W_REGISTER_TO_REFERRAL == temp.getType().intValue()){
					integralConfigVo_7 = temp;
				}else if(IntegralConfigType.W_REGISTER_TO_REFERRAL_PARENT == temp.getType().intValue()){
					integralConfigVo_8 = temp;
				}else if(IntegralConfigType.W_SUBMIT == temp.getType().intValue()){
					integralConfigVo_9 = temp;
				}else if(IntegralConfigType.W_SUBMIT_TO_REFERRAL == temp.getType().intValue()){
					integralConfigVo_10 = temp;
				}else if(IntegralConfigType.P_REGISTER_TO_REFERRAL == temp.getType().intValue()){
					integralConfigVo_13 = temp;
				}else if(IntegralConfigType.P_REGISTER_TO_REFERRAL_PARENT == temp.getType().intValue()){
					integralConfigVo_14 = temp;
				}else if(IntegralConfigType.P_SUBMITCLIENTINFO == temp.getType().intValue()){
					integralConfigVo_15 = temp;
				}else if(IntegralConfigType.P_SUBMITCLIENTINFO_TO_REFERRAL == temp.getType().intValue()){
					integralConfigVo_16 = temp;
				}else if(IntegralConfigType.P_SUBMITAGENTINFO == temp.getType().intValue()){
					integralConfigVo_17 = temp;
				}else if(IntegralConfigType.P_SUBMITAGENTINFO_TO_REFERRAL == temp.getType().intValue()){
					integralConfigVo_18 = temp;
				}else if(IntegralConfigType.P_SIGN_TO_REFERRAL == temp.getType().intValue()){
					integralConfigVo_19 = temp;
				}else if(IntegralConfigType.P_OPEN_TO_REFERRAL == temp.getType().intValue()){
					list_20.add(temp);
				}else if(IntegralConfigType.P_REBATE_TO_REFERRAL == temp.getType().intValue()){
					integralConfigVo_21 = temp;
				}else if(IntegralConfigType.P_SIGN_NOTSINGLE_TO_REFERRAL == temp.getType().intValue()){
					integralConfigVo_22 = temp;
				}else if(IntegralConfigType.P_SIGN_TO_REFERRAL_PARENT == temp.getType().intValue()){
					integralConfigVo_23 = temp;
				}else if(IntegralConfigType.P_OPEN_TO_REFERRAL_PARENT == temp.getType().intValue()){
					integralConfigVo_24 = temp;
				}else if(IntegralConfigType.P_OPEN_NOTSINGLE_TO_REFERRAL == temp.getType().intValue()){
					list_25.add(temp);
				}
			}
			this.request.setAttribute("integralConfigVo_1", integralConfigVo_1);
			this.request.setAttribute("integralConfigVo_2", integralConfigVo_2);
			this.request.setAttribute("integralConfigVo_3", integralConfigVo_3);
			this.request.setAttribute("integralConfigVo_4", integralConfigVo_4);
			this.request.setAttribute("list_5", list_5);
			this.request.setAttribute("list_6", list_6);
			this.request.setAttribute("integralConfigVo_7", integralConfigVo_7);
			this.request.setAttribute("integralConfigVo_8", integralConfigVo_8);
			this.request.setAttribute("integralConfigVo_9", integralConfigVo_9);
			this.request.setAttribute("integralConfigVo_10", integralConfigVo_10);
			this.request.setAttribute("integralConfigVo_13", integralConfigVo_13);
			this.request.setAttribute("integralConfigVo_14", integralConfigVo_14);
			this.request.setAttribute("integralConfigVo_15", integralConfigVo_15);
			this.request.setAttribute("integralConfigVo_16", integralConfigVo_16);
			this.request.setAttribute("integralConfigVo_17", integralConfigVo_17);
			this.request.setAttribute("integralConfigVo_18", integralConfigVo_18);
			this.request.setAttribute("integralConfigVo_19", integralConfigVo_19);
			this.request.setAttribute("list_20", list_20);
			this.request.setAttribute("integralConfigVo_21", integralConfigVo_21);
			this.request.setAttribute("integralConfigVo_22", integralConfigVo_22);
			this.request.setAttribute("integralConfigVo_23", integralConfigVo_23);
			this.request.setAttribute("integralConfigVo_24", integralConfigVo_24);
			this.request.setAttribute("list_25", list_25);
		}
		return "/jsp/sys/admin/integralConfig/index";
	}	
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(IntegralConfigSearch integralConfigSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			integralConfigSearch.setIsPage(true);
			List<IntegralConfigVo> list = this.integralConfigService.getList(integralConfigSearch);
			result.setTotal(this.integralConfigService.getRowCount(integralConfigSearch));
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
	public List<IntegralConfigVo> getList(IntegralConfigSearch integralConfigSearch) {
		List<IntegralConfigVo> list = this.integralConfigService.getList(integralConfigSearch);
		return list;
	}
	
	/**
	 * 后台-添加页面
	 * @param integralConfigVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(IntegralConfigVo integralConfigVo) {
		this.request.setAttribute("integralConfigVo",integralConfigVo);
		return "/jsp/sys/admin/integralConfig/add";
	}	
	
	/**
	 * 后台-添加保存
	 * @param integralConfigVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(IntegralConfigVo integralConfigVo) {
		Result result = new Result();
		try{
			Boolean flag = this.integralConfigService.add(integralConfigVo);
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
			IntegralConfigVo integralConfigVo = this.integralConfigService.getById(id);
			this.request.setAttribute("integralConfigVo",integralConfigVo);
		}
		return "/jsp/sys/admin/integralConfig/add";
	}	
	
	/**
	 * 后台-编辑保存
	 * @param integralConfigVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(Integer bigType) {
		Result result = new Result();
		Boolean flag = true;
		if(bigType == null || bigType.intValue() < 0 || bigType.intValue() > 2){
			result.setError(ResultCode.CODE_STATE_4006, "配置大类不详");
			return result;
		}
		try {
			String[] integralArray = request.getParameterValues("integral");
			String[] typeArray = request.getParameterValues("type");
			String[] rangeBeginArray = request.getParameterValues("rangeBegin");
			String[] rangeEndArray = request.getParameterValues("rangeEnd");
			String[] rateArray = request.getParameterValues("rate");
			if(integralArray != null && integralArray.length > 0){
				List<IntegralConfigVo> list = new ArrayList<IntegralConfigVo>();
				for(int i=0; i<integralArray.length; i++){
					IntegralConfigVo integralConfigVo = new IntegralConfigVo();
					integralConfigVo.setIntegral(Integer.parseInt(integralArray[i]));
					integralConfigVo.setType(Integer.parseInt(typeArray[i]));
					integralConfigVo.setRangeBegin(Integer.parseInt(rangeBeginArray[i]));
					integralConfigVo.setRangeEnd(Integer.parseInt(rangeEndArray[i]));
					integralConfigVo.setRate(Float.parseFloat(rateArray[i]));
					list.add(integralConfigVo);
				}
				//删除旧的
				IntegralConfigSearch integralConfigSearch = new IntegralConfigSearch();
				integralConfigSearch.setBigType(bigType);
//				integralConfigSearch.setIsIntegralLevelConfig(false);
				this.integralConfigService.delete(integralConfigSearch);
				flag = this.integralConfigService.batchAdd(list);
			}
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
			Boolean flag = this.integralConfigService.deleteById(id);
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
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public Result test(Integer type, Float amount){
		Result result = new Result();
		Integer integral = this.integralConfigService.getIntegral(type, amount);
		result.setData(integral);
		return result;
	}
	
	@RequestMapping(value = "/admin/toIntegralLevelConfig", method = RequestMethod.GET)
	public String toIntegralLevelConfig() {
		//返回数据
		List<IntegralConfigVo> integralLevelConfigList = new ArrayList<IntegralConfigVo>();
		IntegralConfigVo clientLimit = null;
		//处理数据
		IntegralConfigSearch integralConfigSearch = new IntegralConfigSearch();
		integralConfigSearch.setBigType(3);
		List<IntegralConfigVo> list = this.integralConfigService.getList(integralConfigSearch);
		if(list != null && list.size() > 0){
			for(IntegralConfigVo temp : list){
				if(IntegralConfigType.INTEGRAL_LEVEL == temp.getType().intValue()){
					integralLevelConfigList.add(temp);
				}else if(IntegralConfigType.CLIENTINFO_LIMIT == temp.getType().intValue()){
					clientLimit = temp;
				}
			}
		}
		this.request.setAttribute("configList", integralLevelConfigList);
		this.request.setAttribute("clientLimit", clientLimit);
		return "/jsp/sys/admin/integralConfig/integralLevelConfig";
	}	
	
	/**
	 * 后台-编辑保存
	 * @param integralConfigVo
	 * @return
	 */
	@RequestMapping(value = "/admin/integralLevelConfig", method = RequestMethod.POST)
	@ResponseBody
	public Result integralLevelConfig() {
		Result result = new Result();
		Boolean flag = true;
		try {
			String[] integralArray = request.getParameterValues("integral");
			String[] typeArray = request.getParameterValues("type");
			String[] rangeBeginArray = request.getParameterValues("rangeBegin");
			String[] rangeEndArray = request.getParameterValues("rangeEnd");
			String[] rateArray = request.getParameterValues("rate");
			if(integralArray != null && integralArray.length > 0){
				List<IntegralConfigVo> list = new ArrayList<IntegralConfigVo>();
				for(int i=0; i<integralArray.length; i++){
					IntegralConfigVo integralConfigVo = new IntegralConfigVo();
					integralConfigVo.setIntegral(Integer.parseInt(integralArray[i]));
					integralConfigVo.setType(Integer.parseInt(typeArray[i]));
					integralConfigVo.setRangeBegin(Integer.parseInt(rangeBeginArray[i]));
					integralConfigVo.setRangeEnd(Integer.parseInt(rangeEndArray[i]));
					integralConfigVo.setRate(Float.parseFloat(rateArray[i]));
					list.add(integralConfigVo);
				}
				//删除旧的
				IntegralConfigSearch integralConfigSearch = new IntegralConfigSearch();
				integralConfigSearch.setBigType(3);
				this.integralConfigService.delete(integralConfigSearch);
				flag = this.integralConfigService.batchAdd(list);
			}
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
	
}
