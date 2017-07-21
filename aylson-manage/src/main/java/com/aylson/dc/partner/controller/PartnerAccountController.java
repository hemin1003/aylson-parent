package com.aylson.dc.partner.controller;

import java.util.Date;
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
import com.aylson.dc.base.PartnerGeneralConstant.AccountState;
import com.aylson.dc.base.PartnerGeneralConstant.IndustryType;
import com.aylson.dc.base.PartnerGeneralConstant.WalletBillSourceType;
import com.aylson.dc.base.PartnerGeneralConstant.WalletBillType;
import com.aylson.dc.partner.po.PartnerWalletBill;
import com.aylson.dc.partner.search.PartnerAccountSearch;
import com.aylson.dc.partner.search.PartnerWalletBillSearch;
import com.aylson.dc.partner.service.PartnerAccountService;
import com.aylson.dc.partner.service.PartnerWalletBillService;
import com.aylson.dc.partner.vo.PartnerAccountVo;
import com.aylson.dc.partner.vo.PartnerWalletBillVo;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.utils.IHuiYiUtils;

/**
 * 合伙人账号信息管理
 * @author wwx
 * @since  2017-01
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/partner/partnerAccount")
public class PartnerAccountController extends BaseController {
	
	@Autowired
	private PartnerAccountService partnerAccountService;                     //合伙人账号服务
	@Autowired
	private PartnerWalletBillService partnerWalletBillService;               //钱包流水服务
	
	
	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		this.request.setAttribute("industryTypeMap", IndustryType.IndustryTypeMap);
		return "/jsp/partner/admin/partnerAccount/index";
	}	
	
	/**
	 * 获取列表-分页
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(PartnerAccountSearch partnerAccountSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			partnerAccountSearch.setIsPage(true);
			List<PartnerAccountVo> list = this.partnerAccountService.getList(partnerAccountSearch);
			result.setTotal(this.partnerAccountService.getRowCount(partnerAccountSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取列表-不分页
	 * 根据条件获取列表信息
	 * @param partnerAccountSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<PartnerAccountVo> getList(PartnerAccountSearch partnerAccountSearch) {
		List<PartnerAccountVo> list = this.partnerAccountService.getList(partnerAccountSearch);
		return list;
	}
	
	/**
	 * 后台-添加页面
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd() {
		return "/jsp/partner/admin/partnerAccount/add";
	}	
	
	/**
	 * 后台-添加
	 * @param partnerAccountVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(PartnerAccountVo partnerAccountVo) {
		Result result = new Result();
		try{
			Boolean flag = this.partnerAccountService.add(partnerAccountVo);
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
			PartnerAccountVo partnerAccountVo = this.partnerAccountService.getById(id);
			this.request.setAttribute("partnerAccountVo",partnerAccountVo);
		}
		return "/jsp/partner/admin/partnerAccount/add";
	}	
	
	/**
	 * 后台-编辑保存
	 * @param partnerAccountVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(PartnerAccountVo partnerAccountVo) {
		Result result = new Result();
		try {
			Boolean flag = this.partnerAccountService.edit(partnerAccountVo);
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
			Boolean flag = this.partnerAccountService.deleteById(id);
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
	
	/**
	 * 审核合伙人注册申请
	 * @param partnerAccountVo
	 * @return
	 */
	@RequestMapping(value = "/admin/verify", method = RequestMethod.POST)
	@ResponseBody
	public Result verify(Integer id, Boolean isPass) {
		Result result = new Result();
		try {
			if(id == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取账号信息失败！");
				return result;
			}
			if(isPass == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取账号信息状态失败！");
				return result;
			}
			PartnerAccountVo partnerAccountVo = this.partnerAccountService.getById(id);
			if(partnerAccountVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取账号信息失败！");
				return result;
			}
			String smsContent = null;
			if(isPass){
				smsContent = "您在艾臣合伙人提交的注册申请已通过，请于微信登录查看。";
				partnerAccountVo.setState(AccountState.PASS);
			}else{
				smsContent = "您在艾臣合伙人提交的注册申请不通过，请与推荐人联系。";
				partnerAccountVo.setState(AccountState.NOTPASS);
			}
			Boolean flag = this.partnerAccountService.edit(partnerAccountVo);
			if(flag){
				//发送短信
				IHuiYiUtils.sentSms(partnerAccountVo.getMobilePhone(), smsContent);
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
	 * 分红管理页面
	 * @return
	 */
	@RequestMapping(value = "/admin/toBonusIndex", method = RequestMethod.GET)
	public String toBonusIndex() {
		this.request.setAttribute("industryTypeMap", IndustryType.IndustryTypeMap);
		return "/jsp/partner/admin/partnerAccount/bonusIndex";
	}
	
	@RequestMapping(value = "/admin/toAddBonus", method = RequestMethod.GET)
	public String toAddBonus(Integer id) {
		if(id != null){
			PartnerAccountVo partnerAccountVo = this.partnerAccountService.getById(id);
			this.request.setAttribute("partnerAccountVo",partnerAccountVo);
		}
		return "/jsp/partner/admin/partnerAccount/addBonus";
	}	
	
	/**
	 * 添加转账记录
	 * @param partnerAccountVo
	 * @return
	 */
	@RequestMapping(value = "/admin/addBonus", method = RequestMethod.POST)
	@ResponseBody
	public Result addBonus(PartnerWalletBill partnerWalletBill) {
		Result result = new Result();
		try{
			SessionInfo sessionInfo = (SessionInfo) this.request.getSession().getAttribute("sessionInfo");
			if(sessionInfo == null){
				result.setError(ResultCode.CODE_STATE_4006, "你还没登陆或超时，请重新登陆");
				return result;
			}
			partnerWalletBill.setCreateTime(new Date());
			partnerWalletBill.setType(WalletBillType.EXCHANGE_BONUS);
			partnerWalletBill.setDescription(sessionInfo.getUser().getUserName());
			partnerWalletBill.setSourceId(sessionInfo.getUser().getId());
			partnerWalletBill.setSourceType(WalletBillSourceType.USER);
			partnerWalletBill.setWallet(-partnerWalletBill.getWallet());
			result = this.partnerAccountService.updateWallet(partnerWalletBill);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 获取列表-分页
	 * @return list
	 */
	@RequestMapping(value = "/admin/billList", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson billList(PartnerWalletBillSearch partnerWalletBillSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			partnerWalletBillSearch.setIsPage(true);
			List<PartnerWalletBillVo> billList = this.partnerWalletBillService.getList(partnerWalletBillSearch);
			result.setTotal(this.partnerWalletBillService.getRowCount(partnerWalletBillSearch));
			result.setRows(billList);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
}
