package com.aylson.dc.mem.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aylson.core.constants.SysConstant.BillCodePrefix;
import com.aylson.core.exception.ServiceException;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.mem.dao.GiftSendDao;
import com.aylson.dc.mem.po.GiftSend;
import com.aylson.dc.mem.search.GiftConfigSearch;
import com.aylson.dc.mem.search.GiftSendDetailSearch;
import com.aylson.dc.mem.search.GiftSendSearch;
import com.aylson.dc.mem.search.WxShareSearch;
import com.aylson.dc.mem.service.GiftConfigService;
import com.aylson.dc.mem.service.GiftSendDetailService;
import com.aylson.dc.mem.service.GiftSendService;
import com.aylson.dc.mem.service.WxShareService;
import com.aylson.dc.mem.vo.GiftConfigVo;
import com.aylson.dc.mem.vo.GiftSendDetailVo;
import com.aylson.dc.mem.vo.GiftSendVo;
import com.aylson.dc.mem.vo.WxShareVo;
import com.aylson.utils.BillNumUtils;
import com.aylson.utils.StringUtil;
import com.aylson.utils.VerificationUtils;

@Service
public class GiftSendServiceImpl extends BaseServiceImpl<GiftSend,GiftSendSearch> implements GiftSendService {
	
	private static final Logger logger = LoggerFactory.getLogger(GiftSendServiceImpl.class);
	
	@Autowired
	private GiftSendDao giftSendDao;
	@Autowired
	private GiftSendDetailService giftSendDetailService;
	@Autowired
	private GiftConfigService giftConfigService;
	@Autowired
	private WxShareService wxShareService;
	
	@Override
	protected BaseDao<GiftSend,GiftSendSearch> getBaseDao() {
		return this.giftSendDao;
	}

