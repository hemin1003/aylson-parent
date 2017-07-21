package com.aylson.dc.partner.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.partner.dao.CouponApplyDao;
import com.aylson.dc.partner.po.CouponApply;
import com.aylson.dc.partner.search.CouponApplySearch;
import com.aylson.dc.partner.service.CouponApplyService;
import com.aylson.dc.partner.vo.CouponApplyVo;
import com.aylson.utils.StringUtil;
import com.aylson.utils.VerificationUtils;


@Service
public class CouponApplyServiceImpl extends BaseServiceImpl<CouponApply,CouponApplySearch> implements CouponApplyService {

	@Autowired
	private CouponApplyDao couponApplyDao;

	@Override
	protected BaseDao<CouponApply,CouponApplySearch> getBaseDao() {
		return couponApplyDao;
	}

	@Override
	public Result applyCoupon(CouponApplyVo couponApplyVo) {
		Result result = new Result();
		//信息校验
		if(couponApplyVo.getApplierId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "申请人信息不能为空");
			return result;
		}
		if(couponApplyVo.getApplyCouponId() == null || StringUtil.isEmpty(couponApplyVo.getApplyCouponName())){
			result.setError(ResultCode.CODE_STATE_4006, "请选择分类");
			return result;
		}
		if(StringUtil.isEmpty(couponApplyVo.getOwnerName())){
			result.setError(ResultCode.CODE_STATE_4006, "姓名不能为空");
			return result;
		}
		if(StringUtil.isEmpty(couponApplyVo.getOwnerPhone())){
			result.setError(ResultCode.CODE_STATE_4006, "电话不能为空");
			return result;
		}
		if(!VerificationUtils.isValid(couponApplyVo.getOwnerPhone(), VerificationUtils.MOBILE)){
			result.setError(ResultCode.CODE_STATE_4006, "请输入有效的手机号码");
			return result;
		}
		if(StringUtil.isEmpty(couponApplyVo.getProvince()) || couponApplyVo.getProvinceId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "省会不能为空");
			return result;
		}
		if(StringUtil.isEmpty(couponApplyVo.getCity()) || couponApplyVo.getCityId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "城市不能为空");
			return result;
		}
		if(StringUtil.isEmpty(couponApplyVo.getArea()) || couponApplyVo.getAreaId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "区县不能为空");
			return result;
		}
		if(StringUtil.isEmpty(couponApplyVo.getStreet())){
			result.setError(ResultCode.CODE_STATE_4006, "街道/小区不能为空");
			return result;
		}
		if(StringUtil.isEmpty(couponApplyVo.getUnit())){
			result.setError(ResultCode.CODE_STATE_4006, "门牌号/单元不能为空");
			return result;
		}
		if(couponApplyVo.getApplyNum() == null){
			result.setError(ResultCode.CODE_STATE_4006, "请输入申请数量");
			return result;
		}
		if(couponApplyVo.getApplyNum().intValue() < 1){
			result.setError(ResultCode.CODE_STATE_4006, "申请数量至少为1");
			return result;
		}
		if(couponApplyVo.getApplyNum().intValue() > 100){
			result.setError(ResultCode.CODE_STATE_4006, "申请数量不能超过100");
			return result;
		}
		CouponApplySearch search  = new CouponApplySearch();
		//search.setApplierId(couponApplyVo.getApplierId());
		search.setOwnerPhone(couponApplyVo.getOwnerPhone());
		search.setIsBind(true);
		List<CouponApplyVo> applyRecords = this.getList(search);
		//是否已经被别人绑定了
		if(applyRecords != null && applyRecords.size() > 0){
			/*if(applyRecords.get(0).getApplierId().intValue() == couponApplyVo.getApplierId().intValue()){
				result.setError(ResultCode.CODE_STATE_4006, "你已经为该手机号码申请过优惠券了，不能重复申请");
				return result;
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "该手机号码已经有其它人申请过优惠券了，不能申请，如有疑问，请您咨询相关人员或进行申诉");
				return result;
			}*/
			if(applyRecords.get(0).getApplierId().intValue() != couponApplyVo.getApplierId().intValue()){
				result.setError(ResultCode.CODE_STATE_4006, "该手机号码已经有其它人申请过优惠券了，不能申请，如有疑问，请您咨询相关人员或进行申诉");
				return result;
			}
		}
		//信息有效
		couponApplyVo.setIsBind(true);//申请时绑定用户
		couponApplyVo.setApplyTime(new Date());
		Boolean flag = this.add(couponApplyVo);
		if(flag){
			result.setOK(ResultCode.CODE_STATE_200, "申请已经成功提交，审核结果请稍后登录系统查看结果！");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "操作失败");
		}
		return result;
		
	}
	

}
