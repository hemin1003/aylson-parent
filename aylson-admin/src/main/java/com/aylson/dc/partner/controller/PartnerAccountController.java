package com.aylson.dc.partner.controller;

import java.util.Date;
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
import com.aylson.dc.base.GeneralConstant.SourceTable;
import com.aylson.dc.base.GeneralConstant.UserType;
import com.aylson.dc.base.PartnerGeneralConstant.AccountState;
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
import com.aylson.utils.StringUtil;

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
	 * 获取合伙人列表（分页）
	 * @param partnerAccountSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPage", method = RequestMethod.GET)
	public Result getPage(PartnerAccountSearch partnerAccountSearch) {
		Result result = new Result();
		SessionInfo sessionInfo = (SessionInfo) this.request.getSession().getAttribute("sessionInfo");
		if(sessionInfo == null){
			result.setError(ResultCode.CODE_STATE_4006, "你还没登陆或超时，请重新登陆");
			return result;
		}
		if(sessionInfo.getUser().getType().intValue() == UserType.AGENT_USER){
			partnerAccountSearch.setUserId(sessionInfo.getUser().getId());
		}
		Page<PartnerAccountVo> page = this.partnerAccountService.getPage(partnerAccountSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", page);
		return result;
	}
	
	/**
	 * 获取合伙人列表（不分页）
	 * @param partnerAccountSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public Result getList(PartnerAccountSearch partnerAccountSearch) {
		Result result = new Result();
		SessionInfo sessionInfo = (SessionInfo) this.request.getSession().getAttribute("sessionInfo");
		if(sessionInfo == null){
			result.setError(ResultCode.CODE_STATE_4006, "你还没登陆或超时，请重新登陆");
			return result;
		}
		if(sessionInfo.getUser().getType().intValue() == UserType.AGENT_USER){
			partnerAccountSearch.setUserId(sessionInfo.getUser().getId());
		}
		List<PartnerAccountVo> list = this.partnerAccountService.getList(partnerAccountSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", list);
		return result;
	}
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getById", method = RequestMethod.GET)
	public Result getById(Integer id) {
		Result result = new Result();
		PartnerAccountSearch partnerAccountSearch = new PartnerAccountSearch();
		SessionInfo sessionInfo = (SessionInfo) this.request.getSession().getAttribute("sessionInfo");
		if(sessionInfo == null){
			result.setError(ResultCode.CODE_STATE_4006, "你还没登陆或超时，请重新登陆");
			return result;
		}
		if(sessionInfo.getUser().getType().intValue() == UserType.AGENT_USER){
			partnerAccountSearch.setUserId(sessionInfo.getUser().getId());
		}
		if(id == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取id失败，请稍后再试或者联系管理员！");
			return result;
		}
		partnerAccountSearch.setId(id);
		List<PartnerAccountVo> list = this.partnerAccountService.getList(partnerAccountSearch);
		PartnerAccountVo partnerAccountVo = list.get(0);
		if(partnerAccountVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取信息失败，请稍后再试或者联系管理员！");
		}else{
			result.setOK(ResultCode.CODE_STATE_200, "操作成功", partnerAccountVo);
		}
		return result;
	}
	
	/**
	 * 审核合伙人注册申请
	 * @param partnerAccountVo
	 * @return
	 */
	@RequestMapping(value = "/verify", method = RequestMethod.POST)
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
	 * 获取转账记录列表（分页）
	 * @param partnerWalletBillSearch
	 * @return
	 */
	@RequestMapping(value = "/getBonusPage", method = RequestMethod.GET)
	@ResponseBody
	public Result getBonusPage(PartnerWalletBillSearch partnerWalletBillSearch){
		Result result = new Result();
		SessionInfo sessionInfo = (SessionInfo) this.request.getSession().getAttribute("sessionInfo");
		if(sessionInfo == null){
			result.setError(ResultCode.CODE_STATE_4006, "你还没登陆或超时，请重新登陆");
			return result;
		}
		if(sessionInfo.getUser().getType().intValue() == UserType.AGENT_USER){
			partnerWalletBillSearch.setAgentUserId(sessionInfo.getUser().getId());
		}
		Page<PartnerWalletBillVo> page = this.partnerWalletBillService.getPage(partnerWalletBillSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", page);
		return result;
	}
	
	/**
	 * 获取转账记录列表（不分页）
	 * @param partnerWalletBillSearch
	 * @return
	 */
	@RequestMapping(value = "/getBonusList", method = RequestMethod.GET)
	@ResponseBody
	public Result getBonusList(PartnerWalletBillSearch partnerWalletBillSearch){
		Result result = new Result();
		SessionInfo sessionInfo = (SessionInfo) this.request.getSession().getAttribute("sessionInfo");
		if(sessionInfo == null){
			result.setError(ResultCode.CODE_STATE_4006, "你还没登陆或超时，请重新登陆");
			return result;
		}
		if(sessionInfo.getUser().getType().intValue() == UserType.AGENT_USER){
			partnerWalletBillSearch.setAgentUserId(sessionInfo.getUser().getId());
		}
		List<PartnerWalletBillVo> list = this.partnerWalletBillService.getList(partnerWalletBillSearch);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功", list);
		return result;
	}

	/**
	 * 添加转账记录
	 * @param partnerAccountVo
	 * @return
	 */
	@RequestMapping(value = "/addBonus", method = RequestMethod.POST)
	@ResponseBody
	public Result addBonus(PartnerWalletBill partnerWalletBill) {
		Result result = new Result();
		try{
			SessionInfo sessionInfo = (SessionInfo) this.request.getSession().getAttribute("sessionInfo");
			if(sessionInfo == null){
				result.setError(ResultCode.CODE_STATE_4006, "你还没登陆或超时，请重新登陆");
				return result;
			}
			if(sessionInfo.getUser().getType().intValue() == UserType.ORG_USER){
				result.setError(ResultCode.CODE_STATE_4006, "您没有该操作权限！");
				return result;
			}
			if(partnerWalletBill.getAccountId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取合伙人id失败，请稍后再试或者联系管理员！");
				return result;
			}
			if(StringUtil.isEmpty(partnerWalletBill.getAttachUrl())){
				result.setError(ResultCode.CODE_STATE_4006, "转账凭证附近不能为空！");
				return result;
			}
			if(partnerWalletBill.getWallet() == null){
				result.setError(ResultCode.CODE_STATE_4006, "转账金额不能为空！");
				return result;
			}
			if(partnerWalletBill.getWallet().floatValue() <= 0.0f){
				result.setError(ResultCode.CODE_STATE_4006, "转账金额不能小于0！");
				return result;
			}
			if(StringUtil.isEmpty(partnerWalletBill.getAttachUrl())){
				result.setError(ResultCode.CODE_STATE_4006, "请上传转账凭证附件");
				return result;
			}
			PartnerAccountSearch partnerAccountSearch = new PartnerAccountSearch();
			if(sessionInfo.getUser().getType().intValue() == UserType.AGENT_USER){
				partnerAccountSearch.setUserId(sessionInfo.getUser().getId());
			}
			partnerAccountSearch.setId(partnerWalletBill.getAccountId());
			List<PartnerAccountVo> list = this.partnerAccountService.getList(partnerAccountSearch);
			PartnerAccountVo partnerAccountVo = list.get(0);
			if(partnerAccountVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取合伙人信息失败，请稍后再试或者联系管理员！");
				return result;
			}
			if(partnerWalletBill.getWallet() > (partnerAccountVo.getWalletGet()+partnerAccountVo.getWalletHad())){
				result.setError(ResultCode.CODE_STATE_4006, "转账金额超过未转分红的金额！");
				return result;
			}
			
			partnerWalletBill.setCreateTime(new Date());
			partnerWalletBill.setType(WalletBillType.EXCHANGE_BONUS);
			partnerWalletBill.setDescription(sessionInfo.getUser().getUserName());
			partnerWalletBill.setSourceId(sessionInfo.getUser().getId());
			partnerWalletBill.setAgentUserId(sessionInfo.getUser().getId());
			partnerWalletBill.setSourceType(SourceTable.SYS_USER);
			partnerWalletBill.setWallet(-partnerWalletBill.getWallet());
			result = this.partnerAccountService.updateWallet(partnerWalletBill);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	
}
