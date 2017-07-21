package com.aylson.dc.mem.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aylson.core.exception.ServiceException;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.base.GeneralConstant.SourceType;
import com.aylson.dc.base.GeneralConstant.WalletType;
import com.aylson.dc.base.GeneralConstant.WithdrawalsApplyStatus;
import com.aylson.dc.base.SmsTemplate;
import com.aylson.dc.base.WxMessageTemplate;
import com.aylson.dc.mem.dao.WithdrawalsApplyDao;
import com.aylson.dc.mem.po.MemAccount;
import com.aylson.dc.mem.po.MemWalletDetail;
import com.aylson.dc.mem.po.WithdrawalsApply;
import com.aylson.dc.mem.search.WithdrawalsApplySearch;
import com.aylson.dc.mem.service.MemAccountService;
import com.aylson.dc.mem.service.WithdrawalsApplyService;
import com.aylson.dc.mem.vo.WithdrawalsApplyVo;
import com.aylson.dc.sys.common.SystemConfig;
import com.aylson.utils.IHuiYiUtils;
import com.aylson.utils.StringUtil;
import com.aylson.utils.VerificationUtils;

@Service
public class WithdrawalsApplyServiceImpl extends BaseServiceImpl<WithdrawalsApply,WithdrawalsApplySearch> implements WithdrawalsApplyService {
	
	private static final Logger logger = LoggerFactory.getLogger(WithdrawalsApplyServiceImpl.class);
	
	@Autowired
	private WithdrawalsApplyDao withdrawalsApplyDao;
	@Autowired
	private MemAccountService memAccountService;
	
	@Override
	protected BaseDao<WithdrawalsApply,WithdrawalsApplySearch> getBaseDao() {
		return this.withdrawalsApplyDao;
	}

