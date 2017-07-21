package com.aylson.dc.mem.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.easyui.EasyuiDataGridJson;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.mem.search.WithdrawalsApplySearch;
import com.aylson.dc.mem.service.WithdrawalsApplyService;
import com.aylson.dc.mem.vo.WithdrawalsApplyVo;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.utils.StringUtil;

/**
 * 提现申请管理
 * @author wwx
 * @since  2016-08
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/mem/withdrawalsApply")
public class WithdrawalsApplyController extends BaseController {
	
	@Autowired
	private WithdrawalsApplyService withdrawalsApplyService;     //提现申请服务
	
	
	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/mem/admin/withdrawalsApply/index";
	}	
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(WithdrawalsApplySearch withdrawalsApplySearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			withdrawalsApplySearch.setIsPage(true);
			List<WithdrawalsApplyVo> list = this.withdrawalsApplyService.getList(withdrawalsApplySearch);
			result.setTotal(this.withdrawalsApplyService.getRowCount(withdrawalsApplySearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据条件获取发布列表信息
	 * @param withdrawalsApplySearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<WithdrawalsApplyVo> getList(WithdrawalsApplySearch withdrawalsApplySearch) {
		List<WithdrawalsApplyVo> list = this.withdrawalsApplyService.getList(withdrawalsApplySearch);
		return list;
	}
	
	/**
	 * 后台-转账页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/toTransfer", method = RequestMethod.GET)
	public String toTransfer(Integer id) {
		if(id!=null){
			WithdrawalsApplyVo withdrawalsApplyVo = this.withdrawalsApplyService.getById(id);
			this.request.setAttribute("withdrawalsApplyVo",withdrawalsApplyVo);
		}
		return "/jsp/mem/admin/withdrawalsApply/transfer";
	}	
	
	/**
	 * 后台-转账保存
	 * @param withdrawalsApplyVo
	 * @return
	 */
	@RequestMapping(value = "/admin/transfer", method = RequestMethod.POST)
	@ResponseBody
	public Result transfer(WithdrawalsApplyVo withdrawalsApplyVo) {
		Result result = new Result();
		try {
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");//缓存信息
			if(sessionInfo != null && sessionInfo.getUser() != null){
				withdrawalsApplyVo.setDealer(sessionInfo.getUser().getUserName());
				withdrawalsApplyVo.setDealerId(sessionInfo.getUser().getId());
				withdrawalsApplyVo.setDealTime(new Date());
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "获取当前操作人信息失败，请稍后再试");
				return result;
			}
			result = this.withdrawalsApplyService.transfer(withdrawalsApplyVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 后台-转账页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/toView", method = RequestMethod.GET)
	public String toView(Integer id) {
		if(id!=null){
			WithdrawalsApplyVo withdrawalsApplyVo = this.withdrawalsApplyService.getById(id);
			if(StringUtil.isNotEmpty(withdrawalsApplyVo.getTransferProof())){
				String[] urls = withdrawalsApplyVo.getTransferProof().split(";");
				List<String> transferProofList = new ArrayList<String>();
				if(urls != null && urls.length > 0){
					for(int i=0; i<urls.length; i++){
						if(i != urls.length-1){
							transferProofList.add(urls[i]);
						}
					}
				}
				this.request.setAttribute("transferProofList",transferProofList);
			}
			this.request.setAttribute("withdrawalsApplyVo",withdrawalsApplyVo);
		}
		return "/jsp/mem/admin/withdrawalsApply/view";
	}	
	
	@RequestMapping(value = "/admin/exportApply", method = RequestMethod.GET)
	@ResponseBody
	public void exportApply(String applyIds,HttpServletRequest request, HttpServletResponse response) {
		try{
			response.setContentType("application/vnd.ms-excel");    
			String excelName = null;
			excelName = URLEncoder.encode("提现申请列表", "UTF-8");
			response.setHeader("Content-disposition", "attachment;filename="+ excelName+".xls"); 
			response.setCharacterEncoding("utf-8");
			OutputStream out = response.getOutputStream();
			if(StringUtil.isNotEmpty(applyIds)){
				WithdrawalsApplySearch search = new WithdrawalsApplySearch();
				search.setIdsArray(applyIds.split(","));
				List<WithdrawalsApplyVo> list = this.getList(search);
				if(list != null && list.size() > 0){
					// 创建一个工作簿
					HSSFWorkbook workBook = new HSSFWorkbook();
					// 创建一个工作表
					HSSFSheet sheet = workBook.createSheet();
					// 设置表格默认列宽度为15个字节  
			        sheet.setDefaultColumnWidth((short)30);  
					HSSFRow row = sheet.createRow(0);
					HSSFCell cell[] = new HSSFCell[5];
					for (int i = 0; i < 5; i++) {
						cell[i] = row.createCell(i);
					}
					cell[0].setCellValue("申请人");
					cell[1].setCellValue("申请人电话");
					cell[2].setCellValue("持卡人");
					cell[3].setCellValue("银行卡号");
					cell[4].setCellValue("所属银行");
					for(int i=0; i<list.size(); i++){
						WithdrawalsApplyVo temp = list.get(i);
						HSSFRow row1 = sheet.createRow(i+1);
						HSSFCell cellData[] = new HSSFCell[5];
						for (int j = 0; j < 5; j++) {
							cellData[j] = row1.createCell(j);
						}
						cellData[0].setCellValue(temp.getApplier());
						cellData[1].setCellValue(temp.getApplierPhone());
						cellData[2].setCellValue(temp.getBankholder());
						cellData[3].setCellValue(temp.getBankNum());
						cellData[4].setCellValue(temp.getBankName());
					}
					workBook.write(out);
					out.flush();
					out.close();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
      
}  
	
	
	
	
	
