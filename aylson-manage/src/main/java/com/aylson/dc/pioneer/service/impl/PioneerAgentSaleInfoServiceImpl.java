package com.aylson.dc.pioneer.service.impl;


import java.net.URLDecoder;
import java.util.Date;

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
import com.aylson.dc.base.SmsTemplate;
import com.aylson.dc.base.WxMessageTemplate;
import com.aylson.dc.mem.po.MemWalletDetail;
import com.aylson.dc.mem.service.MemAccountService;
import com.aylson.dc.pioneer.dao.PioneerAgentSaleInfoDao;
import com.aylson.dc.pioneer.po.PioneerAgentSaleInfo;
import com.aylson.dc.pioneer.search.PioneerAgentSaleInfoSearch;
import com.aylson.dc.pioneer.service.PioneerAgentSaleInfoService;
import com.aylson.dc.pioneer.service.PioneerAgentService;
import com.aylson.dc.pioneer.vo.PioneerAgentSaleInfoVo;
import com.aylson.dc.pioneer.vo.PioneerAgentVo;
import com.aylson.dc.sys.common.SystemConfig;
import com.aylson.utils.IHuiYiUtils;
import com.aylson.utils.StringUtil;
import com.aylson.utils.VerificationUtils;
import com.fastweixin.api.CustomAPI;
import com.fastweixin.api.config.ApiConfig;
import com.fastweixin.message.TextMsg;


@Service
public class PioneerAgentSaleInfoServiceImpl extends BaseServiceImpl<PioneerAgentSaleInfo,PioneerAgentSaleInfoSearch> implements PioneerAgentSaleInfoService {

	@Autowired
	private PioneerAgentSaleInfoDao pioneerAgentSaleInfoDao;
	@Autowired
	private PioneerAgentService pioneerAgentService;
	@Autowired
	private MemAccountService memAccountService;

	@Override
	protected BaseDao<PioneerAgentSaleInfo,PioneerAgentSaleInfoSearch> getBaseDao() {
		return pioneerAgentSaleInfoDao;
	}

	/**
	 * 添加销售明细
	 * @param pioneerAgentSaleInfoVo
	 * @return
	 */
	@Override
	@Transactional
	public Result addSaleInfo(PioneerAgentSaleInfoVo pioneerAgentSaleInfoVo) {
		Result result = new Result();
		Boolean flag = false;
		//信息校验
		PioneerAgentVo pioneerAgentVo = this.pioneerAgentService.getById(pioneerAgentSaleInfoVo.getAgentId());
		if(pioneerAgentVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取代理商信息失败");
			return result;
		}
		if(pioneerAgentSaleInfoVo.getSales() == null || pioneerAgentSaleInfoVo.getSales() <= 0.0f){
			result.setError(ResultCode.CODE_STATE_4006, "销售额不能小于0");
			return result;
		}
		if(pioneerAgentSaleInfoVo.getRebateRate() == null || pioneerAgentSaleInfoVo.getRebateRate()  <= 0.0f){
			result.setError(ResultCode.CODE_STATE_4006, "邀请人回扣不能小于于0");
			return result;
		}
		//年份是否存在
		PioneerAgentSaleInfoSearch search = new PioneerAgentSaleInfoSearch();
		search.setYear(pioneerAgentSaleInfoVo.getYear());
		search.setAgentId(pioneerAgentSaleInfoVo.getAgentId());
		long rows = this.getRowCount(search);
		if(rows > 0){
			result.setError(ResultCode.CODE_STATE_4006, "该年份已经存在，不能重复添加");
			return result;
		}
		pioneerAgentSaleInfoVo.setStatus(1);//已完成
		flag = this.add(pioneerAgentSaleInfoVo);
		if(flag){//赠送现金并发送短信
			MemWalletDetail memWalletDetail = new MemWalletDetail(null, pioneerAgentVo.getSubmitterId(), WalletType.SEND_BY_SALE, pioneerAgentSaleInfoVo.getRebate(), 
					new Date(), WalletType.WalletTypeMap.get(WalletType.SEND_BY_SALE), SourceType.PIONEERAGENTSALES, pioneerAgentSaleInfoVo.getId());
			result = this.memAccountService.updateWallet(memWalletDetail);//赠送提交人现金
			if(!result.isSuccess()){
				result.setError(ResultCode.CODE_STATE_4006, "代理商签约赠送金币失败");
				throw new ServiceException("代理商签约赠送金币失败");
			}
			//发送通知信息
			String smsContent = SmsTemplate.getSmsWhenPioneerRebates(pioneerAgentVo.getAgentName(), pioneerAgentSaleInfoVo.getYear(), pioneerAgentSaleInfoVo.getSales(), pioneerAgentSaleInfoVo.getRebate());
			if(!SystemConfig.isDebugMode()){//非调试模式才发送短信
				if(StringUtil.isNotEmpty(smsContent) && VerificationUtils.isValid(pioneerAgentVo.getSubmitterPhone(), VerificationUtils.MOBILE)){
					//有效的电话号码才发短信
					IHuiYiUtils.sentSms(pioneerAgentVo.getSubmitterPhone(), smsContent);
				}else{
					System.out.println("======短信通知电话无效或内容为空："+pioneerAgentVo.getSubmitterPhone()+":"+smsContent+"==========");
				}
			}
			//发送微信信息
			String wxMessage = WxMessageTemplate.whenPioneerAgentRebate(pioneerAgentVo.getAgentName(), pioneerAgentSaleInfoVo.getYear(), pioneerAgentSaleInfoVo.getSales(), pioneerAgentSaleInfoVo.getRebate(), WxMessageTemplate.getPioneerHost()+"/pages/account-details.jsp");
			if(StringUtil.isNotEmpty(pioneerAgentVo.getWxOpenId()) && wxMessage != null){
				WxMessageTemplate.sendWeixinMesage(pioneerAgentVo.getWxOpenId(), wxMessage);
			}
			result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "操作失败");
		}
		return result;
	}


}