	/**
	 * 提现申请
	 * @param withdrawalsApplyVo
	 * @return
	 */
	@Override
	@Transactional
	public Result addWithdrawalsApply(WithdrawalsApplyVo withdrawalsApplyVo) {
		Result result = new Result();
		Boolean flag = false;
		if(withdrawalsApplyVo.getApplierId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
			return result;
		}
		MemAccount curMem = this.memAccountService.getById(withdrawalsApplyVo.getApplierId());
		if(curMem == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
			return result;
		}
		if(StringUtil.isEmpty(withdrawalsApplyVo.getBankNum())){
			result.setError(ResultCode.CODE_STATE_4006, "银行卡号不能为空");
			return result;
		}
		if(StringUtil.isEmpty(withdrawalsApplyVo.getBankholder())){
			result.setError(ResultCode.CODE_STATE_4006, "持卡人不能为空");
			return result;
		}
		if(StringUtil.isEmpty(withdrawalsApplyVo.getBankName())){
			result.setError(ResultCode.CODE_STATE_4006, "所属银行不能为空");
			return result;
		}
		if(withdrawalsApplyVo.getAmount() == null){
			result.setError(ResultCode.CODE_STATE_4006, "提现金额不能为空");
			return result;
		}
		if(withdrawalsApplyVo.getAmount() <= 0.0f ){
			result.setError(ResultCode.CODE_STATE_4006, "提现金额必须大于0");
			return result;
		}
		if(withdrawalsApplyVo.getAmount() > curMem.getWallet() ){
			result.setError(ResultCode.CODE_STATE_4006, "钱包金额不足");
			return result;
		}
		Date curDate = new Date();
		withdrawalsApplyVo.setApplyTime(curDate);
		withdrawalsApplyVo.setStatus(WithdrawalsApplyStatus.DEALING);
		flag = this.add(withdrawalsApplyVo);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "添加失败");
			throw new ServiceException("添加失败");
		}
		MemWalletDetail memWalletDetail = new MemWalletDetail(null, withdrawalsApplyVo.getApplierId(), WalletType.WITHDRAWALS, -withdrawalsApplyVo.getAmount(), 
				curDate, WalletType.WalletTypeMap.get(WalletType.WITHDRAWALS), SourceType.PIONEERAGENT, withdrawalsApplyVo.getId());
		result = this.memAccountService.updateWallet(memWalletDetail);//赠送提交人现金
		if(!result.isSuccess()){
			result.setError(ResultCode.CODE_STATE_4006, "代理商签约赠送金币失败");
			throw new ServiceException("代理商签约赠送金币失败");
		}
		result.setOK(ResultCode.CODE_STATE_200, "添加成功",withdrawalsApplyVo);
		
		return result;
	}

	@Override
	@Transactional
	public Result transfer(WithdrawalsApplyVo withdrawalsApplyVo) {
		Result result = new Result();
		Boolean flag = false;
		//信息校验
		if(withdrawalsApplyVo.getId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取提现申请详情失败");
			return result;
		}
		if(withdrawalsApplyVo.getStatus() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取状态失败");
			return result;
		}
		if(WithdrawalsApplyStatus.HAD_DEAL != withdrawalsApplyVo.getStatus().intValue() &&
				WithdrawalsApplyStatus.FAIL_DEAL != withdrawalsApplyVo.getStatus().intValue()){
			result.setError(ResultCode.CODE_STATE_4006, "未知状态");
			return result;
		}
		if(WithdrawalsApplyStatus.HAD_DEAL == withdrawalsApplyVo.getStatus().intValue() && 
				StringUtil.isEmpty(withdrawalsApplyVo.getTransferProof())){
			result.setError(ResultCode.CODE_STATE_4006, "转账成功必须上传转账凭证");
			return result;
		}
		if(WithdrawalsApplyStatus.FAIL_DEAL == withdrawalsApplyVo.getStatus().intValue() && 
				StringUtil.isEmpty(withdrawalsApplyVo.getRemark())){
			result.setError(ResultCode.CODE_STATE_4006, "转账失败必须备注原因");
			return result;
		}
		//业务处理：更新申请信息，发送短信，失败的话必须将钱还回去
		Date curDate = new Date();
		flag = this.edit(withdrawalsApplyVo);
		if(flag){
			//失败返还现金
			if(WithdrawalsApplyStatus.FAIL_DEAL == withdrawalsApplyVo.getStatus().intValue()){//失败返回现金
				MemWalletDetail memWalletDetail = new MemWalletDetail(null, withdrawalsApplyVo.getApplierId(), WalletType.WITHDRAWALS_FAIL, withdrawalsApplyVo.getAmount(), 
						curDate, WalletType.WalletTypeMap.get(WalletType.WITHDRAWALS_FAIL), SourceType.PIONEERAGENT, withdrawalsApplyVo.getId());
				result = this.memAccountService.updateWallet(memWalletDetail);//赠送提交人现金
				if(!result.isSuccess()){
					result.setError(ResultCode.CODE_STATE_4006, "代理商签约赠送金币失败");
					throw new ServiceException("代理商签约赠送金币失败");
				}
			}
			String smsContent = null;
			String wxMessage = null;
			if(WithdrawalsApplyStatus.HAD_DEAL == withdrawalsApplyVo.getStatus().intValue()){
				//发送通知短信
				smsContent = SmsTemplate.getSmsWhenPioneerTransferSuccess(withdrawalsApplyVo.getAmount(), withdrawalsApplyVo.getBankNum().substring(withdrawalsApplyVo.getBankNum().length()-4, withdrawalsApplyVo.getBankNum().length()));
				wxMessage = WxMessageTemplate.whenPioneerTransferMoney(withdrawalsApplyVo.getAmount(), withdrawalsApplyVo.getBankNum().substring(withdrawalsApplyVo.getBankNum().length()-4, withdrawalsApplyVo.getBankNum().length()), WxMessageTemplate.getPioneerHost()+"/pages/account-details.jsp");
			}else if(WithdrawalsApplyStatus.FAIL_DEAL == withdrawalsApplyVo.getStatus().intValue()){
				smsContent = SmsTemplate.getSmsWhenPioneerTransferFail(withdrawalsApplyVo.getBankNum().substring(withdrawalsApplyVo.getBankNum().length()-4, withdrawalsApplyVo.getBankNum().length()));
				wxMessage = WxMessageTemplate.whenPioneerTransferFail( withdrawalsApplyVo.getBankNum().substring(withdrawalsApplyVo.getBankNum().length()-4, withdrawalsApplyVo.getBankNum().length()), WxMessageTemplate.getPioneerHost()+"/pages/account-details.jsp");
			}
			if(!SystemConfig.isDebugMode() && smsContent != null){//非调试模式才发送短信
				if(StringUtil.isNotEmpty(smsContent) && VerificationUtils.isValid(withdrawalsApplyVo.getApplierPhone(), VerificationUtils.MOBILE)){
					//有效的电话号码才发短信
					IHuiYiUtils.sentSms(withdrawalsApplyVo.getApplierPhone(), smsContent);
				}else{
					System.out.println("======短信通知电话无效或内容为空："+withdrawalsApplyVo.getApplierPhone()+":"+smsContent+"==========");
				}
			}
			//发送微信信息
			if(StringUtil.isNotEmpty(withdrawalsApplyVo.getWxOpenId()) && wxMessage != null){
				String ktzServiceUrl = null;
				if(SystemConfig.isLiveMode()){
					 ktzServiceUrl = SystemConfig.getConfigValueByKey("ktz_service")+"/service/common/sendWxMessageResponse";
				}else{
					 ktzServiceUrl = "http://test.aylsonclub.com/dc-web/common/sendWxMessageResponse";
				}
				WxMessageTemplate.sendWxMessageRequest(ktzServiceUrl, withdrawalsApplyVo.getWxOpenId(), wxMessage);
			}
			
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "更新提现申请信息失败");
			return result;
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		
		return result;
	}
	
	@Override
	public Float sumByApplierId(Integer applierId) {
		if(applierId == null) return 0.0f;
		return this.withdrawalsApplyDao.sumByApplierId(applierId);
	}

	
}
