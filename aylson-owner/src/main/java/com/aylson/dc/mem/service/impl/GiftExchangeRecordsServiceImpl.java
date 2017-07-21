package com.aylson.dc.mem.service.impl;

import java.util.Date;
import java.util.List;

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
import com.aylson.dc.base.GeneralConstant.IntegeralType;
import com.aylson.dc.base.GeneralConstant.SourceType;
import com.aylson.dc.base.SmsTemplate;
import com.aylson.dc.mem.dao.GiftExchangeRecordsDao;
import com.aylson.dc.mem.po.GiftExchangeRecords;
import com.aylson.dc.mem.search.GiftExchangeRecordsSearch;
import com.aylson.dc.mem.service.GiftConfigService;
import com.aylson.dc.mem.service.GiftExchangeRecordsService;
import com.aylson.dc.mem.service.MemAccountService;
import com.aylson.dc.mem.vo.GiftConfigVo;
import com.aylson.dc.mem.vo.GiftExchangeRecordsVo;
import com.aylson.dc.mem.vo.MemAccountVo;
import com.aylson.dc.mem.vo.MemIntegralDetailVo;
import com.aylson.dc.sys.common.SystemConfig;
import com.aylson.dc.sys.search.DictionarySearch;
import com.aylson.dc.sys.service.DictionaryService;
import com.aylson.dc.sys.vo.DictionaryVo;
import com.aylson.utils.DateUtil;
import com.aylson.utils.IHuiYiUtils;
import com.aylson.utils.StringUtil;
import com.aylson.utils.VerificationUtils;

@Service
public class GiftExchangeRecordsServiceImpl extends BaseServiceImpl<GiftExchangeRecords,GiftExchangeRecordsSearch> implements GiftExchangeRecordsService {
	
	private static final Logger logger = LoggerFactory.getLogger(GiftExchangeRecordsServiceImpl.class);
	
	@Autowired
	private GiftExchangeRecordsDao giftExchangeRecordsDao;
	@Autowired
	private GiftConfigService giftConfigService;
	@Autowired
	private MemAccountService memAccountService;
	@Autowired
	private DictionaryService dictionaryService;
	
	@Override
	protected BaseDao<GiftExchangeRecords,GiftExchangeRecordsSearch> getBaseDao() {
		return this.giftExchangeRecordsDao;
	}

