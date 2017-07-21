package com.aylson.dc.partner.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aylson.core.exception.ServiceException;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.partner.dao.PartnerAccountDao;
import com.aylson.dc.partner.po.PartnerAccount;
import com.aylson.dc.partner.po.PartnerWalletBill;
import com.aylson.dc.partner.search.PartnerAccountSearch;
import com.aylson.dc.partner.service.PartnerAccountService;
import com.aylson.dc.partner.service.PartnerWalletBillService;
import com.aylson.dc.partner.vo.PartnerAccountVo;


@Service
public class PartnerAccountServiceImpl extends BaseServiceImpl<PartnerAccount,PartnerAccountSearch> implements PartnerAccountService {

	@Autowired
	private PartnerAccountDao partnerAccountDao;
	@Autowired
	private PartnerWalletBillService partnerWalletBillService;

	@Override
	protected BaseDao<PartnerAccount,PartnerAccountSearch> getBaseDao() {
		return partnerAccountDao;
	}

	@Override
	@Transactional
	public Result updateWallet(PartnerWalletBill partnerWalletBill) {
		Result result = new Result();
		if(partnerWalletBill.getAccountId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取会员信息失败");
			return result;
		}
		if(partnerWalletBill.getWallet() == null){
			result.setError(ResultCode.CODE_STATE_4006, "钱包值不能为空");
			return result;
		}
		if(partnerWalletBill.getType() == null){
			result.setError(ResultCode.CODE_STATE_4006, "未知类型");
			return result;
		}
		//获取账号信息
		PartnerAccountVo partnerAccountVo = this.getById(partnerWalletBill.getAccountId());
		if(partnerAccountVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取账号信息失败");
			return result;
		}
		//之后积分值
		Float afterWallet = partnerWalletBill.getWallet() + partnerAccountVo.getWallet();
		if(afterWallet < 0.0f ){
			result.setError(ResultCode.CODE_STATE_4006, "钱包值不足");
			return result;
		}
		partnerAccountVo.setWallet(afterWallet);
		Boolean flag = this.edit(partnerAccountVo);
		if(flag){
			flag = this.partnerWalletBillService.add(partnerWalletBill);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "更新账号钱包成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "更新账号钱包失败");
				throw new ServiceException("更新账号钱包失败");
			}
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "更新账号钱包失败");
		}
		return result;
	}
	

}
