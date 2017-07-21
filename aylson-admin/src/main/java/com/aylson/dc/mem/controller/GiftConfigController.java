package com.aylson.dc.mem.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.easyui.EasyuiDataGridJson;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.base.GeneralConstant.MatchObjType;
import com.aylson.dc.base.GeneralConstant.MemberType;
import com.aylson.dc.mem.search.GiftConfigSearch;
import com.aylson.dc.mem.service.GiftConfigService;
import com.aylson.dc.mem.vo.GiftConfigVo;
import com.aylson.utils.StringUtil;

/**
 * 积分兑换礼品配置管理
 * @author wwx
 * @since  2016-08
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/mem/giftConfig")
public class GiftConfigController extends BaseController {
	
	@Autowired
	private GiftConfigService giftConfigService;     //发布管理服务
	
	
	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		this.request.setAttribute("memberTypeMap", MemberType.MemberTypeMap);
		return "/jsp/mem/admin/giftConfig/index";
	}	
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(GiftConfigSearch giftConfigSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			giftConfigSearch.setIsPage(true);
			List<GiftConfigVo> list = this.giftConfigService.getList(giftConfigSearch);
			result.setTotal(this.giftConfigService.getRowCount(giftConfigSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据条件获取列表信息
	 * @param giftConfigSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<GiftConfigVo> getList(GiftConfigSearch giftConfigSearch) {
		List<GiftConfigVo> list = this.giftConfigService.getList(giftConfigSearch);
		return list;
	}
	
	/**
	 * 后台-添加页面
	 * @param giftConfigVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(GiftConfigVo giftConfigVo) {
		this.request.setAttribute("giftConfigVo",giftConfigVo);
		this.request.setAttribute("matchObjType", MatchObjType.MatchObjTypeMap);
		return "/jsp/mem/admin/giftConfig/add";
	}	
	
	/**
	 * 后台-添加保存
	 * @param giftConfigVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(GiftConfigVo giftConfigVo) {
		Result result = new Result();
		try{
			//处理参数
			String[] paramTitle = request.getParameterValues("paramTitle");
			String[] paramValue = request.getParameterValues("paramValue");
			if(paramTitle != null && paramTitle.length > 0){
				StringBuffer sb = new StringBuffer();
				for(int i=0; i<paramTitle.length; i++){
					sb.append(paramTitle[i]);//参数名
					sb.append("<:>");//内容分割符
					sb.append(paramValue[i]);
					sb.append("<;>");//参数分隔符
				}
				giftConfigVo.setParameters(sb.toString());
			}
			//处理图片轮播
			String[] imgNavigationItem = request.getParameterValues("imgNavigationItem");
			if(imgNavigationItem != null && imgNavigationItem.length > 0){
				StringBuffer sb = new StringBuffer();
				for(int i=0; i<imgNavigationItem.length; i++){
					sb.append(imgNavigationItem[i]);//图片地址
					sb.append("<;>");//内容分割符
				}
				giftConfigVo.setImgNavigation(sb.toString());
			}
			Boolean flag = this.giftConfigService.add(giftConfigVo);
//			Boolean flag = true;
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
		if(id!=null){
			GiftConfigVo giftConfigVo = this.giftConfigService.getById(id);
			if(giftConfigVo != null){
				//参数
				if(StringUtil.isNotEmpty(giftConfigVo.getParameters())){
					String[] params = giftConfigVo.getParameters().split("<;>");
					if(params != null && params.length > 0){
						Map<String,String> parameterMap = new HashMap<String,String>();
						for(int i=0; i<params.length; i++){
							System.out.println(i);
							System.out.println(params[i]+"===");
							String[] param = params[i].split("<:>");
							System.out.println(param[0]+"===");
							System.out.println(param[1]+"===");
							parameterMap.put(param[0], param[1]);
						}
						giftConfigVo.setParameterMap(parameterMap);
					}
				}
				//处理礼品导航图片
				if(StringUtil.isNotEmpty(giftConfigVo.getImgNavigation())){
					String[] imgNavigationItem = giftConfigVo.getImgNavigation().split("<;>");
					if(imgNavigationItem != null && imgNavigationItem.length > 0){
						List<String> imgNavigationAddress = new ArrayList<String>();
						for(int i=0; i<imgNavigationItem.length; i++){
							imgNavigationAddress.add(imgNavigationItem[i]);
						}
						giftConfigVo.setImgNavigationAddress(imgNavigationAddress);
					}
				}
			}
			this.request.setAttribute("giftConfigVo",giftConfigVo);
			this.request.setAttribute("matchObjType", MatchObjType.MatchObjTypeMap);
		}
		return "/jsp/mem/admin/giftConfig/add";
	}	
	
	/**
	 * 后台-编辑保存
	 * @param giftConfigVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(GiftConfigVo giftConfigVo) {
		Result result = new Result();
		try {
			//处理参数
			String[] paramTitle = request.getParameterValues("paramTitle");
			String[] paramValue = request.getParameterValues("paramValue");
			if(paramTitle != null && paramTitle.length > 0){
				StringBuffer sb = new StringBuffer();
				for(int i=0; i<paramTitle.length; i++){
					sb.append(paramTitle[i]);//参数名
					sb.append("<:>");//内容分割符
					sb.append(paramValue[i]);
					sb.append("<;>");//参数分隔符
				}
				giftConfigVo.setParameters(sb.toString());
			}
			//处理图片轮播
			String[] imgNavigationItem = request.getParameterValues("imgNavigationItem");
			if(imgNavigationItem != null && imgNavigationItem.length > 0){
				StringBuffer sb = new StringBuffer();
				for(int i=0; i<imgNavigationItem.length; i++){
					sb.append(imgNavigationItem[i]);//图片地址
					sb.append("<;>");//内容分割符
				}
				giftConfigVo.setImgNavigation(sb.toString());
			}
			Boolean flag = this.giftConfigService.edit(giftConfigVo);
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
	
	@RequestMapping(value = "/admin/changeStatus", method = RequestMethod.POST)
	@ResponseBody
	public Result giftConfig(GiftConfigVo giftConfigVo) {
		Result result = new Result();
		try {
			if(giftConfigVo.getStatus() == null){
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
				return result;
			}
			Boolean flag = this.giftConfigService.edit(giftConfigVo);
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
			Boolean flag = this.giftConfigService.deleteById(id);
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
	
	@RequestMapping(value = "/admin/toView", method = RequestMethod.GET)
	public String toView(Integer id) {
		if(id!=null){
			GiftConfigVo giftConfigVo = this.giftConfigService.getById(id);
			//参数
			if(StringUtil.isNotEmpty(giftConfigVo.getParameters())){
				String[] params = giftConfigVo.getParameters().split("<;>");
				if(params != null && params.length > 0){
					Map<String,String> parameterMap = new HashMap<String,String>();
					for(int i=0; i<params.length; i++){
						System.out.println(i);
						System.out.println(params[i]+"===");
						String[] param = params[i].split("<:>");
						System.out.println(param[0]+"===");
						System.out.println(param[1]+"===");
						parameterMap.put(param[0], param[1]);
					}
					giftConfigVo.setParameterMap(parameterMap);
				}
			}
			//处理礼品导航图片
			if(StringUtil.isNotEmpty(giftConfigVo.getImgNavigation())){
				String[] imgNavigationItem = giftConfigVo.getImgNavigation().split("<;>");
				if(imgNavigationItem != null && imgNavigationItem.length > 0){
					List<String> imgNavigationAddress = new ArrayList<String>();
					for(int i=0; i<imgNavigationItem.length; i++){
						imgNavigationAddress.add(imgNavigationItem[i]);
					}
					giftConfigVo.setImgNavigationAddress(imgNavigationAddress);
				}
			}
			this.request.setAttribute("giftConfigVo",giftConfigVo);
			this.request.setAttribute("memberTypeMap", MemberType.MemberTypeMap);
		}
		return "/jsp/mem/admin/giftConfig/view";
	}
	
	
}