	/**
	 * 兑换礼品
	 * @param giftExchangeRecords
	 * @return
	 */
	@Override
	@Transactional
	public Result exchangeGift(GiftExchangeRecordsVo giftExchangeRecordsVo) {
		//信息校验
		Result result = this.commonValid(giftExchangeRecordsVo);
		Boolean flag = false;
		//逻辑处理：添加兑换记录，添加积分明细以及更新会员积分，同时发送短信
		GiftConfigVo giftConfigVo = this.giftConfigService.getById(giftExchangeRecordsVo.getGiftId());
		if(giftConfigVo != null){
			if(giftConfigVo.getIntegral() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取礼品配置的兑换积分失败");
				return result;
			}
			if(giftExchangeRecordsVo.getIntegral() <  giftConfigVo.getIntegral()){
				result.setError(ResultCode.CODE_STATE_4006, "积分不足");
				return result;
			}
			Date curDate = new Date();//当前时间
			Date validDate = DateUtil.operDay(curDate, this.getConfigValidDay());    //有效时间
			//获取兑换码：
			String exchangeCode = (((Math.random()*9+1)*100000)+"").substring(0, 6);
			Boolean ifExist = false;
			while(!ifExist){
				GiftExchangeRecordsSearch giftExchangeRecordsSearch = new GiftExchangeRecordsSearch();
				giftExchangeRecordsSearch.setExchangeCodeLike(exchangeCode);
				long rows = this.giftExchangeRecordsDao.getRowCount(giftExchangeRecordsSearch);
				if(rows > 0){
					exchangeCode = (((Math.random()*9+1)*100000)+"").substring(0, 6);
				}else{
					ifExist = true;
				}
			}
			//处理兑换记录
			giftExchangeRecordsVo.setExchangeCode(exchangeCode);
			giftExchangeRecordsVo.setExchangeTime(curDate);
			giftExchangeRecordsVo.setGiftName(giftConfigVo.getGiftName());
			giftExchangeRecordsVo.setIntegral(giftConfigVo.getIntegral());
			giftExchangeRecordsVo.setValidTime(validDate);
			flag = this.giftExchangeRecordsDao.insert(giftExchangeRecordsVo);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "添加兑换记录失败");
				throw new ServiceException("添加兑换记录失败");
			}
			//处理会员积分情况
			MemIntegralDetailVo memIntegralDetailVo = new MemIntegralDetailVo();
			memIntegralDetailVo.setIntegral(-giftConfigVo.getIntegral());
			memIntegralDetailVo.setCreateTime(curDate);
			memIntegralDetailVo.setSourceType(SourceType.EXCHANGEGIFT);
			memIntegralDetailVo.setSourceId(giftExchangeRecordsVo.getId());
			memIntegralDetailVo.setMemberId(giftExchangeRecordsVo.getExchangerId());
			memIntegralDetailVo.setType(IntegeralType.EXCHANGEGIFT);
			memIntegralDetailVo.setDescription("积分兑换礼品");
			result = this.memAccountService.updateIntegral(memIntegralDetailVo);
			if(!result.isSuccess()){
				throw new ServiceException("更新会员积分信息失败");
			}
			//非debug模式发送短信
			if(!SystemConfig.isDebugMode()){
				String smsContent = SmsTemplate.getSmsWhenExchangeGift(giftExchangeRecordsVo.getGiftName(), giftExchangeRecordsVo.getIntegral(), giftExchangeRecordsVo.getExchangeCode(), giftExchangeRecordsVo.getValidTime());
				IHuiYiUtils.sentSms(giftExchangeRecordsVo.getExchangePhone(), smsContent);
			}
			result.setOK(ResultCode.CODE_STATE_200, "保存成功",giftExchangeRecordsVo);
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "获取礼品配置信息失败");
			throw new ServiceException("获取礼品配置信息失败");
		}
		return result;
	}

	/**
	 * 通用性校验
	 * @param giftExchangeRecords
	 * @return
	 */
	@Override
	public Result commonValid(GiftExchangeRecordsVo giftExchangeRecordsVo) {
		Result result = new Result();
		if(StringUtil.isEmpty(giftExchangeRecordsVo.getExchangePhone())){
			result.setError(ResultCode.CODE_STATE_4006, "兑换人的手机号码不能为空");
			return result;
		}	
		if(!VerificationUtils.isValid(giftExchangeRecordsVo.getExchangePhone(), VerificationUtils.MOBILE)){
			result.setError(ResultCode.CODE_STATE_4006, "兑换人的手机号码格式有误");
			return result;
		}
		if(StringUtil.isEmpty(giftExchangeRecordsVo.getExchanger()) || giftExchangeRecordsVo.getExchangerId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "兑换人信息不能为空");
			return result;
		}
		if(giftExchangeRecordsVo.getGiftId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取礼品配置id失败");
			return result;
		}
		result.setOK(ResultCode.CODE_STATE_200, "信息有效");
		return result;
	}
	
	/**
	 * 获取配置的礼品兑换有效领取时长，默认30天，
	 * @return
	 */
	public Integer getConfigValidDay(){
		Integer day = 30;//默认30天
		DictionarySearch search = new DictionarySearch();
		search.setDicType("exchangeValidDay");
		List<DictionaryVo> list = this.dictionaryService.getList(search);
		if(list != null && list.size() > 0){
			String exchangeValidDay = list.get(0).getDicValue();
			if(StringUtil.isNotEmpty(exchangeValidDay)){
				try{
					day = Integer.parseInt(exchangeValidDay);
				}catch(Exception e){
					day = 30;
					e.printStackTrace();
				}
			}
		}
		return day;
	}
	
	@Override
	@Transactional
	public Result exchangeGift(Integer giftId, String memberId) throws Exception{
		Result result = new Result();
		if(giftId == null){
			result.setError(ResultCode.CODE_STATE_4006, "查看礼品详情失败");
			return result;
		}
		if(StringUtil.isEmpty(memberId)){
			result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
			return result;
		}
		MemAccountVo memAccountVo = this.memAccountService.getById(Integer.parseInt(memberId));
		if(memAccountVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
			return result;
		}
		GiftExchangeRecordsVo giftExchangeRecordsVo = new GiftExchangeRecordsVo();
		giftExchangeRecordsVo.setGiftId(giftId);
		giftExchangeRecordsVo.setExchangePhone(memAccountVo.getMobilePhone());
		giftExchangeRecordsVo.setExchanger(memAccountVo.getAccountName());
		giftExchangeRecordsVo.setExchangerId(memAccountVo.getId());
		//信息校验
		result = this.commonValid(giftExchangeRecordsVo);
		Boolean flag = false;
		//逻辑处理：添加兑换记录，添加积分明细以及更新会员积分，同时发送短信
		GiftConfigVo giftConfigVo = this.giftConfigService.getById(giftExchangeRecordsVo.getGiftId());
		if(giftConfigVo != null){
			if(giftConfigVo.getIntegral() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取礼品配置的兑换积分失败");
				return result;
			}
			if(memAccountVo.getIntegral() <  giftConfigVo.getIntegral()){
				result.setError(ResultCode.CODE_STATE_4006, "积分不足");
				return result;
			}
			Date curDate = new Date();//当前时间
			Date validDate = DateUtil.operDay(curDate, this.getConfigValidDay());    //有效时间
			//获取兑换码：
			String exchangeCode = (((Math.random()*9+1)*100000)+"").substring(0, 6);
			Boolean ifExist = false;
			while(!ifExist){
				GiftExchangeRecordsSearch giftExchangeRecordsSearch = new GiftExchangeRecordsSearch();
				giftExchangeRecordsSearch.setExchangeCodeLike(exchangeCode);
				long rows = this.giftExchangeRecordsDao.getRowCount(giftExchangeRecordsSearch);
				if(rows > 0){
					exchangeCode = (((Math.random()*9+1)*100000)+"").substring(0, 6);
				}else{
					ifExist = true;
				}
			}
			//处理兑换记录
			giftExchangeRecordsVo.setExchangeCode(exchangeCode);
			giftExchangeRecordsVo.setExchangeTime(curDate);
			giftExchangeRecordsVo.setGiftName(giftConfigVo.getGiftName());
			giftExchangeRecordsVo.setIntegral(giftConfigVo.getIntegral());
			giftExchangeRecordsVo.setValidTime(validDate);
			giftExchangeRecordsVo.setMatchObj(giftConfigVo.getMatchObj());
			flag = this.giftExchangeRecordsDao.insert(giftExchangeRecordsVo);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "添加兑换记录失败");
				throw new ServiceException("添加兑换记录失败");
			}
			//处理会员积分情况
			MemIntegralDetailVo memIntegralDetailVo = new MemIntegralDetailVo();
			memIntegralDetailVo.setIntegral(-giftConfigVo.getIntegral());
			memIntegralDetailVo.setCreateTime(curDate);
			memIntegralDetailVo.setSourceType(SourceType.EXCHANGEGIFT);
			memIntegralDetailVo.setSourceId(giftExchangeRecordsVo.getId());
			memIntegralDetailVo.setMemberId(giftExchangeRecordsVo.getExchangerId());
			memIntegralDetailVo.setType(IntegeralType.EXCHANGEGIFT);
			memIntegralDetailVo.setDescription("积分兑换礼品");
			result = this.memAccountService.updateIntegral(memIntegralDetailVo);
			if(!result.isSuccess()){
				throw new ServiceException("更新会员积分信息失败");
			}
			//非debug模式发送短信
			if(!SystemConfig.isDebugMode()){
				String smsContent = SmsTemplate.getSmsWhenExchangeGift(giftExchangeRecordsVo.getGiftName(), giftExchangeRecordsVo.getIntegral(), giftExchangeRecordsVo.getExchangeCode(), giftExchangeRecordsVo.getValidTime());
				IHuiYiUtils.sentSms(giftExchangeRecordsVo.getExchangePhone(), smsContent);
			}
			result.setOK(ResultCode.CODE_STATE_200, "保存成功",giftExchangeRecordsVo);
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "获取礼品配置信息失败");
			throw new ServiceException("获取礼品配置信息失败");
		}
		return result;
	}
	
	
}
