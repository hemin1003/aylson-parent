package com.aylson.dc.sys.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.base.GeneralConstant.IntegralConfigType;
import com.aylson.dc.sys.dao.IntegralConfigDao;
import com.aylson.dc.sys.po.IntegralConfig;
import com.aylson.dc.sys.search.IntegralConfigSearch;
import com.aylson.dc.sys.service.IntegralConfigService;
import com.aylson.dc.sys.vo.IntegralConfigVo;


@Service
public class IntegralConfigServiceImpl extends BaseServiceImpl<IntegralConfig,IntegralConfigSearch> implements IntegralConfigService {

	@Autowired
	private IntegralConfigDao integralConfigDao;

	@Override
	protected BaseDao<IntegralConfig,IntegralConfigSearch> getBaseDao() {
		return integralConfigDao;
	}

	@Override
	public Integer getIntegral(Integer type, Float amount) {
		Integer integral = 0;
		if(type != null){
			IntegralConfigSearch IntegralConfigSearch = new IntegralConfigSearch();
			IntegralConfigSearch.setType(type);
			List<IntegralConfigVo> list = this.getList(IntegralConfigSearch);
			if(list != null && list.size() > 0){
				if(IntegralConfigType.D_REGISTER_TO_REFERRAL == type){//（设计师）注册赠送[我的推荐人]积分
					integral = list.get(0).getIntegral();
				}else if(IntegralConfigType.D_REGISTER_TO_REFERRAL_PARENT == type){//（设计师）注册赠送[我的推荐人的推荐人]积分 
					integral = list.get(0).getIntegral(); 
				}else if(IntegralConfigType.D_SUBMIT == type){//（设计师）提交方案赠送[提交人]积分
					integral = list.get(0).getIntegral();
				}else if(IntegralConfigType.D_SUBMIT_TO_REFERRAL == type){//（设计师）提交方案赠送[推荐人]积分
					integral = list.get(0).getIntegral();
				}else if(IntegralConfigType.D_SETTLE == type){//（设计师）结算方案赠送[提交人]积分
					if(amount != null){
						for(int i=0; i<list.size(); i++){
							IntegralConfigVo temp = list.get(i);
							if(i == 0 && temp.getRate() != null && amount <= temp.getRangeBegin()){
								integral = Math.round(temp.getRate()*amount*100);
								break;
							}
							if(temp.getRate() != null && amount > temp.getRangeBegin() && amount <= temp.getRangeEnd()){
								integral = Math.round(temp.getRate()*amount*100);
								break;
							}
							if(i == (list.size()-1) && temp.getRate() != null && amount > temp.getRangeEnd()){
								integral = Math.round(temp.getRate()*amount*100);
								break;
							}
						}
					}
				}else if(IntegralConfigType.D_SETTLE_TO_REFERRAL == type){//（设计师）结算方案赠送[推荐人]积分
					for(int i=0; i<list.size(); i++){
						IntegralConfigVo temp = list.get(i);
						if(i == 0 && temp.getRate() != null && amount <= temp.getRangeBegin()){
							integral = Math.round(temp.getRate()*amount*100);
							break;
						}
						if(temp.getRate() != null && amount > temp.getRangeBegin() && amount <= temp.getRangeEnd()){
							integral = Math.round(temp.getRate()*amount*100);
							break;
						}
						if(i == (list.size()-1) && temp.getRate() != null && amount > temp.getRangeEnd()){
							integral = Math.round(temp.getRate()*amount*100);
							break;
						}
					}
				}else if(IntegralConfigType.W_REGISTER_TO_REFERRAL == type){//（产业工人）注册赠送[我的推荐人]积分
					integral = list.get(0).getIntegral();
				}else if(IntegralConfigType.W_REGISTER_TO_REFERRAL_PARENT == type){//（产业工人）注册赠送[我的推荐人的推荐人]积分 
					integral = list.get(0).getIntegral(); 
				}else if(IntegralConfigType.W_SUBMIT == type){//（产业工人）提交方案赠送[提交人]积分
					integral = list.get(0).getIntegral();
				}else if(IntegralConfigType.W_SUBMIT_TO_REFERRAL == type){//（产业工人）提交方案赠送[推荐人]积分
					integral = list.get(0).getIntegral();
				}
			}
		}
		
		return integral;
	}

