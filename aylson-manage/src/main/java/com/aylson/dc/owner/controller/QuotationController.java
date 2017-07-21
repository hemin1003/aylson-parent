package com.aylson.dc.owner.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.base.OwnerGeneralConstant.DesignType;
import com.aylson.dc.owner.service.AppointmentService;
import com.aylson.dc.owner.service.QuotationService;
import com.aylson.dc.owner.vo.AppointmentVo;
import com.aylson.dc.owner.vo.QuotationDetailDWVo;
import com.aylson.dc.owner.vo.QuotationDetailSRVo;
import com.aylson.dc.owner.vo.QuotationVo;
import com.aylson.utils.DateUtil;


/**
 * 反馈管理
 * @author wwx
 * @since  2016-08
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/owner/quotation")
public class QuotationController extends BaseController {
	
	@Autowired
	private QuotationService quotationService;                      //订货报价单服务
	@Autowired
	private AppointmentService appointmentService;                  //在线预约服务
	
	/**
	 * 订货报价单页面
	 */
	@RequestMapping(value = "/admin/toAddQuotation", method = RequestMethod.GET)
	public String toAddQuotation(Integer designId,Integer designType, Model model) {
		//获取设计信息表的明细信息
		QuotationVo quotationVo = this.quotationService.toAddQuotation(designId, designType);
		model.addAttribute("quotationVo", quotationVo);
		if(DesignType.DOOR == designType || DesignType.WINDOW == designType){
			return "/jsp/owner/admin/quotation/addDW";
		}else if(DesignType.SUMROOM == designType){
			return "/jsp/owner/admin/quotation/addSR";
		}
		return null;
	}	
	
	/**
	 * 保存订货报价单
	 * @param quotationVo
	 * @param model
	 * @param quotationDetailDWVoListJson
	 * @param quotationDetailSRVo
	 * @return
	 */
	@RequestMapping(value = "/admin/addQuotation", method = RequestMethod.POST)
	@ResponseBody
	public Result addQuotation(QuotationVo quotationVo,String quotationDetailDWVoListJson,QuotationDetailSRVo quotationDetailSRVo) {
		Result result = new Result();
		try {
			result = this.quotationService.addQuotation(quotationVo, quotationDetailDWVoListJson);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 查看订货单
	 * @param id
	 * @param designType
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/toViewQuotation", method = RequestMethod.GET)
	public String toViewQuotation(Integer designId,Integer designType,Integer appointId, Model model) {
		String pageUrl = null;
		if(designId != null && designType != null){
			QuotationVo quotationVo  = this.quotationService.getQuotationVo(designId, designType);
			if(quotationVo != null && quotationVo.getDeliveryTime() != null){
				quotationVo.setDeliveryTimeStr(DateUtil.format(quotationVo.getDeliveryTime(), true));
			}
			model.addAttribute("quotationVo", quotationVo);
		}
		AppointmentVo appointmentVo = null;
		if(appointId != null){
			appointmentVo = this.appointmentService.getById(appointId);
		}
		model.addAttribute("appointmentVo", appointmentVo);
		if(DesignType.SUMROOM == designType.intValue()){
			pageUrl =  "/jsp/owner/admin/quotation/viewSR";
		}else{
			pageUrl =  "/jsp/owner/admin/quotation/viewDW";
		}
		return pageUrl;
	}	
	
	/**
	 * 修改报价单-页面
	 * @param designId
	 * @param designType
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/toEditQuotation", method = RequestMethod.GET)
	public String toEditQuotation(Integer designId,Integer designType, Model model) {
		String pageUrl = null;
		if(designId != null && designType != null){
			QuotationVo quotationVo  = this.quotationService.getQuotationVo(designId, designType);
			if(quotationVo != null && quotationVo.getDeliveryTime() != null){
				quotationVo.setDeliveryTimeStr(DateUtil.format(quotationVo.getDeliveryTime(), true));
			}
			model.addAttribute("quotationVo", quotationVo);
		}
		if(DesignType.SUMROOM == designType.intValue()){
			pageUrl =  "/jsp/owner/admin/quotation/editSR";
		}else{
			pageUrl =  "/jsp/owner/admin/quotation/editDW";
		}
		return pageUrl;
	}
	
	/**
	 * 保存报价订货单信息
	 * @param quotationVo
	 * @param quotationDetailDWVoListJson
	 * @return
	 */
	@RequestMapping(value = "/admin/editQuotation", method = RequestMethod.POST)
	@ResponseBody
	public Result editQuotation(QuotationVo quotationVo,String quotationDetailDWVoListJson) {
		Result result = new Result();
		try {
			result = this.quotationService.editQuotation(quotationVo, quotationDetailDWVoListJson);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 代理商报价-页面
	 * @param designId
	 * @param designType
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/toAgentQuote", method = RequestMethod.GET)
	public String toAgentQuote(Integer designId,Integer designType, Model model) {
		String pageUrl = null;
		if(designId != null && designType != null){
			QuotationVo quotationVo  = this.quotationService.getQuotationVo(designId, designType);
			model.addAttribute("quotationVo", quotationVo);
		}
		if(DesignType.SUMROOM == designType.intValue()){
			pageUrl =  "/jsp/owner/admin/quotation/agentSR";
		}else{
			pageUrl =  "/jsp/owner/admin/quotation/agentDW";
		}
		return pageUrl;
	}	
	
	/**
	 * 
	 * @param quotationVo
	 * @return
	 */
	@RequestMapping(value = "/admin/agentQuote", method = RequestMethod.POST)
	@ResponseBody
	public Result agentQuote(QuotationVo quotationVo,String quotationDetailDWVoListJson) {
		Result result = null;
		try {
			result = this.quotationService.agentQuote(quotationVo, quotationDetailDWVoListJson);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 导出订货单
	 * @param designId
	 * @param designType
	 * @param appointId
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/admin/exportQuotation", method = RequestMethod.GET)
	@ResponseBody
	public void exportApply(Integer designId,Integer designType,Integer appointId,HttpServletRequest request, HttpServletResponse response) {
		try{
			
			//封装excel内容
			QuotationVo quotationVo = null;
			AppointmentVo appointmentVo = null;
			if(designId != null && designType != null){
				quotationVo  = this.quotationService.getQuotationVo(designId, designType);
				if(quotationVo != null && quotationVo.getDeliveryTime() != null){
					quotationVo.setDeliveryTimeStr(DateUtil.format(quotationVo.getDeliveryTime(), true));
				}
			}
			if(appointId != null){
				appointmentVo = this.appointmentService.getById(appointId);
			}
			HSSFWorkbook workBook = null;
			if(DesignType.SUMROOM == designType.intValue()){
				workBook = this.getSRWorkBook(quotationVo, appointmentVo);
			}else{
				workBook = this.getDWWorkBook(quotationVo, appointmentVo);
			}
			//响应头
			response.setContentType("application/vnd.ms-excel");    
			String excelName = null;
			excelName = new String(workBook.getSheetName(0).getBytes("GB2312"),"ISO-8859-1");
//			excelName = URLEncoder.encode(workBook.getSheetName(0), "UTF-8");
			response.setHeader("Content-disposition", "attachment;filename="+ excelName+".xls"); 
			response.setCharacterEncoding("utf-8");
			OutputStream out = response.getOutputStream();
			//将输出流写入一个文件
			workBook.write(out);
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 门窗订货单导出内容
	 * @param quotationVo
	 * @param appointmentVo
	 * @return
	 */
	private HSSFWorkbook getDWWorkBook(QuotationVo quotationVo,AppointmentVo appointmentVo){
		//一、创建工作簿和所需的行列
		// 创建一个工作簿
		HSSFWorkbook workBook = new HSSFWorkbook();
		// 创建一个工作表
		HSSFSheet sheet = workBook.createSheet("艾臣安全门窗订货单");
		// 行、列情况
		int fixed_rows = 11;                                               //固定行
		int change_rows = quotationVo.getDetailDWVoList().size();          //变化行
		int total_rows = fixed_rows + change_rows;                         //总行数
		int total_cells = 17;  
		// 创建表格的行x列   total_rows*total_cells
		List<HSSFRow> allRows = new ArrayList<HSSFRow>();                 //所有行
		for(int row_index=0; row_index<total_rows; row_index++){
			HSSFRow row = sheet.createRow(row_index);
			for(int cell_index=0; cell_index<total_cells; cell_index++){
				HSSFCell cell = row.createCell(cell_index);
				//cell.setCellValue(cell_index);
			}
			allRows.add(row);
		}
		//二、为工作簿的行列放入数据
		//数据内容处理
		if(allRows.size() > 0){
			for(int row_index = 0; row_index < allRows.size(); row_index++){
				HSSFRow row = allRows.get(row_index);
				if(row_index == 0){//处理第1行数据
					row.getCell(0).setCellValue("艾臣安全门窗订货单");
				}else if(row_index == 1){//处理第2行数据
					StringBuffer cell_1 = new StringBuffer("");
					cell_1.append("地址：广东省佛山市顺德区陈村镇白陈路石洲路段雄盈物流C3栋5楼");
					cell_1.append("                ");
					cell_1.append("客户姓名：").append(appointmentVo.getName());
					cell_1.append("                ");
					cell_1.append("工程地址：").append(appointmentVo.getAddress());
					cell_1.append("                ");
					cell_1.append("下单日期：").append(quotationVo.getOrderTimeStr());
					row.getCell(0).setCellValue(cell_1.toString());
				}else if(row_index == 2){//处理第3行数据
					StringBuffer cell_1 = new StringBuffer("");
					cell_1.append("销售部门：").append(quotationVo.getSalesDep());
					cell_1.append("                            ");
					cell_1.append("区域负责人：").append(quotationVo.getAreaLeader());
					cell_1.append("                            ");
					cell_1.append("专卖店联系电话：").append(quotationVo.getShopPhone());
					cell_1.append("                            ");
					cell_1.append("专卖店联系人：").append(quotationVo.getShopContacter());
					cell_1.append("                            ");
					cell_1.append("订单编号：").append(quotationVo.getOrderNo());
					row.getCell(0).setCellValue(cell_1.toString());
				}else if(row_index == 3){//处理第4行数据
					row.getCell(0).setCellValue("序号");
					row.getCell(1).setCellValue("生产编号");
					row.getCell(2).setCellValue("产品名称");
					row.getCell(3).setCellValue("洞口尺寸（mm）");
					row.getCell(5).setCellValue("产品尺寸（mm）");
					row.getCell(7).setCellValue("墙厚（mm）");
					row.getCell(8).setCellValue("颜色");
					row.getCell(10).setCellValue("玻璃/百叶");
					row.getCell(11).setCellValue("下轨道");
					row.getCell(12).setCellValue("执手/拉手颜色");
					row.getCell(13).setCellValue("数量（套）");
					row.getCell(14).setCellValue("面积（㎡）");
					row.getCell(15).setCellValue("单价（元）");
					row.getCell(16).setCellValue("金额（元）");
				}else if(row_index == 4){//处理第5行数据
					row.getCell(3).setCellValue("宽");
					row.getCell(4).setCellValue("高");
					row.getCell(5).setCellValue("宽");
					row.getCell(6).setCellValue("高");
					row.getCell(8).setCellValue("内");
					row.getCell(9).setCellValue("外");
				}else if(row_index == total_rows-6){//处理最后第6行数据
					row.getCell(0).setCellValue("合计金额："+quotationVo.getOrderAmount());
				}else if(row_index == total_rows-5){//处理最后第5行数据
					row.getCell(0).setCellValue("折后金额："+quotationVo.getRealAmount());
				}else if(row_index == total_rows-4){//处理最后第4行数据
					row.getCell(0).setCellValue("预收定金："+quotationVo.getDeposit());
					row.getCell(6).setCellValue("货物出厂前应付金额："+quotationVo.getRemainAmount());
					row.getCell(12).setCellValue("付款方式："+quotationVo.getPayMode());
				}else if(row_index == total_rows-3){//处理最后第3行数据
					row.getCell(0).setCellValue("备注");
					row.getCell(1).setCellValue(quotationVo.getRemark());
				}else if(row_index == total_rows-2){//处理最后第2行数据
					String desc = "1 门框门洞测量方法：上下、左右量最窄处，墙厚量最厚处，门扇计算方法：上下比门洞小4.5CM，左右比门洞小7CM\r\n"
							+ "2 铝框门门洞测量方法参照型材决定\r\n"
							+ "3 付款方式：客户在下单确认后付50%以上，厂家发货前付清全部货款，未付清货款前，产品所有权归厂方所有\r\n"
							+ "4 门页在弯曲2.5mm以内属正常范围，门套线与门页门框稍有色差属正常，内容填写清楚并核实，如因错误造成损失，公司不负责任";
					row.getCell(0).setCellValue(desc);
				}else if(row_index == total_rows-1){//处理最后一行数据
					StringBuffer cell_1 = new StringBuffer("");
					cell_1.append("交货日期：").append(quotationVo.getDeliveryTimeStr()==null?"":quotationVo.getDeliveryTimeStr());
					cell_1.append("                            ");
					cell_1.append("制单：").append(quotationVo.getOriginator()==null?"":quotationVo.getOriginator());
					cell_1.append("                            ");
					cell_1.append("审核：").append(quotationVo.getAuditer()==null?"":quotationVo.getAuditer());
					cell_1.append("                            ");
					cell_1.append("确认：").append(quotationVo.getConfirmer()==null?"":quotationVo.getConfirmer());
					cell_1.append("                            ");
					cell_1.append("财务审核：").append(quotationVo.getFinancer()==null?"":quotationVo.getFinancer());
					row.getCell(0).setCellValue(cell_1.toString());
				}else{//动态数据行
					QuotationDetailDWVo dw = quotationVo.getDetailDWVoList().get(row_index-5);//获取订货单信息,减去表头前5行
					row.getCell(0).setCellValue(dw.getSeq()==null?"":dw.getSeq()+"");
					row.getCell(1).setCellValue(dw.getProductNo()==null?"":dw.getProductNo());
					row.getCell(2).setCellValue(dw.getProductName()==null?"":dw.getProductName());
					row.getCell(3).setCellValue(dw.getHoleSizeW()==null?"":dw.getHoleSizeW()+"");
					row.getCell(4).setCellValue(dw.getHoleSizeH()==null?"":dw.getHoleSizeH()+"");
					row.getCell(5).setCellValue(dw.getProductSizeW()==null?"":dw.getProductSizeW()+"");
					row.getCell(6).setCellValue(dw.getProductSizeH()==null?"":dw.getProductSizeH()+"");
					row.getCell(7).setCellValue(dw.getWallThick()==null?"":dw.getWallThick()+"");
					row.getCell(8).setCellValue(dw.getColorIn()==null?"":dw.getColorIn());
					row.getCell(9).setCellValue(dw.getColorOut()==null?"":dw.getColorOut());
					row.getCell(10).setCellValue(dw.getGlass()==null?"":dw.getGlass());
					row.getCell(11).setCellValue(dw.getRails()==null?"":dw.getRails());
					row.getCell(12).setCellValue(dw.getHandColor()==null?"":dw.getHandColor());
					row.getCell(13).setCellValue(dw.getPruductNum()==null?"":dw.getPruductNum()+"");
					row.getCell(14).setCellValue(dw.getAreas()==null?"":dw.getAreas()+"");
					row.getCell(15).setCellValue(dw.getUnitPrice()==null?"":dw.getUnitPrice()+"");
					row.getCell(16).setCellValue(dw.getAmount()==null?"":dw.getAmount()+"");
				}
				
			}
		}
		//三、格式化工作簿
		//Sheet页自适应页面大小
		PrintSetup ps = sheet.getPrintSetup();
		sheet.setAutobreaks(true);
		ps.setFitHeight((short)2);
		ps.setFitWidth((short)2);
		//调整列宽以使用内容长度
		sheet.autoSizeColumn((short)11);
		//合并处理：合并的首行，末行，首列，末列
		//第1行
		CellRangeAddress cra=new CellRangeAddress(0, 0, 0, total_cells-1); 
		sheet.addMergedRegion(cra);
		//第2行
		CellRangeAddress cra2=new CellRangeAddress(1, 1, 0, total_cells-1); 
		sheet.addMergedRegion(cra2);
		//第3行
		CellRangeAddress cra3=new CellRangeAddress(2, 2, 0, total_cells-1); 
		sheet.addMergedRegion(cra3);
		//第4、5行
		CellRangeAddress cra4_5_1=new CellRangeAddress(3, 4, 0, 0);    
		sheet.addMergedRegion(cra4_5_1);
		CellRangeAddress cra4_5_2=new CellRangeAddress(3, 4, 1, 1);    
		sheet.addMergedRegion(cra4_5_2);
		CellRangeAddress cra4_5_3=new CellRangeAddress(3, 4, 2, 2);    
		sheet.addMergedRegion(cra4_5_3);
		CellRangeAddress cra4_5_4=new CellRangeAddress(3, 3, 3, 4);    
		sheet.addMergedRegion(cra4_5_4);
		CellRangeAddress cra4_5_5=new CellRangeAddress(3, 3, 5, 6);    
		sheet.addMergedRegion(cra4_5_5);
		CellRangeAddress cra4_5_6=new CellRangeAddress(3, 4, 7, 7);    
		sheet.addMergedRegion(cra4_5_6);
		CellRangeAddress cra4_5_7=new CellRangeAddress(3, 3, 8, 9);    
		sheet.addMergedRegion(cra4_5_7);
		CellRangeAddress cra4_5_8=new CellRangeAddress(3, 4, 10, 10);    
		sheet.addMergedRegion(cra4_5_8);
		CellRangeAddress cra4_5_9=new CellRangeAddress(3, 4, 11, 11);    
		sheet.addMergedRegion(cra4_5_9);
		CellRangeAddress cra4_5_10=new CellRangeAddress(3, 4, 12, 12);    
		sheet.addMergedRegion(cra4_5_10);
		CellRangeAddress cra4_5_11=new CellRangeAddress(3, 4, 13, 13);    
		sheet.addMergedRegion(cra4_5_11);
		CellRangeAddress cra4_5_12=new CellRangeAddress(3, 4, 14, 14);    
		sheet.addMergedRegion(cra4_5_12);
		CellRangeAddress cra4_5_13=new CellRangeAddress(3, 4, 15, 15);    
		sheet.addMergedRegion(cra4_5_13);
		CellRangeAddress cra4_5_14=new CellRangeAddress(3, 4, 16, 16);    
//		sheet.addMergedRegion(cra4_5_14);
//		CellRangeAddress cra4_5_15=new CellRangeAddress(3, 4, total_cells-1, total_cells-1);    
//		sheet.addMergedRegion(cra4_5_15);
		//倒数第6行
		CellRangeAddress cra10=new CellRangeAddress(total_rows-6, total_rows-6, 0, total_cells-1); 
		sheet.addMergedRegion(cra10);
		//倒数第5行
		CellRangeAddress cra11=new CellRangeAddress(total_rows-5, total_rows-5, 0, total_cells-1); 
		sheet.addMergedRegion(cra11);
		//倒数第4行
		CellRangeAddress cra12=new CellRangeAddress(total_rows-4, total_rows-4, 0, 5); 
		sheet.addMergedRegion(cra12);
		CellRangeAddress cra13=new CellRangeAddress(total_rows-4, total_rows-4, 6, 11); 
		sheet.addMergedRegion(cra13);
		CellRangeAddress cra14=new CellRangeAddress(total_rows-4, total_rows-4, 12, total_cells-1); 
		sheet.addMergedRegion(cra14);
		//倒数第3行
		CellRangeAddress cra15=new CellRangeAddress(total_rows-3, total_rows-3, 1, total_cells-1); 
		sheet.addMergedRegion(cra15);
		//倒数第2行
		CellRangeAddress cra16=new CellRangeAddress(total_rows-2, total_rows-2, 0, total_cells-1); 
		sheet.addMergedRegion(cra16);
		//倒数第1行
		CellRangeAddress cra17=new CellRangeAddress(total_rows-1, total_rows-1, 0, total_cells-1); 
		sheet.addMergedRegion(cra17);
		//单元格字体
		Font font_row_1 = workBook.createFont();     //第1行
		font_row_1.setFontHeightInPoints((short)22);
		font_row_1.setBoldweight(Font.BOLDWEIGHT_BOLD);
		Font font_row_4_5 = workBook.createFont();   //第4-5行
		font_row_4_5.setFontHeightInPoints((short)11);
		font_row_4_5.setBoldweight(Font.BOLDWEIGHT_BOLD);
		Font font_size11 = workBook.createFont();    //字体大小
		font_size11.setFontHeightInPoints((short)11);
		Font font_base = workBook.createFont();
		font_base.setFontHeightInPoints((short)11);
		//单元格格式
		CellStyle base_css = workBook.createCellStyle();
		base_css.setWrapText(true);   //设置允许换行
		//base_css.setFont(font_base);
		base_css.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		//base_css.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		base_css.setBorderTop(CellStyle.BORDER_THIN);
		base_css.setTopBorderColor(IndexedColors.BLACK.getIndex());
		base_css.setBorderRight(CellStyle.BORDER_THIN);
		base_css.setRightBorderColor(IndexedColors.BLACK.getIndex());
		base_css.setBorderBottom(CellStyle.BORDER_THIN);
		base_css.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		base_css.setBorderLeft(CellStyle.BORDER_THIN);
		base_css.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		
		CellStyle css_row_1 = workBook.createCellStyle();
		//css_row_1.cloneStyleFrom(base_css);
		css_row_1.setFont(font_row_1);
		css_row_1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		css_row_1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		CellStyle css_row_2_3 = workBook.createCellStyle();
		css_row_2_3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		CellStyle css_row_4_5 = workBook.createCellStyle();
		css_row_4_5.cloneStyleFrom(base_css);
		css_row_4_5.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		css_row_4_5.setFont(font_row_4_5);
		
		CellStyle css_1 = workBook.createCellStyle();
		css_1.cloneStyleFrom(base_css);
		css_1.setFont(font_size11);
		
		CellStyle css_row_6_16 = workBook.createCellStyle();
		css_row_6_16.cloneStyleFrom(base_css);
		css_row_6_16.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//样式处理
		if(allRows.size() > 0){
			for(int row_index = 0; row_index < allRows.size(); row_index++){
				HSSFRow row = allRows.get(row_index);
				for(int cell_index=0; cell_index<total_cells; cell_index++){
					row.getCell(cell_index).setCellStyle(base_css);
				}
				if(row_index == 0){//处理第1行数据
					row.setHeightInPoints(40);//行高
					for(int cell_index=0; cell_index<total_cells; cell_index++){
						row.getCell(cell_index).setCellStyle(css_row_1);
					}
				}else if(row_index == 1){//处理第2行数据
					row.setHeightInPoints(24);
					for(int cell_index=0; cell_index<total_cells; cell_index++){
						row.getCell(cell_index).setCellStyle(css_row_2_3);
					}
				}else if(row_index == 2){//处理第3行数据
					for(int cell_index=0; cell_index<total_cells; cell_index++){
						row.getCell(cell_index).setCellStyle(css_row_2_3);
					}
					row.setHeightInPoints(18);
				}else if(row_index == 3){//处理第4行数据
					row.setHeightInPoints(28);
					for(int cell_index=0; cell_index<total_cells; cell_index++){
						row.getCell(cell_index).setCellStyle(css_row_4_5);
					}
				}else if(row_index == 4){//处理第5行数据
					row.setHeightInPoints(20);
					for(int cell_index=0; cell_index<total_cells; cell_index++){
						row.getCell(cell_index).setCellStyle(css_row_4_5);
					}
				}else if(row_index == total_rows-6){//处理最后第6行数据
					row.getCell(0).setCellStyle(css_1);
					row.setHeightInPoints(23);
				}else if(row_index == total_rows-5){//处理最后第5行数据
					row.getCell(0).setCellStyle(css_1);
					row.setHeightInPoints(23);
				}else if(row_index == total_rows-4){//处理最后第4行数据
					row.getCell(0).setCellStyle(css_1);
					row.getCell(6).setCellStyle(css_1);
					row.getCell(12).setCellStyle(css_1);
					row.setHeightInPoints(23);
				}else if(row_index == total_rows-3){//处理最后第3行数据
					row.getCell(0).setCellStyle(css_1);
					row.setHeightInPoints(60);
				}else if(row_index == total_rows-2){//处理最后第2行数据
					row.setHeightInPoints(56);
				}else if(row_index == total_rows-1){//处理最后一行数据
					row.getCell(0).setCellStyle(css_1);
					row.setHeightInPoints(23);
				}else{//动态数据行
					row.setHeightInPoints(25);
					for(int cell_index=0; cell_index<total_cells; cell_index++){
						row.getCell(cell_index).setCellStyle(css_row_6_16);
					}
				}
			}
		}
		return workBook;
		
	}
	
	/**
	 * 阳光房订货单导出内容
	 * @param quotationVo
	 * @param appointmentVo
	 * @return
	 */
	private HSSFWorkbook getSRWorkBook(QuotationVo quotationVo,AppointmentVo appointmentVo){
		//一、创建工作簿和所需的行列
		// 创建一个工作簿
		HSSFWorkbook workBook = new HSSFWorkbook();
		// 创建一个工作表
		HSSFSheet sheet = workBook.createSheet("艾臣阳光房订货单");
		// 行、列情况
		int total_rows = 22;                                              //总行数
		int total_cells = 16;                                             //每行列数
		// 创建表格的行x列   total_rows*total_cells
		List<HSSFRow> allRows = new ArrayList<HSSFRow>();                 //所有行
		for(int row_index=0; row_index<total_rows; row_index++){
			HSSFRow row = sheet.createRow(row_index);
			for(int cell_index=0; cell_index<total_cells; cell_index++){
				HSSFCell cell = row.createCell(cell_index);
				//cell.setCellValue(cell_index);
			}
			allRows.add(row);
		}
		//二、为工作簿的行列放入数据
		//数据内容处理
		if(allRows.size() > 0){
			for(int row_index = 0; row_index < allRows.size(); row_index++){
				HSSFRow row = allRows.get(row_index);
				if(row_index == 0){//处理第1行数据
					row.getCell(0).setCellValue("艾臣阳光房订货单");
				}else if(row_index == 1){//处理第2行数据
					StringBuffer cell_1 = new StringBuffer("");
					cell_1.append("地址：广东省佛山市顺德区陈村镇白陈路石洲路段雄盈物流C3栋5楼");
					cell_1.append("         ");
					cell_1.append("客户姓名：").append(appointmentVo.getName());
					cell_1.append("         ");
					cell_1.append("工程地址：").append(appointmentVo.getAddress());
					cell_1.append("         ");
					cell_1.append("下单日期：").append(quotationVo.getOrderTimeStr());
					row.getCell(0).setCellValue(cell_1.toString());
				}else if(row_index == 2){//处理第3行数据
					StringBuffer cell_1 = new StringBuffer("");
					cell_1.append("销售部门：").append(quotationVo.getSalesDep());
					cell_1.append("         ");
					cell_1.append("区域负责人：").append(quotationVo.getAreaLeader());
					cell_1.append("         ");
					cell_1.append("专卖店联系电话：").append(quotationVo.getShopPhone());
					cell_1.append("         ");
					cell_1.append("专卖店联系人：").append(quotationVo.getShopContacter());
					cell_1.append("         ");
					cell_1.append("订单编号：").append(quotationVo.getOrderNo());
					row.getCell(0).setCellValue(cell_1.toString());
				}else if(row_index == 3){//处理第4行数据
					row.getCell(0).setCellValue("序号");
					row.getCell(1).setCellValue("生产编号");
					row.getCell(2).setCellValue("类别");
					row.getCell(3).setCellValue("结构");
					row.getCell(4).setCellValue("颜色");
					//row.getCell(5).setCellValue("");
					row.getCell(6).setCellValue("玻璃/百叶");
					row.getCell(7).setCellValue("长（MM）");
					row.getCell(8).setCellValue("宽（MM）");
					row.getCell(9).setCellValue("柱高（MM）");
					row.getCell(10).setCellValue("执手/拉手颜色");
					row.getCell(11).setCellValue("数量");
					row.getCell(12).setCellValue("面积");
					row.getCell(13).setCellValue("单位");
					row.getCell(14).setCellValue("单价（元）");
					row.getCell(15).setCellValue("金额（元）");
				}else if(row_index == 4){//处理第5行数据
					row.getCell(4).setCellValue("内");
					row.getCell(5).setCellValue("外");
				}else if(row_index == 16){//处理第17行数据
					row.getCell(15).setCellValue(quotationVo.getOrderAmount());
				}else if(row_index == 17){//处理第18行数据
					row.getCell(0).setCellValue("合计金额："+quotationVo.getOrderAmount());
				}else if(row_index == 18){//处理第19行数据
					row.getCell(0).setCellValue("预收定金："+quotationVo.getDeposit());
					row.getCell(6).setCellValue("货物出厂前应付金额："+quotationVo.getRemainAmount());
					row.getCell(12).setCellValue("付款方式："+quotationVo.getPayMode());
				}else if(row_index == 19){//处理第20行数据
					row.getCell(0).setCellValue("备注");
					row.getCell(1).setCellValue(quotationVo.getRemark());
				}else if(row_index == 20){//处理第21行数据
					String desc = "1 门框门洞测量方法：上下、左右量最窄处，墙厚量最厚处，门扇计算方法：上下比门洞小4.5CM，左右比门洞小7CM\r\n"
							+ "2 铝框门门洞测量方法参照型材决定\r\n"
							+ "3 付款方式：客户在下单确认后付50%以上，厂家发货前付清全部货款，未付清货款前，产品所有权归厂方所有\r\n"
							+ "4 门页在弯曲2.5mm以内属正常范围，门套线与门页门框稍有色差属正常，内容填写清楚并核实，如因错误造成损失，公司不负责任";
					row.getCell(0).setCellValue(desc);
				}else if(row_index == 21){//处理第22行数据
					StringBuffer cell_1 = new StringBuffer("");
					cell_1.append("交货日期：").append(quotationVo.getDeliveryTimeStr()==null?"":quotationVo.getDeliveryTimeStr());
					cell_1.append("                            ");
					cell_1.append("制单：").append(quotationVo.getOriginator()==null?"":quotationVo.getOriginator());
					cell_1.append("                            ");
					cell_1.append("审核：").append(quotationVo.getAuditer()==null?"":quotationVo.getAuditer());
					cell_1.append("                            ");
					cell_1.append("确认：").append(quotationVo.getConfirmer()==null?"":quotationVo.getConfirmer());
					cell_1.append("                            ");
					cell_1.append("财务审核：").append(quotationVo.getFinancer()==null?"":quotationVo.getFinancer());
					row.getCell(0).setCellValue(cell_1.toString());
				}else{
					QuotationDetailSRVo sr = quotationVo.getDetailSRVoList().get(row_index-5);//获取订货单信息,减去表头前5行
					row.getCell(0).setCellValue(sr.getSeq());                                 //序号
					row.getCell(1).setCellValue(sr.getProductNo()==null?"":sr.getProductNo());//生产编号
					String categoryName = "";
					switch (sr.getCategory()) {
					case 1:
						categoryName = "钢结构";
						break;
					case 2:
						categoryName = "立柱";
						break;
					case 3:
						categoryName = "屋顶部分";
						break;
					case 4:
						categoryName = "门窗";
						break;
					default:
						categoryName = "";
						break;
					}
					row.getCell(2).setCellValue(categoryName);
					String structureName = "";
					String unitName = "";
					switch (sr.getStructure()) {
					case 1:
						structureName = "钢结构";
						unitName = "㎡";
						break;
					case 2:
						structureName = "主立柱";
						unitName = "根";
						break;
					case 3:
						structureName = "靠墙部分";
						unitName = "根";
						break;
					case 4:
						structureName = "次立柱";
						unitName = "根";
						break;
					case 5:
						structureName = "三角面积";
						unitName = "㎡";
						break;
					case 6:
						structureName = "屋顶";
						unitName = "㎡";
						break;
					case 7:
						structureName = "水槽";
						unitName = "m";
						break;
					case 8:
						structureName = "水槽堵塞网";
						unitName = "m";
						break;
					case 9:
						structureName = "清风双悬推拉门";
						unitName = "㎡";
						break;
					case 10:
						structureName = "95手摇平开窗-固定部分";
						unitName = "㎡";
						break;
					case 11:
						structureName = "95手摇平开窗-扇部分";
						unitName = "㎡";
						break;
					default:
						structureName = "";
						break;
					}
					row.getCell(3).setCellValue(structureName);
					row.getCell(4).setCellValue(sr.getColorIn()==null?"":sr.getColorIn());
					row.getCell(5).setCellValue(sr.getColorOut()==null?"":sr.getColorIn());
					row.getCell(6).setCellValue(sr.getGlass()==null?"":sr.getGlass()+"");
					row.getCell(7).setCellValue(sr.getLength()==null?"":sr.getLength()+"");
					row.getCell(8).setCellValue(sr.getWidth()==null?"":sr.getWidth()+"");
					row.getCell(9).setCellValue(sr.getHeight()==null?"":sr.getHeight()+"");
					row.getCell(10).setCellValue(sr.getHandColor()==null?"":sr.getHandColor()+"");
					row.getCell(11).setCellValue(sr.getPruductNum()==null?"":sr.getPruductNum()+"");
					row.getCell(12).setCellValue(sr.getAreas()==null?"":sr.getAreas()+"");
					row.getCell(13).setCellValue(unitName);
					row.getCell(14).setCellValue(sr.getUnitPrice()==null?"":sr.getUnitPrice()+"");
					row.getCell(15).setCellValue(sr.getAmount()==null?"":sr.getAmount()+"");
				}
			}
		}
		//三、格式化工作簿
		//Sheet页自适应页面大小
		PrintSetup ps = sheet.getPrintSetup();
		sheet.setAutobreaks(true);
		ps.setFitHeight((short)11);
		ps.setFitWidth((short)11);
		//调整列宽以使用内容长度
		sheet.autoSizeColumn((short)30);
		//合并处理：合并的首行，末行，首列，末列
		//第1行
		CellRangeAddress cra=new CellRangeAddress(0, 0, 0, total_cells-1); 
		sheet.addMergedRegion(cra);
		//第2行
		CellRangeAddress cra2=new CellRangeAddress(1, 1, 0, total_cells-1); 
		sheet.addMergedRegion(cra2);
		//第3行
		CellRangeAddress cra3=new CellRangeAddress(2, 2, 0, total_cells-1); 
		sheet.addMergedRegion(cra3);
		//第4、5行
		CellRangeAddress cra4_5_1=new CellRangeAddress(3, 4, 0, 0);    
		sheet.addMergedRegion(cra4_5_1);
		CellRangeAddress cra4_5_2=new CellRangeAddress(3, 4, 1, 1);    
		sheet.addMergedRegion(cra4_5_2);
		CellRangeAddress cra4_5_3=new CellRangeAddress(3, 4, 2, 2);    
		sheet.addMergedRegion(cra4_5_3);
		CellRangeAddress cra4_5_4=new CellRangeAddress(3, 4, 3, 3);    
		sheet.addMergedRegion(cra4_5_4);
		CellRangeAddress cra4_5_5=new CellRangeAddress(3, 4, 6, 6);    
		sheet.addMergedRegion(cra4_5_5);
		CellRangeAddress cra4_5_6=new CellRangeAddress(3, 4, 7, 7);    
		sheet.addMergedRegion(cra4_5_6);
		CellRangeAddress cra4_5_7=new CellRangeAddress(3, 4, 8, 8);    
		sheet.addMergedRegion(cra4_5_7);
		CellRangeAddress cra4_5_8=new CellRangeAddress(3, 4, 9, 9);    
		sheet.addMergedRegion(cra4_5_8);
		CellRangeAddress cra4_5_9=new CellRangeAddress(3, 4, 10, 10);    
		sheet.addMergedRegion(cra4_5_9);
		CellRangeAddress cra4_5_10=new CellRangeAddress(3, 4, 11, 11);    
		sheet.addMergedRegion(cra4_5_10);
		CellRangeAddress cra4_5_11=new CellRangeAddress(3, 4, 12, 12);    
		sheet.addMergedRegion(cra4_5_11);
		CellRangeAddress cra4_5_12=new CellRangeAddress(3, 4, 13, 13);    
		sheet.addMergedRegion(cra4_5_12);
		CellRangeAddress cra4_5_13=new CellRangeAddress(3, 4, 14, 14);    
		sheet.addMergedRegion(cra4_5_13);
		CellRangeAddress cra4_5_14=new CellRangeAddress(3, 4, 15, 15);    
		sheet.addMergedRegion(cra4_5_14);
		CellRangeAddress cra4_5_15=new CellRangeAddress(3, 3, 4, 5);    
		sheet.addMergedRegion(cra4_5_15);
		//第6-9行
		CellRangeAddress cra7_9_1=new CellRangeAddress(6, 8, 0, 0);    
		sheet.addMergedRegion(cra7_9_1);
		CellRangeAddress cra7_9_2=new CellRangeAddress(6, 8, 1, 1);    
		sheet.addMergedRegion(cra7_9_2);
		CellRangeAddress cra7_9_3=new CellRangeAddress(6, 8, 2, 2);    
		sheet.addMergedRegion(cra7_9_3);
		//第10-13行
		CellRangeAddress cra10_13_1=new CellRangeAddress(9, 12, 0, 0);    
		sheet.addMergedRegion(cra10_13_1);
		CellRangeAddress cra10_13_2=new CellRangeAddress(9, 12, 1, 1);    
		sheet.addMergedRegion(cra10_13_2);
		CellRangeAddress cra10_13_3=new CellRangeAddress(9, 12, 2, 2);    
		sheet.addMergedRegion(cra10_13_3);
		//第14-16行
		CellRangeAddress cra14_16_1=new CellRangeAddress(13, 15, 0, 0);    
		sheet.addMergedRegion(cra14_16_1);
		CellRangeAddress cra14_16_2=new CellRangeAddress(13, 15, 1, 1);    
		sheet.addMergedRegion(cra14_16_2);
		CellRangeAddress cra14_16_3=new CellRangeAddress(13, 15, 2, 2);    
		sheet.addMergedRegion(cra14_16_3);
		//第17行
		CellRangeAddress cra17_1=new CellRangeAddress(16, 16, 0, 14);    
		sheet.addMergedRegion(cra17_1);
		//第18行
		CellRangeAddress cra18_1=new CellRangeAddress(17, 17, 0, total_cells-1);    
		sheet.addMergedRegion(cra18_1);
		//第19行
		CellRangeAddress cra19_1=new CellRangeAddress(18, 18, 0, 5);    
		sheet.addMergedRegion(cra19_1);
		CellRangeAddress cra19_2=new CellRangeAddress(18, 18, 6, 11);    
		sheet.addMergedRegion(cra19_2);
		CellRangeAddress cra19_3=new CellRangeAddress(18, 18, 12, total_cells-1);    
		sheet.addMergedRegion(cra19_3);
		//第20行
		CellRangeAddress cra20_1=new CellRangeAddress(19, 19, 1, total_cells-1);    
		sheet.addMergedRegion(cra20_1);
		//第21行
		CellRangeAddress cra21_1=new CellRangeAddress(20, 20, 0, total_cells-1);    
		sheet.addMergedRegion(cra21_1);
		//第22行
		CellRangeAddress cra22_1=new CellRangeAddress(21, 21, 0, total_cells-1);    
		sheet.addMergedRegion(cra22_1);
		//单元格字体
		Font font_row_1 = workBook.createFont();     //第1行
		font_row_1.setFontHeightInPoints((short)22);
		font_row_1.setBoldweight(Font.BOLDWEIGHT_BOLD);
		Font font_row_4_5 = workBook.createFont();   //第4-5行
		font_row_4_5.setFontHeightInPoints((short)10);
		font_row_4_5.setBoldweight(Font.BOLDWEIGHT_BOLD);
		Font font_size11 = workBook.createFont();    //字体大小
		font_size11.setFontHeightInPoints((short)11);
		//单元格样式
		CellStyle base_css = workBook.createCellStyle();
		base_css.setWrapText(true);   //设置允许换行
		//base_css.setFont(font_base);
		base_css.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		//base_css.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		base_css.setBorderTop(CellStyle.BORDER_THIN);
		base_css.setTopBorderColor(IndexedColors.BLACK.getIndex());
		base_css.setBorderRight(CellStyle.BORDER_THIN);
		base_css.setRightBorderColor(IndexedColors.BLACK.getIndex());
		base_css.setBorderBottom(CellStyle.BORDER_THIN);
		base_css.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		base_css.setBorderLeft(CellStyle.BORDER_THIN);
		base_css.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		
		CellStyle css_row_1 = workBook.createCellStyle();
		//css_row_1.cloneStyleFrom(base_css);
		css_row_1.setFont(font_row_1);
		css_row_1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		css_row_1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		CellStyle css_row_2_3 = workBook.createCellStyle();
		css_row_2_3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		CellStyle css_row_4_5 = workBook.createCellStyle();
		css_row_4_5.cloneStyleFrom(base_css);
		css_row_4_5.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		css_row_4_5.setFont(font_row_4_5);
		
		CellStyle css_1 = workBook.createCellStyle();
		css_1.cloneStyleFrom(base_css);
		css_1.setFont(font_size11);
		
		CellStyle css_row_6_16 = workBook.createCellStyle();
		css_row_6_16.cloneStyleFrom(base_css);
		css_row_6_16.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//样式处理
		if(allRows.size() > 0){
			for(int row_index = 0; row_index < allRows.size(); row_index++){
				HSSFRow row = allRows.get(row_index);
				for(int cell_index=0; cell_index<total_cells; cell_index++){
					row.getCell(cell_index).setCellStyle(base_css);
				}
				if(row_index == 0){//处理第1行样式
					row.setHeightInPoints(40);//行高
					for(int cell_index=0; cell_index<total_cells; cell_index++){
						row.getCell(cell_index).setCellStyle(css_row_1);
					}
				}else if(row_index == 1){//处理第2行样式
					row.setHeightInPoints(24);
					for(int cell_index=0; cell_index<total_cells; cell_index++){
						row.getCell(cell_index).setCellStyle(css_row_2_3);
					}
				}else if(row_index == 2){//处理第3行样式
					for(int cell_index=0; cell_index<total_cells; cell_index++){
						row.getCell(cell_index).setCellStyle(css_row_2_3);
					}
					row.setHeightInPoints(18);
				}else if(row_index == 3){//处理第4行样式
					row.setHeightInPoints(28);
					for(int cell_index=0; cell_index<total_cells; cell_index++){
						row.getCell(cell_index).setCellStyle(css_row_4_5);
					}
				}else if(row_index == 4){//处理第5行样式
					row.setHeightInPoints(20);
					for(int cell_index=0; cell_index<total_cells; cell_index++){
						row.getCell(cell_index).setCellStyle(css_row_4_5);
					}
				}else if(row_index == 16){//处理第17行样式
					//row.getCell(0).setCellStyle(css_1);
					row.setHeightInPoints(23);
				}else if(row_index == 17){//处理第18行样式
					row.getCell(0).setCellStyle(css_1);
					row.setHeightInPoints(23);
				}else if(row_index == 18){//处理第19行样式
					row.getCell(0).setCellStyle(css_1);
					row.getCell(6).setCellStyle(css_1);
					row.getCell(12).setCellStyle(css_1);
					row.setHeightInPoints(23);
				}else if(row_index == 19){//处理第20行样式
					row.getCell(0).setCellStyle(css_1);
					row.setHeightInPoints(60);
				}else if(row_index == 20){//处理第21行样式
					row.setHeightInPoints(56);
				}else if(row_index == 21){//处理第22行样式
					row.getCell(0).setCellStyle(css_1);
					row.setHeightInPoints(23);
				}else{//动态数据行
					row.setHeightInPoints(25);
					for(int cell_index=0; cell_index<total_cells; cell_index++){
						row.getCell(cell_index).setCellStyle(css_row_6_16);
					}
				}
			}
		}
		
		return workBook;
	}
	
	
}