	@Override
	@Transactional
	public Result addGiftSend(GiftSendVo giftSendVo) {
		Result result = new Result();
		//信息校验
		if(StringUtil.isEmpty(giftSendVo.getDetailIds())){
			result.setError(ResultCode.CODE_STATE_4006, "礼品ids不能为空");
			return result;
		}
		if(StringUtil.isEmpty(giftSendVo.getDetailNames())){
			result.setError(ResultCode.CODE_STATE_4006, "礼品名称不能为空");
			return result;
		}
		if(StringUtil.isEmpty(giftSendVo.getConsignee())){
			result.setError(ResultCode.CODE_STATE_4006, "姓名不能为空");
			return result;
		}
		if(StringUtil.isEmpty(giftSendVo.getConsigneePhone())){
			result.setError(ResultCode.CODE_STATE_4006, "手机号码不能为空");
			return result;
		}
		if(!VerificationUtils.isValid(giftSendVo.getConsigneePhone(), VerificationUtils.MOBILE)){
			result.setError(ResultCode.CODE_STATE_4006, "请输入有效的手机号码");
			return result;
		}
		if(StringUtil.isEmpty(giftSendVo.getProvince()) || giftSendVo.getProvinceId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "所在地区不能为空");
			return result;
		}
		if(StringUtil.isEmpty(giftSendVo.getCity()) || giftSendVo.getCityId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "所在地区不能为空");
			return result;
		}
		if(StringUtil.isEmpty(giftSendVo.getArea()) || giftSendVo.getAreaId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "所在地区不能为空");
			return result;
		}
		if(StringUtil.isEmpty(giftSendVo.getAddress())){
			result.setError(ResultCode.CODE_STATE_4006, "详细地址不能为空");
			return result;
		}
		if(StringUtil.isEmpty(giftSendVo.getChannel())){
			result.setError(ResultCode.CODE_STATE_4006, "活动渠道不能为空");
			return result;
		}
		if(StringUtil.isEmpty(giftSendVo.getAccountPk())){
			result.setError(ResultCode.CODE_STATE_4006, "微信openId不能为空");
			return result;
		}
		//判断是否满足条件或者已经领取过了
		GiftConfigSearch giftConfigSearch = new GiftConfigSearch();
		giftConfigSearch.setIds(giftSendVo.getDetailIds().split(","));
		List<GiftConfigVo> giftConfigList = this.giftConfigService.getList(giftConfigSearch);
		if(giftConfigList == null || giftConfigList.size() <=0){
			result.setError(ResultCode.CODE_STATE_4006, "获取不到所选礼品的相关详情");
			return result;
		}
		WxShareSearch wxShareSearch = new WxShareSearch();
		wxShareSearch.setChannel(giftSendVo.getChannel());
		wxShareSearch.setInviterWxOpenId(giftSendVo.getAccountPk());
	    long curShareNum = this.wxShareService.getRowCount(wxShareSearch);
	    for(GiftConfigVo giftConfigVo : giftConfigList){//是否满足条件领取
	    	if(giftConfigVo.getIntegral().longValue() > curShareNum){
	    		result.setError(ResultCode.CODE_STATE_4006, "条件不足，不能领取【"+giftConfigVo.getGiftName()+"】礼品");
				return result;
	    	}
	    }
	    GiftSendDetailSearch giftSendDetailSearch = new GiftSendDetailSearch();
	    giftSendDetailSearch.setChannel(giftSendVo.getChannel());
	    giftSendDetailSearch.setAccountPk(giftSendVo.getAccountPk());
	    List<GiftSendDetailVo> giftSendDetailList = this.giftSendDetailService.getList(giftSendDetailSearch);
	    if(giftSendDetailList != null && giftSendDetailList.size() > 0){
	    	 for(GiftConfigVo giftConfigVo : giftConfigList){
	    		for(GiftSendDetailVo giftSendDetailVo : giftSendDetailList){
	 	    		if(giftConfigVo.getId().intValue() == giftSendDetailVo.getGiftId().intValue()){
	 	    			result.setError(ResultCode.CODE_STATE_4006, "已经领取过【"+giftConfigVo.getGiftName()+"】礼品，不能重复领取");
	 					return result;
	 	    		}
	 	    	}
	    	 }
	    }
		//信息有效，保存信息
		Boolean flag = this.giftSendDao.insert(giftSendVo);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "保存用户信息失败");
			return result;
		}
		String[] detailIdArray = giftSendVo.getDetailIds().split(",");
		String[] detailNameArray = giftSendVo.getDetailNames().split(",");
		List<GiftSendDetailVo>	detailList = new ArrayList<GiftSendDetailVo>();
		for(int i=0; i<detailIdArray.length; i++){
			GiftSendDetailVo giftSendDetailVo =  new GiftSendDetailVo();
			giftSendDetailVo.setGiftId(Integer.parseInt(detailIdArray[i]));
			giftSendDetailVo.setGiftName(detailNameArray[i]);
			giftSendDetailVo.setSendId(giftSendVo.getId());
			detailList.add(giftSendDetailVo);
		}
		flag = this.giftSendDetailService.batchAdd(detailList);
		if(flag){
			result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "保存礼品信息失败");
			throw new ServiceException("保存礼品信息失败");
		}
		return result;
	}

	@Override
	public Result exchangeActGift(GiftSendVo giftSendVo) {
		Result result = new Result();
		if(StringUtil.isEmpty(giftSendVo.getConsignee())){
			result.setError(ResultCode.CODE_STATE_4006, "姓名不能为空");
			return result;
		}
		if(StringUtil.isEmpty(giftSendVo.getConsigneePhone())){
			result.setError(ResultCode.CODE_STATE_4006, "手机号码不能为空");
			return result;
		}
		if(!VerificationUtils.isValid(giftSendVo.getConsigneePhone(), VerificationUtils.MOBILE)){
			result.setError(ResultCode.CODE_STATE_4006, "请输入有效的手机号码");
			return result;
		}
		if(StringUtil.isEmpty(giftSendVo.getProvince()) || giftSendVo.getProvinceId() == null
				|| StringUtil.isEmpty(giftSendVo.getCity()) || giftSendVo.getCityId() == null
					||StringUtil.isEmpty(giftSendVo.getArea()) || giftSendVo.getAreaId() == null
				){
			result.setError(ResultCode.CODE_STATE_4006, "所在地区不能为空");
			return result;
		}
		if(StringUtil.isEmpty(giftSendVo.getAddress())){
			result.setError(ResultCode.CODE_STATE_4006, "详细地址不能为空");
			return result;
		}
		if(StringUtil.isEmpty(giftSendVo.getChannel())){
			result.setError(ResultCode.CODE_STATE_4006, "活动渠道不能为空");
			return result;
		}
		if(StringUtil.isEmpty(giftSendVo.getAccountPk())){
			result.setError(ResultCode.CODE_STATE_4006, "微信openId不能为空");
			return result;
		}
	    //判断是否已经领取过了
	    GiftSendDetailSearch giftSendDetailSearch = new GiftSendDetailSearch();
	    giftSendDetailSearch.setChannel(giftSendVo.getChannel());
	    giftSendDetailSearch.setAccountPk(giftSendVo.getAccountPk());
	    List<GiftSendDetailVo> giftSendDetailList = this.giftSendDetailService.getList(giftSendDetailSearch);
	    if(giftSendDetailList != null && giftSendDetailList.size() > 0){//已经领取过了
	    	result.setError(ResultCode.CODE_STATE_4006, "你已经领取过该次活动的奖品！如有疑问，请联系客服人员");
			return result;
	    }else{//未领取过该次活动奖品，则判断是否满足条件领取
			WxShareSearch wxShareSearch = new WxShareSearch();
			wxShareSearch.setChannel(giftSendVo.getChannel());
			wxShareSearch.setInviterWxOpenId("join");
			wxShareSearch.setWxOpenId(giftSendVo.getAccountPk());
		    //long curShareNum = this.wxShareService.getRowCount(wxShareSearch);
		    List<WxShareVo> list = this.wxShareService.getList(wxShareSearch);
		    if(list != null && list.size() > 0){
		    	WxShareVo ws = list.get(0);
		    	if(ws.getSharesHad() < 100 && ws.getAssistValueHad() < 800){
		    		result.setError(ResultCode.CODE_STATE_4006, "条件不足，还未能领取该奖品！如有疑问，请联系客服人员");
					return result;
		    	}
		    }
	    }
		//信息有效，保存信息
	    giftSendVo.setCreateTime(new Date());
	    giftSendVo.setAccountType(1);//微信类型
	    giftSendVo.setBillCode(BillNumUtils.getBillCode(BillCodePrefix.EXCHANGEGIFT,giftSendVo.getConsigneePhone()));
		Boolean flag = this.giftSendDao.insert(giftSendVo);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "保存信息失败");
			return result;
		}
		GiftSendDetailVo  giftSendDetailVo = new GiftSendDetailVo();
		giftSendDetailVo.setGiftName("商务四件套");
		giftSendDetailVo.setGiftId(null);
		giftSendDetailVo.setSendId(giftSendVo.getId());
		giftSendDetailVo.setCreateTime(new Date());
		flag = this.giftSendDetailService.add(giftSendDetailVo);
		if(flag){
			result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "保存礼品信息失败");
			throw new ServiceException("保存礼品信息失败");
		}
		return result;
	}

	
}