	@Override
	public Integer getIntegralLevel(Integer integral) {
		Integer integralLevel = 1;
		if(integral != null && integral.intValue() > 0){
			IntegralConfigSearch integralConfigSearch = new IntegralConfigSearch();
			integralConfigSearch.setType(IntegralConfigType.INTEGRAL_LEVEL);
			List<IntegralConfigVo> integralLevelConfigList = this.getList(integralConfigSearch);
			if(integralLevelConfigList != null && integralLevelConfigList.size() > 0){
				for(IntegralConfigVo temp : integralLevelConfigList){
					if(temp.getRangeBegin() != null && temp.getRangeEnd() != null && temp.getRate() != null
							&& temp.getRangeBegin().intValue() < integral && integral <= temp.getRangeEnd().intValue()){
						integralLevel = temp.getIntegral();
					}
				}
			}
		}
		return integralLevel;
	}

	@Override
	public IntegralConfigVo getIntegralConfig(Integer type, Float rangeValue) {
		IntegralConfigVo integralConfigVo = null;
		if(type != null){
			IntegralConfigSearch IntegralConfigSearch = new IntegralConfigSearch();
			IntegralConfigSearch.setType(type);
			List<IntegralConfigVo> list = this.getList(IntegralConfigSearch);
			if(list != null && list.size() > 0){
				//只会有一条记录的配置数据
				if(IntegralConfigType.D_REGISTER_TO_REFERRAL == type || IntegralConfigType.D_REGISTER_TO_REFERRAL_PARENT == type ||
				   IntegralConfigType.D_SUBMIT == type || IntegralConfigType.D_SUBMIT_TO_REFERRAL == type || 
				   IntegralConfigType.W_REGISTER_TO_REFERRAL == type || IntegralConfigType.W_REGISTER_TO_REFERRAL_PARENT == type ||
				   IntegralConfigType.W_SUBMIT == type || IntegralConfigType.W_SUBMIT_TO_REFERRAL == type ||
				   IntegralConfigType.CLIENTINFO_LIMIT == type || IntegralConfigType.P_REGISTER_TO_REFERRAL == type ||
				   IntegralConfigType.P_REGISTER_TO_REFERRAL_PARENT == type || IntegralConfigType.P_SUBMITCLIENTINFO == type ||
				   IntegralConfigType.P_SUBMITCLIENTINFO_TO_REFERRAL == type || IntegralConfigType.P_SUBMITAGENTINFO == type ||
				   IntegralConfigType.P_SUBMITAGENTINFO_TO_REFERRAL == type || IntegralConfigType.P_REBATE_TO_REFERRAL == type || 
				   IntegralConfigType.P_SIGN_TO_REFERRAL == type || IntegralConfigType.P_SIGN_NOTSINGLE_TO_REFERRAL == type ||
				   IntegralConfigType.P_SIGN_TO_REFERRAL_PARENT == type || IntegralConfigType.P_OPEN_TO_REFERRAL_PARENT == type ||
				   IntegralConfigType.INTEGRAL_LEVEL == type
				){
					integralConfigVo = list.get(0);
				}
				//可以有多条记录的配置数据
				if(IntegralConfigType.D_SETTLE == type || IntegralConfigType.D_SETTLE_TO_REFERRAL == type){
					if(rangeValue != null){
						for(int i=0; i<list.size(); i++){
							IntegralConfigVo temp = list.get(i);
							if(i == 0 && temp.getRate() != null && rangeValue <= temp.getRangeBegin()){
								temp.setIntegral( Math.round(temp.getRate()*rangeValue*100));
								integralConfigVo = temp;
								break;
							}
							if(temp.getRate() != null && rangeValue > temp.getRangeBegin() && rangeValue <= temp.getRangeEnd()){
								temp.setIntegral( Math.round(temp.getRate()*rangeValue*100));
								integralConfigVo = temp;
								break;
							}
							if(i == (list.size()-1) && temp.getRate() != null && rangeValue > temp.getRangeEnd()){
								temp.setIntegral( Math.round(temp.getRate()*rangeValue*100));
								integralConfigVo = temp;
								break;
							}
						}
					}
				}
				if(IntegralConfigType.P_OPEN_TO_REFERRAL == type || IntegralConfigType.P_OPEN_NOTSINGLE_TO_REFERRAL == type ){
					if(rangeValue != null){
						for(int i=0; i<list.size(); i++){
							IntegralConfigVo temp = list.get(i);
							if(i == 0 && temp.getRate() != null && rangeValue <= temp.getRangeBegin()){
								integralConfigVo = temp;
								break;
							}
							if(temp.getRate() != null && rangeValue > temp.getRangeBegin() && rangeValue <= temp.getRangeEnd()){
								integralConfigVo = temp;
								break;
							}
							if(i == (list.size()-1) && temp.getRate() != null && rangeValue > temp.getRangeEnd()){
								integralConfigVo = temp;
								break;
							}
						}
					}
				}
				
			}
		}
		return integralConfigVo;
	}

	

}
