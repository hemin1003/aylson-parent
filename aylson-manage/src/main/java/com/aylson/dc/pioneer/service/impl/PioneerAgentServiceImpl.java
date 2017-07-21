package com.aylson.dc.pioneer.service.impl;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aylson.core.exception.ServiceException;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.base.GeneralConstant.IntegeralType;
import com.aylson.dc.base.GeneralConstant.IntegralConfigType;
import com.aylson.dc.base.GeneralConstant.PioneerAgentStatus;
import com.aylson.dc.base.GeneralConstant.SourceType;
import com.aylson.dc.base.GeneralConstant.WalletType;
import com.aylson.dc.base.SmsTemplate;
import com.aylson.dc.base.WxMessageTemplate;
import com.aylson.dc.mem.po.MemIntegralDetail;
import com.aylson.dc.mem.po.MemWalletDetail;
import com.aylson.dc.mem.service.MemAccountService;
import com.aylson.dc.mem.vo.MemAccountVo;
import com.aylson.dc.pioneer.dao.PioneerAgentDao;
import com.aylson.dc.pioneer.po.PioneerAgent;
import com.aylson.dc.pioneer.search.PioneerAgentSearch;
import com.aylson.dc.pioneer.service.PioneerAgentService;
import com.aylson.dc.pioneer.vo.PioneerAgentVo;
import com.aylson.dc.sys.common.SystemConfig;
import com.aylson.dc.sys.service.IntegralConfigService;
import com.aylson.dc.sys.vo.IntegralConfigVo;
import com.aylson.utils.DateUtil;
import com.aylson.utils.IHuiYiUtils;
import com.aylson.utils.StringUtil;
import com.aylson.utils.VerificationUtils;


@Service
public class PioneerAgentServiceImpl extends BaseServiceImpl<PioneerAgent,PioneerAgentSearch> implements PioneerAgentService {

	@Autowired
	private PioneerAgentDao pioneerAgentDao;
	@Autowired
	private IntegralConfigService integralConfigService;
	@Autowired
	private MemAccountService memAccountService;

	@Override
	protected BaseDao<PioneerAgent,PioneerAgentSearch> getBaseDao() {
		return pioneerAgentDao;
	}

