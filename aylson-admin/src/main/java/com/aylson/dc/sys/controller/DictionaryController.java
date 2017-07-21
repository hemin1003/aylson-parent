package com.aylson.dc.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.sys.search.DictionarySearch;
import com.aylson.dc.sys.search.StoreSearch;
import com.aylson.dc.sys.service.DictionaryService;
import com.aylson.dc.sys.vo.DictionaryVo;
import com.aylson.dc.sys.vo.StoreVo;

/**
 * 数据字典管理
 * @author wwx
 * @since  2016-05
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/sys/dictionary")
public class DictionaryController extends BaseController {

	@Autowired
	private DictionaryService dictionaryService;    //数据字典服务

	/**
	 * 后台-数据字典 首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/sys/admin/dic/index";
	}

	@ResponseBody
	@RequestMapping(value = "/gridTree", method = RequestMethod.GET)
	public List<DictionaryVo> gridTree(DictionarySearch dictionarySearch) {
		if(dictionarySearch.getId() == null){
			dictionarySearch.setParentId(0);
		}else{
			dictionarySearch.setParentId(dictionarySearch.getId());
			dictionarySearch.setId(null);
		}
		List<DictionaryVo> list = this.dictionaryService.getList(dictionarySearch);
		for(DictionaryVo mItem: list){
			if(!mItem.getIsLeaf()){
				mItem.setState("closed");
			}else{
				mItem.setState("");
			}
		}
		return list;
	}
	
	/**
	 * 根据条件获取数据字典列表信息
	 * @param dictionarySearch
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<DictionaryVo> getList(DictionarySearch dictionarySearch) {
		List<DictionaryVo> list = this.dictionaryService.getList(dictionarySearch);
		return list;
	}*/
	
	/**
	 * 获取列表（不分页）
	 * @param storeSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public Result getList(DictionarySearch dictionarySearch) {
		Result result = new Result();
		List<DictionaryVo> list = this.dictionaryService.getList(dictionarySearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", list);
		return result;
	}
	
	
	/**
	 * 后台-数据字典 添加页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(DictionaryVo dictionaryVo) {
		this.request.setAttribute("dictionaryVo", dictionaryVo);
		return "/jsp/sys/admin/dic/add";
	}

	/**
	 * 后台-数据字典 添加-保存
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Result add(DictionaryVo dictionaryVo) {
		Result result = new Result();
		result = this.dictionaryService.valid(dictionaryVo);
		if(!result.isSuccess())return result;
		try {
			dictionaryVo.setIsLeaf(true);
			Boolean flag = this.dictionaryService.add(dictionaryVo);
			if(flag){
				DictionaryVo parent = new DictionaryVo();
				parent.setId(dictionaryVo.getParentId());
				parent.setIsLeaf(false);
				flag = this.dictionaryService.edit(parent);
				if(flag){
					result.setOK(ResultCode.CODE_STATE_200, "操作成功");
				}
			}
			if (!flag) {
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}

		return result;
	}
	
	/**
	 * 后台-数据字典 编辑页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toEdit", method = RequestMethod.GET)
	public String toEdit(Integer id) {
		if (id != null) {
			DictionaryVo dictionaryVo = this.dictionaryService.getById(id);
			this.request.setAttribute("dictionaryVo", dictionaryVo);
		}
		return "/jsp/sys/admin/dic/add";
	}
	
	/**
	 * 后台-数据字典 编辑-保存
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Result update(DictionaryVo dictionaryVo) {
		Result result = new Result();
		result = this.dictionaryService.valid(dictionaryVo);
		if(!result.isSuccess())return result;
		try {
			Boolean flag = this.dictionaryService.edit(dictionaryVo);
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
	 * 后台-数据字典 编辑-保存
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAny", method = RequestMethod.POST)
	public Result updateAny(DictionaryVo dictionaryVo) {
		Result result = new Result();
		try {
			Boolean flag = this.dictionaryService.edit(dictionaryVo);
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
