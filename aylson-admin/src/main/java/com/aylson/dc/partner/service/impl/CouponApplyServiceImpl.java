package com.aylson.dc.partner.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aylson.core.exception.ServiceException;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.base.PartnerGeneralConstant.CouponApplyState;
import com.aylson.dc.partner.dao.CouponApplyDao;
import com.aylson.dc.partner.po.CouponApply;
import com.aylson.dc.partner.search.CouponApplySearch;
import com.aylson.dc.partner.service.CouponApplyService;
import com.aylson.dc.partner.service.CouponService;
import com.aylson.dc.partner.vo.CouponApplyVo;
import com.aylson.utils.IHuiYiUtils;
import com.aylson.utils.StringUtil;


@Service
public class CouponApplyServiceImpl extends BaseServiceImpl<CouponApply,CouponApplySearch> implements CouponApplyService {

	@Autowired
	private CouponApplyDao couponApplyDao;
	@Autowired
	private CouponService couponService;

	@Override
	protected BaseDao<CouponApply,CouponApplySearch> getBaseDao() {
		return couponApplyDao;
	}

	@Override
	@Transactional
	public Result verify(Integer id, Boolean isPass) {
		Result result = new Result();
		Boolean flag = true;
		//信息校验
		if(id == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取优惠券申请信息失败！");
			return result;
		}
		if(isPass == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取优惠券申请的状态失败！");
			return result;
		}
		CouponApplyVo couponApplyVo = this.getById(id);
		if(couponApplyVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取优惠券申请信息失败！");
			return result;
		}
		//信息有效
		String smsContent = null;   //通知短信内容
		//CouponApplyVo couponApplyVo = new CouponApplyVo();
		//couponApplyVo.setId(id);
		if(isPass){//如果通过，发送优惠券到申请人
			//CouponApplyVo couponApply = this.getById(id);
			//if(couponApply == null){
				//result.setError(ResultCode.CODE_STATE_4006, "获取优惠券申请信息失败！");
				//return result;
			//}
			if(CouponApplyState.PASS == couponApplyVo.getState().intValue()){
				result.setError(ResultCode.CODE_STATE_4006, "您已经审核过该申请了，不能重复操作！");
				return result;
			}
			//发送优惠券
			flag = this.couponService.batchAdd(this.couponService.getSendList(couponApplyVo.getId(), 
					couponApplyVo.getApplyCouponId(), couponApplyVo.getApplierId(), couponApplyVo.getApplyNum()));
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "发送优惠券失败！");
				return result;
			}
			couponApplyVo.setState(CouponApplyState.PASS);        //通过
			smsContent = "您为"+couponApplyVo.getOwnerName()+"提交的现金券申请已通过，请微信登录查看。";
		}else{//如果不通过
			couponApplyVo.setState(CouponApplyState.NOTPASS);     //不通过
			couponApplyVo.setIsBind(false);                       //解绑
			smsContent = "您为"+couponApplyVo.getOwnerName()+"提交的现金券申请不通过，请微信登录查看。";
		}
		couponApplyVo.setAuditTime(new Date());
		flag = this.edit(couponApplyVo);
		if(flag){
			//发送短信通知
			IHuiYiUtils.sentSms(couponApplyVo.getApplierPhone(), smsContent);
			result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			throw new ServiceException("更新优惠券申请信息失败！");
		}
		return result;
	}

	@Override
	public Result isBond(String ownerPhone) {
		Result result = new Result();
		if(StringUtil.isNotEmpty(ownerPhone)){
			CouponApplySearch search = new CouponApplySearch();
			search.setOwnerPhone(ownerPhone);
			search.setIsBind(true);
			List<CouponApplyVo> list = this.getList(search);
			if(list != null && list.size() > 0){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功",list.get(0));
			}
		}
		return result;
	}


}
