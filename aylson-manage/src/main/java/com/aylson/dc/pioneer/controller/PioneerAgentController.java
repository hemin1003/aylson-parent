package com.aylson.dc.pioneer.controller;

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
import com.aylson.dc.pioneer.search.PioneerAgentSearch;
import com.aylson.dc.pioneer.service.PioneerAgentService;
import com.aylson.dc.pioneer.vo.PioneerAgentVo;
import com.aylson.utils.StringUtil;

/**
 * 开拓者代理商资料管理
 * @author wwx
 * @since  2016-09
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/pioneer/pioneerAgent")
public class PioneerAgentController extends BaseController {
	
	@Autowired
	private PioneerAgentService pioneerAgentService;     //开拓者代理商资料服务
	
	
	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex(String flag) {
		this.request.setAttribute("flag", flag);
		return "/jsp/pioneer/admin/pioneerAgent/index";
	}	
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(PioneerAgentSearch pioneerAgentSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			String flag = request.getParameter("flag");
			if("joining".equals(flag)){
				//pioneerAgentSearch.setStatusMerge("s4");
			}else if("joined".equals(flag)){
				pioneerAgentSearch.setStatusMerge("s3");
			}
			pioneerAgentSearch.setIsPage(true);
			List<PioneerAgentVo> list = this.pioneerAgentService.getList(pioneerAgentSearch);
			result.setTotal(this.pioneerAgentService.getRowCount(pioneerAgentSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据条件获取列表信息
	 * @param pioneerAgentSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<PioneerAgentVo> getList(PioneerAgentSearch pioneerAgentSearch) {
		List<PioneerAgentVo> list = this.pioneerAgentService.getList(pioneerAgentSearch);
		return list;
	}
	
	/**
	 * 后台-审核页面
	 * @param pioneerAgentVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toVerify", method = RequestMethod.GET)
	public String toVerify(Integer id) {
		if(id != null){
			PioneerAgentVo pioneerAgentVo = this.pioneerAgentService.getById(id);
			this.request.setAttribute("pioneerAgentVo",pioneerAgentVo);
		}
		return "/jsp/pioneer/admin/pioneerAgent/verify";
	}	
	
	/**
	 * 后台-审核
	 * @param pioneerAgentVo
	 * @return
	 */
	@RequestMapping(value = "/admin/verify", method = RequestMethod.POST)
	@ResponseBody
	public Result verify(PioneerAgentVo pioneerAgentVo) {
		Result result = new Result();
		try{
			result  = this.pioneerAgentService.verify(pioneerAgentVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 后台-签约
	 * @param pioneerAgentVo
	 * @return
	 */
	@RequestMapping(value = "/admin/sign", method = RequestMethod.POST)
	@ResponseBody
	public Result sign(PioneerAgentVo pioneerAgentVo) {
		Result result = new Result();
		try{
			result  = this.pioneerAgentService.sign(pioneerAgentVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 后台-审核页面
	 * @param pioneerAgentVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toOpenShop", method = RequestMethod.GET)
	public String toOpenShop(Integer id) {
		if(id != null){
			PioneerAgentVo pioneerAgentVo = this.pioneerAgentService.getById(id);
			this.request.setAttribute("pioneerAgentVo",pioneerAgentVo);
		}
		return "/jsp/pioneer/admin/pioneerAgent/openShop";
	}	
	
	/**
	 * 后台-审核
	 * @param pioneerAgentVo
	 * @return
	 */
	@RequestMapping(value = "/admin/openShop", method = RequestMethod.POST)
	@ResponseBody
	public Result openShop(PioneerAgentVo pioneerAgentVo) {
		Result result = new Result();
		try{
			result  = this.pioneerAgentService.openShop(pioneerAgentVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 查看
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/toView", method = RequestMethod.GET)
	public String toView(Integer id) {
		if(id != null){
			PioneerAgentVo pioneerAgentVo = this.pioneerAgentService.getById(id);
			if(pioneerAgentVo != null && StringUtil.isNotEmpty(pioneerAgentVo.getShopImg())){
				String[] shopImgArray = pioneerAgentVo.getShopImg().split(";");
				List<String> shopImgList = new ArrayList<String>();
				for(int i=0; i<shopImgArray.length; i++){
					if(StringUtil.isNotEmpty(shopImgArray[i])){
						shopImgList.add(shopImgArray[i]);
					}
				}
				pioneerAgentVo.setShopImgList(shopImgList);
			}
			this.request.setAttribute("pioneerAgentVo",pioneerAgentVo);
		}
		return "/jsp/pioneer/admin/pioneerAgent/view";
	}
	
	
}