	/**
	 * 审核代理商资料
	 * @param pioneerAgentVo
	 * @return
	 */
	@Override
	@Transactional
	public Result verify(PioneerAgentVo pioneerAgentVo) {
		Result result = new Result();
		Boolean flag = false;
		//1、信息校验
		if(pioneerAgentVo.getId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取代理商信息id失败");
			return result;
		}
		if(pioneerAgentVo.getSubmitterId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取开拓者会员信息失败");
			return result;
		}
		if(pioneerAgentVo.getStatus() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取代理商信息状态失败");
			return result;
		}
		if(PioneerAgentStatus.WAIT_AUDIT != pioneerAgentVo.getStatus().intValue()
				&& PioneerAgentStatus.HAD_AUDIT != pioneerAgentVo.getStatus().intValue()
				&& PioneerAgentStatus.FAIL_AUDIT != pioneerAgentVo.getStatus().intValue()){
			result.setError(ResultCode.CODE_STATE_4006, "无效状态");
			return result;
		}
		if(PioneerAgentStatus.FAIL_AUDIT == pioneerAgentVo.getStatus().intValue() && StringUtil.isEmpty(pioneerAgentVo.getRemark())){
			result.setError(ResultCode.CODE_STATE_4006, "审核不通过必须填写审核意见");
			return result;
		}
		//2、业务处理：赠送积分，更新状态
		Date curDate = new Date();
		if(StringUtil.isNotEmpty(pioneerAgentVo.getRemark())){
			pioneerAgentVo.setHistoryRemark(DateUtil.format(curDate)+":"+pioneerAgentVo.getRemark()+";<br>");
		}
		String smsContent = null;               //短信通知提交人内容
		//String smsReferralerContent = null;     //短信通知提交人的推荐人内容
		MemAccountVo submitterReferral = null;  //提交人的推荐人
		String wxMessage = null;                //微信通知提交人内容
		//String wxReferralerMessage = null;      //微信通知提交人的推荐人内容
		if(PioneerAgentStatus.HAD_AUDIT == pioneerAgentVo.getStatus().intValue()){//审核通过，需要赠送积分
			IntegralConfigVo submitterIntegral = this.integralConfigService.getIntegralConfig(IntegralConfigType.P_SUBMITAGENTINFO, null);
			if(submitterIntegral != null &&
					submitterIntegral.getIntegral() != null && 
							submitterIntegral.getIntegral().intValue() > 0	){
				MemIntegralDetail memIntegralDetail = new MemIntegralDetail(null, pioneerAgentVo.getSubmitterId(), IntegeralType.SEND_BY_VERIFY_AGENTINFO, submitterIntegral.getIntegral(), 
						curDate, IntegeralType.IntegeralTypeMap.get(IntegeralType.SEND_BY_VERIFY_AGENTINFO), SourceType.PIONEERAGENT, pioneerAgentVo.getId());
				result = this.memAccountService.updateIntegral(memIntegralDetail);//赠送提交人积分
				if(!result.isSuccess()){
					result.setError(ResultCode.CODE_STATE_4006, "代理商资料审核通过赠送积分失败");
					throw new ServiceException("代理商资料审核通过赠送积分失败");
				}
				//通知短信内容
				smsContent = SmsTemplate.getSmsWhenPioneerVerifyAgentInfo(pioneerAgentVo.getAgentName(), submitterIntegral.getIntegral());
				wxMessage = WxMessageTemplate.whenPioneerVerifyAgentInfo(pioneerAgentVo.getAgentName(), 
						submitterIntegral.getIntegral(), WxMessageTemplate.getPioneerHost()+"/pages/integral.jsp");
				if(pioneerAgentVo.getSubmitterReferral() != null){
					submitterReferral = this.memAccountService.getById(pioneerAgentVo.getSubmitterReferral());
					IntegralConfigVo myReferralerIntegral = this.integralConfigService.getIntegralConfig(IntegralConfigType.P_SUBMITAGENTINFO_TO_REFERRAL, null);
					if(submitterIntegral != null &&
							submitterIntegral.getIntegral() != null && 
									submitterIntegral.getIntegral().intValue() > 0	){
						MemIntegralDetail memIntegralDetail2 = new MemIntegralDetail(null, pioneerAgentVo.getSubmitterReferral(), IntegeralType.SEND_BY_VERIFY_AGENTINFO_REFERRAL, myReferralerIntegral.getIntegral(), 
								curDate, IntegeralType.IntegeralTypeMap.get(IntegeralType.SEND_BY_VERIFY_AGENTINFO_REFERRAL), SourceType.PIONEERAGENT, pioneerAgentVo.getId());
						result = this.memAccountService.updateIntegral(memIntegralDetail2);//赠送提交人的推荐人积分
						if(!result.isSuccess()){
							result.setError(ResultCode.CODE_STATE_4006, "代理商资料审核通过赠送推荐人积分失败");
							throw new ServiceException("代理商资料审核通过赠送推荐人积分失败");
						}
						//smsReferralerContent = "";//代理商资料审核通过提示推荐人
					}
				}
			}
		}else if(PioneerAgentStatus.FAIL_AUDIT == pioneerAgentVo.getStatus().intValue()){//审核不通过
			smsContent = SmsTemplate.getSmsWhenPioneerAgentInfoImperfect(pioneerAgentVo.getAgentName());
			wxMessage = WxMessageTemplate.whenPioneerVerifyAgentInfoImperfect(pioneerAgentVo.getAgentName(), 
					WxMessageTemplate.getPioneerHost()+"/pages/agent-add.jsp?id="+pioneerAgentVo.getId());

		}
		flag = this.edit(pioneerAgentVo);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "保存审核信息失败");
			throw new ServiceException("保存审核信息失败");
		}
		//发送通知信息
		if(!SystemConfig.isDebugMode()){//非调试模式才发送短信
			if(StringUtil.isNotEmpty(smsContent) && VerificationUtils.isValid(pioneerAgentVo.getSubmitterPhone(), VerificationUtils.MOBILE)){
				//有效的电话号码才发短信
				IHuiYiUtils.sentSms(pioneerAgentVo.getSubmitterPhone(), smsContent);
			}
			/*if(StringUtil.isNotEmpty(smsReferralerContent) && submitterReferral != null && VerificationUtils.isValid(submitterReferral.getMobilePhone(), VerificationUtils.MOBILE)){
				//有效的电话号码才发短信  推荐人
				IHuiYiUtils.sentSms(submitterReferral.getMobilePhone(), smsReferralerContent);
			}*/
		}
		//发送微信信息
		if(StringUtil.isNotEmpty(pioneerAgentVo.getWxOpenId()) && wxMessage != null){
			String ktzServiceUrl = null;
			if(SystemConfig.isLiveMode()){
				 ktzServiceUrl = SystemConfig.getConfigValueByKey("ktz_service")+"/service/common/sendWxMessageResponse";
			}else{
				 ktzServiceUrl = "http://test.aylsonclub.com/dc-web/common/sendWxMessageResponse";
			}
			WxMessageTemplate.sendWxMessageRequest(ktzServiceUrl, pioneerAgentVo.getWxOpenId(), wxMessage);
		}
		/*if(submitterReferral != null && StringUtil.isNotEmpty(submitterReferral.getWxOpenId()) && wxReferralerMessage != null){
			this.sendWeixinMesage(submitterReferral.getWxOpenId(), wxReferralerMessage);
		}*/
		result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		return result;
	}

	/**
	 * 签约代理商资料
	 * @param pioneerAgentVo
	 * @return
	 */
	@Override
	public Result sign(PioneerAgentVo pioneerAgentVo) {
		Result result = new Result();
		Boolean flag = false;
		//1、信息校验
		if(pioneerAgentVo.getId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取代理商信息id失败");
			return result;
		}
		PioneerAgentVo pioneerAgentVoOld = this.getById(pioneerAgentVo.getId());
		if(pioneerAgentVoOld == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取代理商信息失败");
			return result;
		}
		if(pioneerAgentVoOld.getSubmitterId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取开拓者会员信息失败");
			return result;
		}
		if(pioneerAgentVo.getStatus() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取代理商信息状态失败");
			return result;
		}
		if(PioneerAgentStatus.HAD_SIGN != pioneerAgentVo.getStatus().intValue()
				&& PioneerAgentStatus.FAIL_SIGN != pioneerAgentVo.getStatus().intValue()){
			result.setError(ResultCode.CODE_STATE_4006, "无效状态");
			return result;
		}
		if(PioneerAgentStatus.FAIL_SIGN == pioneerAgentVo.getStatus().intValue() && StringUtil.isEmpty(pioneerAgentVo.getRemark())){
			result.setError(ResultCode.CODE_STATE_4006, "审核不通过必须填写审核意见");
			return result;
		}
		if(PioneerAgentStatus.HAD_SIGN == pioneerAgentVo.getStatus().intValue() && pioneerAgentVo.getSignMode() == null){
			result.setError(ResultCode.CODE_STATE_4006, "签约方式不能为空");
			return result;
		}
		//业务处理：赠送积分和现金；发送短信、微信通知；更新状态
		Date curDate = new Date();
		if(StringUtil.isNotEmpty(pioneerAgentVo.getRemark())){
			pioneerAgentVo.setHistoryRemark(pioneerAgentVoOld.getHistoryRemark() + DateUtil.format(curDate)+":"+pioneerAgentVo.getRemark()+";<br>");
		}
		String smsContent = null;  //短信通知提交人
		String wxMessage = null;   //微信通知提交人
		IntegralConfigVo submitterIntegral = null;
		if(PioneerAgentStatus.HAD_SIGN == pioneerAgentVo.getStatus().intValue()){//签约成功，需要赠送积分和现金
			if(1 == pioneerAgentVo.getSignMode().intValue()){//独立介绍
				submitterIntegral = this.integralConfigService.getIntegralConfig(IntegralConfigType.P_SIGN_TO_REFERRAL, null);
			}else if(2 == pioneerAgentVo.getSignMode().intValue()){//分独立介绍
				submitterIntegral = this.integralConfigService.getIntegralConfig(IntegralConfigType.P_SIGN_NOTSINGLE_TO_REFERRAL, null);
			}
			if(submitterIntegral != null &&	submitterIntegral.getIntegral() != null 
					&& 	submitterIntegral.getIntegral().intValue() > 0	){
				MemIntegralDetail memIntegralDetail = new MemIntegralDetail(null, pioneerAgentVoOld.getSubmitterId(), IntegeralType.SEND_BY_SIGN_AGENTINFO, submitterIntegral.getIntegral(), 
						curDate, IntegeralType.IntegeralTypeMap.get(IntegeralType.SEND_BY_SIGN_AGENTINFO), SourceType.PIONEERAGENT, pioneerAgentVo.getId());
				result = this.memAccountService.updateIntegral(memIntegralDetail);//赠送提交人积分
				if(!result.isSuccess()){
					result.setError(ResultCode.CODE_STATE_4006, "代理商签约赠送积分失败");
					throw new ServiceException("代理商签约赠送积分失败");
				}
			}
			//赠送邀请人的推荐人积分
			if(pioneerAgentVoOld.getSubmitterReferral() != null){
				IntegralConfigVo submitterIntegralReferral = this.integralConfigService.getIntegralConfig(IntegralConfigType.P_SIGN_TO_REFERRAL_PARENT, null);
				if(submitterIntegralReferral != null &&	submitterIntegralReferral.getIntegral() != null 
						&& 	submitterIntegralReferral.getIntegral().intValue() > 0	){
					MemIntegralDetail memIntegralDetail = new MemIntegralDetail(null, pioneerAgentVoOld.getSubmitterReferral(), IntegeralType.SEND_BY_SIGN_REFERRAL, submitterIntegralReferral.getIntegral(), 
							curDate, IntegeralType.IntegeralTypeMap.get(IntegeralType.SEND_BY_SIGN_REFERRAL), SourceType.PIONEERAGENT, pioneerAgentVo.getId());
					result = this.memAccountService.updateIntegral(memIntegralDetail);//赠送邀请人的推荐人积分
					if(!result.isSuccess()){
						result.setError(ResultCode.CODE_STATE_4006, "代理商签约赠送推荐人积分失败");
						throw new ServiceException("代理商签约赠送推荐人积分失败");
					}
				}
			}
			if(submitterIntegral != null &&	submitterIntegral.getRate() != null 
					&& submitterIntegral.getRate().floatValue() > 0.0f	){
				MemWalletDetail memWalletDetail = new MemWalletDetail(null, pioneerAgentVoOld.getSubmitterId(), WalletType.SEND_BY_SIGN, submitterIntegral.getRate(), 
						curDate, WalletType.WalletTypeMap.get(WalletType.SEND_BY_SIGN), SourceType.PIONEERAGENT, pioneerAgentVo.getId());
				result = this.memAccountService.updateWallet(memWalletDetail);//赠送提交人现金
				if(!result.isSuccess()){
					result.setError(ResultCode.CODE_STATE_4006, "代理商签约赠送金币失败");
					throw new ServiceException("代理商签约赠送金币失败");
				}
			}
			if(submitterIntegral != null){
				smsContent = SmsTemplate.getSmsWhenPioneerSignSuccess(pioneerAgentVoOld.getAgentName(), submitterIntegral.getIntegral(), submitterIntegral.getRate());
				wxMessage = WxMessageTemplate.whenPioneerAgentSign(pioneerAgentVoOld.getAgentName(), submitterIntegral.getIntegral(), submitterIntegral.getRate(), WxMessageTemplate.getPioneerHost()+"/pages/user.jsp");
			}
		}
		flag = this.edit(pioneerAgentVo);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "保存审核信息失败");
			throw new ServiceException("保存审核信息失败");
		}
		//发送通知信息
		if(!SystemConfig.isDebugMode()){//非调试模式才发送短信
			if(StringUtil.isNotEmpty(smsContent) && VerificationUtils.isValid(pioneerAgentVoOld.getSubmitterPhone(), VerificationUtils.MOBILE)){
				//有效的电话号码才发短信
				IHuiYiUtils.sentSms(pioneerAgentVoOld.getSubmitterPhone(), smsContent);
			}else{
				System.out.println("======短信通知电话无效或内容为空："+pioneerAgentVoOld.getSubmitterPhone()+":"+smsContent+"==========");
			}
		}
		//发送微信信息
		if(StringUtil.isNotEmpty(pioneerAgentVoOld.getWxOpenId()) && wxMessage != null){
			String ktzServiceUrl = null;
			if(SystemConfig.isLiveMode()){
				 ktzServiceUrl = SystemConfig.getConfigValueByKey("ktz_service")+"/service/common/sendWxMessageResponse";
			}else{
				 ktzServiceUrl = "http://test.aylsonclub.com/dc-web/common/sendWxMessageResponse";
			}
			WxMessageTemplate.sendWxMessageRequest(ktzServiceUrl, pioneerAgentVoOld.getWxOpenId(), wxMessage);
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		return result;
	}

	/**
	 * 开业
	 * @param pioneerAgentVo
	 * @return
	 */
	@Override
	public Result openShop(PioneerAgentVo pioneerAgentVo) {
		Result result = new Result();
		Boolean flag = false;
		//1、信息校验
		if(pioneerAgentVo.getId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取代理商信息id失败");
			return result;
		}
		PioneerAgentVo pioneerAgentVoOld = this.getById(pioneerAgentVo.getId());
		if(pioneerAgentVoOld == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取代理商信息失败");
			return result;
		}
		if(pioneerAgentVoOld.getSubmitterId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取开拓者会员信息失败");
			return result;
		}
		if(pioneerAgentVo.getStatus() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取代理商信息状态失败");
			return result;
		}
		if(PioneerAgentStatus.HAD_OPEN != pioneerAgentVo.getStatus().intValue()
				&& PioneerAgentStatus.FAIL_OPEN != pioneerAgentVo.getStatus().intValue()){
			result.setError(ResultCode.CODE_STATE_4006, "无效状态");
			return result;
		}
		if(PioneerAgentStatus.FAIL_OPEN == pioneerAgentVo.getStatus().intValue() && StringUtil.isEmpty(pioneerAgentVo.getRemark())){
			result.setError(ResultCode.CODE_STATE_4006, "开业失败必须填写审核意见");
			return result;
		}
		if(PioneerAgentStatus.HAD_OPEN == pioneerAgentVo.getStatus().intValue() && StringUtil.isEmpty(pioneerAgentVo.getShopImg())){
			result.setError(ResultCode.CODE_STATE_4006, "开业成功必须上传商店图片");
			return result;
		}
		//2、逻辑处理
		//开业成功：更新信息，赠送积分和现金，短信通知，微信通知提交人
		//开业失败：更新信息
		Date curDate = new Date();
		if(StringUtil.isNotEmpty(pioneerAgentVo.getRemark())){
			pioneerAgentVo.setHistoryRemark(pioneerAgentVoOld.getHistoryRemark() + DateUtil.format(curDate)+":"+pioneerAgentVo.getRemark()+";<br>");
		}
		String smsContent = null;  //短信通知提交人
		String wxMessage = null;   //微信通知提交人
		IntegralConfigVo submitterIntegral = null;
		if(PioneerAgentStatus.HAD_OPEN == pioneerAgentVo.getStatus().intValue()){
			if(1 == pioneerAgentVo.getSignMode().intValue()){//独立介绍
				submitterIntegral = this.integralConfigService.getIntegralConfig(IntegralConfigType.P_OPEN_TO_REFERRAL, pioneerAgentVo.getShopAreas());
			}else if(2 == pioneerAgentVo.getSignMode().intValue()){//非独立介绍
				submitterIntegral = this.integralConfigService.getIntegralConfig(IntegralConfigType.P_OPEN_NOTSINGLE_TO_REFERRAL, pioneerAgentVo.getShopAreas());
			}
			if(submitterIntegral != null &&	submitterIntegral.getIntegral() != null 
					&& 	submitterIntegral.getIntegral().intValue() > 0	){
				MemIntegralDetail memIntegralDetail = new MemIntegralDetail(null, pioneerAgentVoOld.getSubmitterId(), IntegeralType.SEND_BY_OPEN_AGENTINFO, submitterIntegral.getIntegral(), 
						curDate, IntegeralType.IntegeralTypeMap.get(IntegeralType.SEND_BY_OPEN_AGENTINFO), SourceType.PIONEERAGENT, pioneerAgentVo.getId());
				result = this.memAccountService.updateIntegral(memIntegralDetail);//赠送提交人积分
				if(!result.isSuccess()){
					result.setError(ResultCode.CODE_STATE_4006, "代理商开业赠送积分失败");
					throw new ServiceException("代理商开业赠送积分失败");
				}
			}
			//赠送邀请人的推荐人积分
			if(pioneerAgentVoOld.getSubmitterReferral() != null){
				IntegralConfigVo submitterIntegralReferral = this.integralConfigService.getIntegralConfig(IntegralConfigType.P_OPEN_TO_REFERRAL_PARENT, null);
				if(submitterIntegralReferral != null &&	submitterIntegralReferral.getIntegral() != null 
						&& 	submitterIntegralReferral.getIntegral().intValue() > 0	){
					MemIntegralDetail memIntegralDetail = new MemIntegralDetail(null, pioneerAgentVoOld.getSubmitterReferral(), IntegeralType.SEND_BY_OPEN_REFERRAL, submitterIntegralReferral.getIntegral(), 
							curDate, IntegeralType.IntegeralTypeMap.get(IntegeralType.SEND_BY_OPEN_REFERRAL), SourceType.PIONEERAGENT, pioneerAgentVo.getId());
					result = this.memAccountService.updateIntegral(memIntegralDetail);//赠送邀请人的推荐人积分
					if(!result.isSuccess()){
						result.setError(ResultCode.CODE_STATE_4006, "代理商开业赠送推荐人积分失败");
						throw new ServiceException("代理商开业赠送推荐人积分失败");
					}
				}
			}
			if(submitterIntegral != null &&	submitterIntegral.getRate() != null 
					&& submitterIntegral.getRate().floatValue() > 0.0f	){
				MemWalletDetail memWalletDetail = new MemWalletDetail(null, pioneerAgentVoOld.getSubmitterId(), WalletType.SEND_BY_OPEN, submitterIntegral.getRate(), 
						curDate, WalletType.WalletTypeMap.get(WalletType.SEND_BY_OPEN), SourceType.PIONEERAGENT, pioneerAgentVo.getId());
				result = this.memAccountService.updateWallet(memWalletDetail);//赠送提交人现金
				if(!result.isSuccess()){
					result.setError(ResultCode.CODE_STATE_4006, "代理商开业赠送金币失败");
					throw new ServiceException("代理商开业赠送金币失败");
				}
			}
			if(submitterIntegral != null){
				smsContent = SmsTemplate.getSmsWhenPioneerOpenSuccess(pioneerAgentVoOld.getAgentName(), submitterIntegral.getIntegral(), submitterIntegral.getRate());
				wxMessage = WxMessageTemplate.whenPioneerAgentOpen(pioneerAgentVoOld.getAgentName(), submitterIntegral.getIntegral(), submitterIntegral.getRate(), WxMessageTemplate.getPioneerHost()+"/pages/user.jsp");
			}
		}
		flag = this.edit(pioneerAgentVo);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			throw new ServiceException("操作失败");
		}
		//发送通知信息
		if(!SystemConfig.isDebugMode()){//非调试模式才发送短信
			if(StringUtil.isNotEmpty(smsContent) && VerificationUtils.isValid(pioneerAgentVoOld.getSubmitterPhone(), VerificationUtils.MOBILE)){
				//有效的电话号码才发短信
				IHuiYiUtils.sentSms(pioneerAgentVoOld.getSubmitterPhone(), smsContent);
			}else{
				System.out.println("======短信通知电话无效或内容为空："+pioneerAgentVoOld.getSubmitterPhone()+":"+smsContent+"==========");
			}
		}
		//发送微信信息
		if(StringUtil.isNotEmpty(pioneerAgentVoOld.getWxOpenId()) && wxMessage != null){
			String ktzServiceUrl = null;
			if(SystemConfig.isLiveMode()){
				 ktzServiceUrl = SystemConfig.getConfigValueByKey("ktz_service")+"/service/common/sendWxMessageResponse";
			}else{
				 ktzServiceUrl = "http://test.aylsonclub.com/dc-web/common/sendWxMessageResponse";
			}
			WxMessageTemplate.sendWxMessageRequest(ktzServiceUrl, pioneerAgentVoOld.getWxOpenId(), wxMessage);
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		return result;
	}
	
	public static void main(String[] args) {
		String aa = "审核不通过";
		System.out.println("通过".indexOf(aa));
		System.out.println(aa.indexOf("通过"));
	}
	
	
	/**
	 * 发送微信信息
	 * @param openId
	 * @param message
	 * @return
	 */
	/*private Result sendWeixinMesage(String openId, String message) {
		Result result = new Result();
		try {
			if(message != null && !message.equals("")){
				message = URLDecoder.decode(message, "UTF-8");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "内容不能为空");
				return result;
			}
			if (openId != null && !openId.equals("")) {
				String appId = null;
				String appSecret = null;
				if(SystemConfig.isLiveMode()){
					appId = SystemConfig.getConfigValueByKey("Formal_AppID");
					appSecret = SystemConfig.getConfigValueByKey("Formal_AppSecret");
				}else{
					appId = SystemConfig.getConfigValueByKey("Test_AppID");
					appSecret = SystemConfig.getConfigValueByKey("Test_AppSecret");
				}
				ApiConfig apiConfig = new ApiConfig(appId,appSecret);
				CustomAPI customAPI = new CustomAPI(apiConfig);
				customAPI.sendCustomMessage(openId,
						new TextMsg(message));
				result.setOK(ResultCode.CODE_STATE_200, "发送信息成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_4008, "系统繁忙，请稍候重试");
			return result;
		}
		return result;
	}*/
	
	

}
